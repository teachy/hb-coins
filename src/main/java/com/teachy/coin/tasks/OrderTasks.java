package com.teachy.coin.tasks;

import com.teachy.coin.huobi.HBApi;
import com.teachy.coin.huobi.client.req.trade.CreateOrderRequest;
import com.teachy.coin.huobi.constant.enums.CandlestickIntervalEnum;
import com.teachy.coin.huobi.model.account.Account;
import com.teachy.coin.huobi.model.account.Balance;
import com.teachy.coin.huobi.model.market.Candlestick;
import com.teachy.coin.huobi.model.market.MarketTrade;
import com.teachy.coin.huobi.model.trade.MatchResult;
import com.teachy.coin.model.CoinsBuy;
import com.teachy.coin.model.CoinsMatchResults;
import com.teachy.coin.model.CoinsSymbols;
import com.teachy.coin.service.CoinsBuyService;
import com.teachy.coin.service.CoinsMatchResultsService;
import com.teachy.coin.service.CoinsSymbolsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName OrderTasks
 * @Author gang.tu
 * @Date 2021/1/9 16:38
 */
@Component
@Slf4j
public class OrderTasks {
    @Resource
    CoinsSymbolsService soinsSymbolsService;
    @Resource
    CoinsBuyService coinsBuyService;
    @Resource
    CoinsMatchResultsService coinsMatchResultsService;
    @Resource
    HBApi hbApi;
    public static boolean isOn = true;
    public static int MAX_COINS = 15;
    public static String BUY_MAX = "6";
    public static String UU_ID = "null";
    List<CoinsSymbols> enableCoins = Collections.synchronizedList(new ArrayList<>());
    Map<String, String> coinNames = new HashMap<>();
    public static volatile List<CoinsSymbols> buyCoins = Collections.synchronizedList(new ArrayList<>());
    public static volatile List<CoinsSymbols> zbBuy = Collections.synchronizedList(new ArrayList<>());
    public static volatile List<CoinsSymbols> sellCoins = Collections.synchronizedList(new ArrayList<>());
    private Long accountId = 0L;
    private Map<String, Long> buyTime = new HashMap<>();

    @PostConstruct
    void init() {
        List<Account> accounts = hbApi.accountList();
        for (Account account : accounts) {
            if (account.getType().equals("spot")) {
                accountId = account.getId();
                break;
            }
        }
        enableCoins = soinsSymbolsService.getEnableCoins();
        enableCoins.stream().forEach(e -> coinNames.put(e.getBaseCurrency(), e.getSymbol()));
        initSellCoins();
    }

    public void initSellCoins() {
        List<Balance> list = hbApi.getBalances(accountId).stream().filter(e -> coinNames.containsKey(e.getCurrency())).collect(toList());
        List<CoinsBuy> all = coinsBuyService.findAll();
        sellCoins.clear();
        for (Balance balance : list) {
            boolean temp = false;
            for (CoinsBuy coinsBuy : all) {
                if (coinsBuy.getBaseUrrency().equals(balance.getCurrency())) {
                    temp = true;
                    break;
                }
            }
            if (temp) {
                MarketTrade marketTrade = hbApi.getMarketTrade(coinNames.get(balance.getCurrency())).get(0);
                if (Double.valueOf(marketTrade.getPrice().toPlainString()) * Double.valueOf(balance.getBalance().toPlainString()) > 3) {
                    for (CoinsSymbols coinsSymbol : enableCoins) {
                        if (coinsSymbol.getSymbol().equals(coinNames.get(balance.getCurrency()))) {
                            sellCoins.add(coinsSymbol);
                            break;
                        }
                    }
                } else {
                    coinsBuyService.deleteBySymbol(coinNames.get(balance.getCurrency()));
                }
            }
        }
    }


    /**
     * 功能描述 找寻有用数据
     *
     * @return void
     * @author gang.tu
     **/
    @Scheduled(cron = "*/1 * * * * ?")
    public void begin() {
        if (isOn && sellCoins.size() < MAX_COINS) {
            getKline();
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 功能描述 buy
     *
     * @return void
     * @author gang.tu
     **/
    @Scheduled(cron = "*/1 * * * * ?")
    public void buy() {
        if (isOn) {
            dealBuyCoins();
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 功能描述 sell
     *
     * @return void
     * @author gang.tu
     **/
    @Scheduled(cron = "*/1 * * * * ?")
    public void sell() {
        if (isOn) {
            dealSellCoins();
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void dealBuyCoins() {
        buyCoins.removeAll(sellCoins);
        if (buyCoins.size() > 0) {
            for (CoinsSymbols e : buyCoins) {
                if (sellCoins.contains(e)) {
                    continue;
                }
                Long aLong = buyTime.get(e.getSymbol());
                if (aLong != null) {
                    if (System.currentTimeMillis() - aLong < 1800000) {
                        continue;
                    }
                }
                CreateOrderRequest createOrderRequest = CreateOrderRequest.spotBuyMarket(accountId, e.getSymbol(), new BigDecimal(BUY_MAX));
                Long buyMarketId = hbApi.createOrder(createOrderRequest);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                List<CoinsMatchResults> results = insertResults(e, buyMarketId);
                CoinsBuy coinsBuy = new CoinsBuy();
                coinsBuy.setSymbol(e.getSymbol());
                coinsBuy.setBaseUrrency(e.getBaseCurrency());
                coinsBuy.setPrice(results.get(0).getPrice());
                coinsBuy.setSellPrice(new BigDecimal(coinsBuy.getPrice()).multiply(new BigDecimal("0.95")).toPlainString());
                coinsBuyService.save(coinsBuy);
                sellCoins.add(e);
                buyTime.put(e.getSymbol(), System.currentTimeMillis());
            }
        }
    }

    void dealSellCoins() {
        if (sellCoins.size() > 0) {
            Iterator<CoinsSymbols> iterator = sellCoins.iterator();
            while (iterator.hasNext()) {
                try {
                    Thread.sleep(200);
                    CoinsSymbols next = null;
                    try {
                        next = iterator.next();
                    } catch (Exception ee) {
                        return;
                    }
                    MarketTrade marketTrade = hbApi.getMarketTrade(next.getSymbol()).get(0);
                    CoinsBuy coinsBuy = coinsBuyService.findBy("baseUrrency", next.getBaseCurrency());
                    if (coinsBuy != null) {
                        double nowPrice = Double.valueOf(marketTrade.getPrice().toPlainString());
                        double sellPrice = Double.valueOf(coinsBuy.getSellPrice());
                        if (nowPrice > sellPrice) {
                            if (nowPrice > sellPrice * 1.08) {
                                coinsBuy.setSellPrice(marketTrade.getPrice().multiply(new BigDecimal("0.95")).toPlainString());
                                coinsBuyService.update(coinsBuy);
                            }
                        } else {
                            List<Balance> list = hbApi.getBalances(accountId).stream().filter(e -> coinNames.containsKey(e.getCurrency())).collect(toList());
                            for (Balance balance : list) {
                                if (balance.getCurrency().equals(next.getBaseCurrency())) {
                                    doSell(next, balance);
                                    buyTime.put(next.getSymbol(), System.currentTimeMillis());
                                    break;
                                }
                            }
                            iterator.remove();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    void doSell(CoinsSymbols coinsSymbols, Balance balance) {
        log.info("sell coin：{}", coinsSymbols.getBaseCurrency());
        CreateOrderRequest createOrderRequest = CreateOrderRequest.spotSellMarket(accountId, coinsSymbols.getSymbol(), balance.getBalance().setScale(coinsSymbols.getAmountPrecision(), BigDecimal.ROUND_DOWN));
        Long sellMarketId = hbApi.createOrder(createOrderRequest);
        insertResults(coinsSymbols, sellMarketId);
        coinsBuyService.deleteBySymbol(coinsSymbols.getSymbol());
    }


    List<CoinsMatchResults> insertResults(CoinsSymbols coinsSymbols, Long marketId) {
        List<MatchResult> matchResult = hbApi.getMatchResult(marketId);
        if (matchResult == null || matchResult.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            matchResult = hbApi.getMatchResult(marketId);
        }
        List<CoinsMatchResults> results = matchResult.stream().map(eve -> {
            CoinsMatchResults coinsMatchResults = new CoinsMatchResults();
            BeanUtils.copyProperties(eve, coinsMatchResults);
            coinsMatchResults.setBaseCurrency(coinsSymbols.getBaseCurrency());
            coinsMatchResults.setFilledAmount(eve.getFilledAmount().toPlainString());
            coinsMatchResults.setFilledFees(eve.getFilledFees().toPlainString());
            coinsMatchResults.setPrice(eve.getPrice().toPlainString());
            return coinsMatchResults;
        }).collect(toList());
        coinsMatchResultsService.save(results.get(0));
        return results;
    }

    /**
     * 功能描述 K线分析
     *
     * @return void
     * @author gang.tu
     **/
    private void getKline() {
        enableCoins.stream().forEach(e -> {
            if (!buyCoins.contains(e) && !sellCoins.contains(e)) {
                List<Candlestick> candlestick = hbApi.getCandlestick(e.getSymbol(), CandlestickIntervalEnum.DAY1, 45);
                if (checkAmount(e, candlestick)) {
                    buyCoins.add(e);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });

    }

    private boolean checkAmount(CoinsSymbols coinsSymbols, List<Candlestick> candlestick) {
        boolean f1 = false;
        int maxI = 0;
        for (int i = candlestick.size() - 1; i >= 35; i--) {
            Double amount = Double.valueOf(candlestick.get(i).getAmount().toPlainString());
            Double firstValue = Double.valueOf(candlestick.get(i).getClose().toPlainString());
            Double openValue = Double.valueOf(candlestick.get(i).getOpen().toPlainString());
            if (firstValue > openValue) {
                double asDouble = candlestick.stream().limit(i + 1).mapToDouble(e -> Double.valueOf(e.getAmount().toPlainString())).max().getAsDouble();
                if (amount == asDouble) {
                    f1 = true;
                    maxI = i;
                    break;
                }
            } else {
                continue;
            }
        }
        /********************************************************************************/

        Double d4 = Double.valueOf(candlestick.get(4).getClose().toPlainString());
        Double d04 = Double.valueOf(candlestick.get(4).getOpen().toPlainString());
        Double d3 = Double.valueOf(candlestick.get(3).getClose().toPlainString());
        Double d03 = Double.valueOf(candlestick.get(3).getOpen().toPlainString());
        Double d2 = Double.valueOf(candlestick.get(2).getClose().toPlainString());
        Double d02 = Double.valueOf(candlestick.get(2).getOpen().toPlainString());
        Double d1 = Double.valueOf(candlestick.get(1).getClose().toPlainString());
        Double d01 = Double.valueOf(candlestick.get(1).getOpen().toPlainString());
        Double d0 = Double.valueOf(candlestick.get(0).getClose().toPlainString());
        Double d00 = Double.valueOf(candlestick.get(0).getOpen().toPlainString());

        if (f1) {
            boolean t1 = d02 > d2;
            boolean t2 = d01 > d1;
            boolean t3 = (d00 - d0) / d00 > 0.10;
            if (t1 && t2 && t3) {
                return true;
            }
        }
        /********************************************************************************/
        if (f1) {
            boolean t2 = d01 > d1;
            boolean t3 = (d00 - d0) / d00 > 0.15;
            if (t2 && t3) {
                return true;
            }
        }
        /********************************************************************************/
        if (f1) {
            boolean t3 = (d00 - d0) / d00 > 0.25;
            if (t3) {
                return true;
            }
        }

        /********************************************************************************/

        if (f1) {
            boolean t4 = d04 > d4;
            boolean t3 = d03 > d3;
            boolean t2 = d02 > d2;
            boolean t1 = d01 > d1;
            boolean t0 = d00 > d0;
            if (t4 && t3 && t2 && t1 && t0) {
                return true;
            }
        }
        /********************************************************************************/
        if (f1) {
            boolean t3 = d03 > d3;
            boolean t2 = d02 > d2;
            boolean t1 = d01 > d1;
            boolean t0 = (d00 - d0) / d00 > 0.05;
            if (t3 && t2 && t1 && t0) {
                return true;
            }

        }
        /********************************************************************************/
        if (f1) {
            boolean t2 = (d02 - d2) / d02 > 0.10;
            boolean t1 = (d01 - d1) / d01 > 0.05;
            boolean t0 = (d00 - d0) / d00 > 0.05;
            if (t2 && t1 && t0) {
                return true;
            }
        }
        /********************************************************************************/
        if (f1) {
            boolean t1 = (d01 - d1) / d01 > 0.15;
            boolean t0 = (d00 - d0) / d00 > 0.1;
            if (t1 && t0) {
                return true;
            }
        }
        /********************************************************************************/
        if (f1) {
            boolean t3 = d03 > d3;
            boolean t2 = (d02 - d2) / d02 > 0.1;
            boolean t1 = (d1 - d01) / d01 > 0 && (d1 - d01) / d01 < 0.03;
            boolean t0 = d0 < d00;
            if (t3 && t2 && t1 && t0) {
                return true;
            }
        }
        /********************************************************************************/
        if (true) {
            boolean t4 = d04 > d4;
            boolean t3 = d03 > d3;
            boolean t2 = d02 > d2;
            boolean t1 = d01 > d1;
            boolean t0 = (d00 - d0) / d00 > 0.05;
            if (t4 && t3 && t2 && t1 && t0) {
                return true;
            }

            t3 = d03 > d3;
            t2 = d02 > d2;
            t1 = d01 > d1;
            t0 = (d00 - d0) / d00 > 0.1;
            if (t3 && t2 && t1 && t0) {
                return true;
            }

            t2 = d02 > d2;
            t1 = d01 > d1;
            t0 = (d00 - d0) / d00 > 0.15;
            if (t2 && t1 && t0) {
                return true;
            }

            t4 = d04 > d4;
            t3 = d03 > d3;
            t2 = d02 > d2 && (d04 - d2) / d04 > 0.2;
            t1 = (d1 - d01) / d01 > 0 && (d1 - d01) / d01 < 0.03;
            t0 = (d00 - d0) / d00 > 0.05;
            if (t4 && t3 && t2 && t1 && t0) {
                return true;
            }

            t0 = ((d0 - d00) / d00 > 0.01) && ((d0 - d00) / d00 < 0.05);
            if (t0) {
                t1 = d01 > d1 && (d01 - d1) / d01 > 0.06;
                if (t1) {
                    return true;
                }
                t1 = (d02 - d1) / d02 > 0.8;
                if (t1) {
                    return true;
                }
                t1 = (d03 - d1) / d02 > 0.12;
                if (t1) {
                    return true;
                }
                t1 = (d04 - d1) / d02 > 0.15;
                if (t1) {
                    return true;
                }
            }

        }
        /********************************************************************************/

        return false;
    }

}

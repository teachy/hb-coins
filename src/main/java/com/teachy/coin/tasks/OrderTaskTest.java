package com.teachy.coin.tasks;

import com.teachy.coin.huobi.HBApi;
import com.teachy.coin.huobi.client.req.trade.CreateOrderRequest;
import com.teachy.coin.huobi.constant.enums.CandlestickIntervalEnum;
import com.teachy.coin.huobi.exception.SDKException;
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
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName OrderTasks
 * @Author gang.tu
 * @Date 2021/1/9 16:38
 */
//@Component
@Slf4j
public class OrderTaskTest {
    @Resource
    CoinsSymbolsService soinsSymbolsService;
    @Resource
    CoinsBuyService coinsBuyService;
    @Resource
    CoinsMatchResultsService coinsMatchResultsService;
    @Resource
    HBApi hbApi;
    public static int MAX_COINS = 10;
    @Value("${coin.max}")
    public String BUY_MAX;
    public static String UU_ID = "null";
    List<CoinsSymbols> enableCoins = Collections.synchronizedList(new ArrayList<>());
    Map<String, String> coinNames = new HashMap<>();
    public static volatile List<CoinsSymbols> buyCoins = Collections.synchronizedList(new ArrayList<>());
    public static volatile List<CoinsSymbols> sellCoins = Collections.synchronizedList(new ArrayList<>());
    private Long accountId = 0L;
    Map<Double, CoinsSymbols> maxMap = new HashMap<>();
    Map<Double, CoinsSymbols> buyMap = new HashMap<>();
    boolean beginSell;

    @PostConstruct
    void init() {
        List<Account> accounts = hbApi.accountList();
        for (Account account : accounts) {
            if (account.getType().equals("spot")) {
                accountId = account.getId();
                log.info("id:" + account);
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
     * 功能描述 定时找寻有用数据
     *
     * @return void
     * @author gang.tu
     **/
//    @Scheduled(cron = "0 57 23 * * ?")
    @Scheduled(cron = "*/1 * * * * ?")
    public void begin() {
        log.info("开始买");
        beginSell = false;
        getKline();
    }

    /**
     * 功能描述 定时sell
     *
     * @return void
     * @author gang.tu
     **/
    @Scheduled(cron = "0 10 0 * * ?")
    public void sell() {
        beginSell = true;
        while (sellCoins.size() > 0) {//如果没有卖完将会一直卖  --无法退出
            dealSellCoins();
        }
        log.info("结束");
    }


//    @Scheduled(cron = "*/1 * * * * ?")//测试专用
//    public void test() {
//
//        enableCoins.stream().forEach(e -> {
//            try {
//                List<Candlestick> candlestick = hbApi.getCandlestick(e.getSymbol(), CandlestickIntervalEnum.MIN60, 1800);
//                int count = 0;
//                for (int i = candlestick.size() - 1; i >= 0; i--) {
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//                    long lt = new Long(candlestick.get(i).getId());
//                    lt = lt * 1000;
//                    Date date = new Date(lt);
//                    String res = simpleDateFormat.format(date);
//                    if (res.startsWith("00")) {
//                        Double hi = Double.valueOf(candlestick.get(i).getHigh().toPlainString());
//                        Double open = Double.valueOf(candlestick.get(i).getOpen().toPlainString());
//                        if ((hi - open) / open > 0.3) {
//                            count++;
//                        }
//                    }
//                }
//                if (count == 0) {
//                    soinsSymbolsService.disableCoin(e.getBaseCurrency());
//                }
//                System.out.println(e.getBaseCurrency() + ":" + count);
//                Thread.sleep(100);
//            } catch (Exception interruptedException) {
//                interruptedException.printStackTrace();
//            }
//        });
//
//    }

    /**
     * 功能描述 K线分析
     *
     * @return void
     * @author gang.tu
     **/
    private void getKline() {
        enableCoins.stream().forEach(e -> {
            try {
                List<Candlestick> candlestick = hbApi.getCandlestick(e.getSymbol(), CandlestickIntervalEnum.DAY1, 60);
                candlestick=candlestick.stream().skip(1).collect(toList());
                Double aDouble = checkAmount(candlestick);
                if (maxMap.size() < 15) {
                    maxMap.put(aDouble, e);
                } else {
                    Set<Double> keys = maxMap.keySet();
                    Double min = keys.stream().mapToDouble(eve -> eve).min().getAsDouble();
                    if (min < aDouble) {
                        if (buyMap.size() < 10) {
                            buyMap.put(min, maxMap.get(min));
                        } else {
                            Set<Double> keys1 = buyMap.keySet();
                            Double min1 = keys1.stream().mapToDouble(eve -> eve).min().getAsDouble();
                            if (min1 < min) {
                                buyMap.remove(min1);
                                buyMap.put(min, maxMap.get(min));
                            }
                        }
                        maxMap.remove(min);
                        maxMap.put(aDouble, e);
                    }
                }
                Thread.sleep(100);
            } catch (Exception interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        buyMap.values().stream().forEach(e -> System.out.println(e.getBaseCurrency()));
//        dealBuyCoins();
//        buyMap.clear();
//        maxMap.clear();
//        buyCoins.clear();
//        while (!beginSell) {
//            sellFirst();
//        }
        log.info("退出卖出点");
    }


    void dealSellCoins() {
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
                    double buyPrice = Double.valueOf(coinsBuy.getPrice());
                    if (nowPrice < sellPrice || nowPrice > buyPrice * 1.03) {
                        List<Balance> list = hbApi.getBalances(accountId).stream().filter(e -> coinNames.containsKey(e.getCurrency())).collect(toList());
                        for (Balance balance : list) {
                            if (balance.getCurrency().equals(next.getBaseCurrency())) {
                                doSell(next, balance);
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

    void sellFirst() {
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
                    double buyPrice = Double.valueOf(coinsBuy.getPrice());
                    if (nowPrice > sellPrice && nowPrice > buyPrice * 0.97) {
                        if (nowPrice > sellPrice * 1.08) {
                            coinsBuy.setSellPrice(marketTrade.getPrice().multiply(new BigDecimal("0.97")).toPlainString());
                            coinsBuyService.update(coinsBuy);
                        }
                    } else {
                        List<Balance> list = hbApi.getBalances(accountId).stream().filter(e -> coinNames.containsKey(e.getCurrency())).collect(toList());
                        for (Balance balance : list) {
                            if (balance.getCurrency().equals(next.getBaseCurrency())) {
                                doSell(next, balance);
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


    void doSell(CoinsSymbols coinsSymbols, Balance balance) {
        log.info("sell coin：{}", coinsSymbols.getBaseCurrency());
        CreateOrderRequest createOrderRequest = CreateOrderRequest.spotSellMarket(accountId, coinsSymbols.getSymbol(), balance.getBalance().setScale(coinsSymbols.getAmountPrecision(), BigDecimal.ROUND_DOWN));
        try {
            Long sellMarketId = hbApi.createOrder(createOrderRequest);
            insertResults(coinsSymbols, sellMarketId);
            coinsBuyService.deleteBySymbol(coinsSymbols.getSymbol());
        } catch (SDKException e) {
            if (e.getMessage().contains("amount")) {
                coinsBuyService.deleteBySymbol(coinsSymbols.getSymbol());
            }
        }
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


    void dealBuyCoins() {
        for (CoinsSymbols e : buyCoins) {
            CreateOrderRequest createOrderRequest = CreateOrderRequest.spotBuyMarket(accountId, e.getSymbol(), new BigDecimal(BUY_MAX));
            Long buyMarketId = hbApi.createOrder(createOrderRequest);
            try {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            List<CoinsMatchResults> results = insertResults(e, buyMarketId);
            CoinsBuy coinsBuy = new CoinsBuy();
            coinsBuy.setSymbol(e.getSymbol());
            coinsBuy.setBaseUrrency(e.getBaseCurrency());
            coinsBuy.setPrice(results.get(0).getPrice());
            coinsBuy.setSellPrice(new BigDecimal(coinsBuy.getPrice()).multiply(new BigDecimal("0.97")).toPlainString());
            coinsBuyService.save(coinsBuy);
            sellCoins.add(e);
        }
    }

    private Double checkAmount(List<Candlestick> candlestick) {
        int count = 0;
        Double now = Double.valueOf(candlestick.get(0).getClose().toPlainString());
        List<Double> all = new ArrayList<>();
        candlestick.stream().forEach(e -> {
            all.add(Double.valueOf(e.getOpen().toPlainString()));
            all.add(Double.valueOf(e.getClose().toPlainString()));
        });
        Double max = all.stream().mapToDouble(e -> e).max().getAsDouble();
        Double min = all.stream().mapToDouble(e -> e).min().getAsDouble();
        double res = (max - now) / (max - min);
        BigDecimal bg = new BigDecimal(res);
        double f1 = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue() * 100;
        for (int i = candlestick.size() - 1; i >= 0; i--) {
            Double hi = Double.valueOf(candlestick.get(i).getHigh().toPlainString());
            Double open = Double.valueOf(candlestick.get(i).getOpen().toPlainString());
            Double close = Double.valueOf(candlestick.get(i).getClose().toPlainString());
            if ((hi - open) / open > 0.3) {
                count++;
            }
            if ((close - open) / open > 0.25) {
                count--;
            }
        }
        return f1 + count;
    }

}

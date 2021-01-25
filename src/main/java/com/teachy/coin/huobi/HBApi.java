package com.teachy.coin.huobi;

import com.teachy.coin.huobi.client.AccountClient;
import com.teachy.coin.huobi.client.GenericClient;
import com.teachy.coin.huobi.client.MarketClient;
import com.teachy.coin.huobi.client.TradeClient;
import com.teachy.coin.huobi.client.req.account.AccountBalanceRequest;
import com.teachy.coin.huobi.client.req.market.CandlestickRequest;
import com.teachy.coin.huobi.client.req.market.MarketDetailMergedRequest;
import com.teachy.coin.huobi.client.req.market.MarketTradeRequest;
import com.teachy.coin.huobi.client.req.trade.CreateOrderRequest;
import com.teachy.coin.huobi.constant.Constants;
import com.teachy.coin.huobi.constant.HuobiOptions;
import com.teachy.coin.huobi.constant.enums.CandlestickIntervalEnum;
import com.teachy.coin.huobi.model.account.Account;
import com.teachy.coin.huobi.model.account.AccountBalance;
import com.teachy.coin.huobi.model.account.Balance;
import com.teachy.coin.huobi.model.generic.Symbol;
import com.teachy.coin.huobi.model.market.Candlestick;
import com.teachy.coin.huobi.model.market.MarketDetailMerged;
import com.teachy.coin.huobi.model.market.MarketTicker;
import com.teachy.coin.huobi.model.market.MarketTrade;
import com.teachy.coin.huobi.model.trade.MatchResult;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName HBApi
 * @Description TODO
 * @Author gang.tu
 * @Date 2021/1/9 14:38
 */
@Component
public class HBApi {

    private static GenericClient genericService = GenericClient.create(HuobiOptions.builder().build());
    private static MarketClient marketClient = MarketClient.create(new HuobiOptions());

    private static TradeClient tradeService = TradeClient.create(HuobiOptions.builder()
            .apiKey(Constants.API_KEY)
            .secretKey(Constants.SECRET_KEY)
            .build());

    AccountClient accountService = AccountClient.create(HuobiOptions.builder()
            .apiKey(Constants.API_KEY)
            .secretKey(Constants.SECRET_KEY)
            .build());

    /**
     * 功能描述 获取所有交易对 GET /v1/common/symbols
     *
     * @return void
     * @author gang.tu
     **/
    public List<Symbol> getSymbols() {
        return genericService.getSymbols();
    }

    /**
     * 功能描述 K线数据
     *
     * @return void
     * @author gang.tu
     **/
    public List<Candlestick> getCandlestick(String symbol, CandlestickIntervalEnum candlestickIntervalEnum, Integer size) {
        return marketClient.getCandlestick(CandlestickRequest.builder()
                .symbol(symbol)
                .interval(candlestickIntervalEnum)
                .size(size)
                .build());
    }

    /**
     * 功能描述 行情数据
     *
     * @return void
     * @author gang.tu
     **/
    public MarketDetailMerged getMarketDetailMerged(String symbol) {
        return marketClient.getMarketDetailMerged(MarketDetailMergedRequest.builder().symbol(symbol).build());
    }

    /**
     * 功能描述 所有币种统计信息
     *
     * @return void
     * @author gang.tu
     **/
    public List<MarketTicker> getTickers() {
        return marketClient.getTickers();
    }

    /**
     * 功能描述 创建订单
     *
     * @author gang.tu
     **/
    public Long createOrder(CreateOrderRequest request) {
        return tradeService.createOrder(request);
    }

    /**
     * 功能描述 查询用户id
     *
     * @author gang.tu
     **/
    public List<Account> accountList() {
        return accountService.getAccounts();
    }


    /**
     * 功能描述 查询指定账户的余额
     *
     * @author gang.tu
     **/
    public AccountBalance getAccountBalance(Long accountId) {
        return accountService.getAccountBalance(AccountBalanceRequest.builder()
                .accountId(accountId)
                .build());
    }

    /**
     * 功能描述 指定交易对最新的一个交易记录
     *
     * @author gang.tu
     **/
    public List<MarketTrade> getMarketTrade(String symbol) {
        return marketClient.getMarketTrade(MarketTradeRequest.builder().symbol(symbol).build());
    }

    /**
     * 功能描述 指定交易对最新的一个交易记录
     *
     * @author gang.tu
     **/
    public List<MatchResult> getMatchResult(Long orderId) {
        return tradeService.getMatchResult(orderId);
    }

    /**
     * 功能描述 获取余额大于0的
     *
     * @author gang.tu
     **/
    public List<Balance> getBalances(Long orderId) {
        return getAccountBalance(orderId).getList().stream().filter(e -> e.getType().equals("trade") && Double.valueOf(e.getBalance().toPlainString()) > 0).collect(toList());
    }
}

package com.teachy.coin.huobi.client;

import java.util.List;

import com.teachy.coin.huobi.client.req.market.CandlestickRequest;
import com.teachy.coin.huobi.client.req.market.MarketDepthRequest;
import com.teachy.coin.huobi.client.req.market.MarketDetailMergedRequest;
import com.teachy.coin.huobi.client.req.market.MarketDetailRequest;
import com.teachy.coin.huobi.client.req.market.MarketHistoryTradeRequest;
import com.teachy.coin.huobi.client.req.market.MarketTradeRequest;
import com.teachy.coin.huobi.client.req.market.ReqCandlestickRequest;
import com.teachy.coin.huobi.client.req.market.ReqMarketDepthRequest;
import com.teachy.coin.huobi.client.req.market.ReqMarketDetailRequest;
import com.teachy.coin.huobi.client.req.market.ReqMarketTradeRequest;
import com.teachy.coin.huobi.client.req.market.SubCandlestickRequest;
import com.teachy.coin.huobi.client.req.market.SubMarketBBORequest;
import com.teachy.coin.huobi.client.req.market.SubMarketDepthRequest;
import com.teachy.coin.huobi.client.req.market.SubMarketDetailRequest;
import com.teachy.coin.huobi.client.req.market.SubMarketTradeRequest;
import com.teachy.coin.huobi.client.req.market.SubMbpIncrementalUpdateRequest;
import com.teachy.coin.huobi.client.req.market.SubMbpRefreshUpdateRequest;
import com.teachy.coin.huobi.constant.Options;
import com.teachy.coin.huobi.constant.enums.ExchangeEnum;
import com.teachy.coin.huobi.exception.SDKException;
import com.teachy.coin.huobi.model.market.Candlestick;
import com.teachy.coin.huobi.model.market.CandlestickEvent;
import com.teachy.coin.huobi.model.market.CandlestickReq;
import com.teachy.coin.huobi.model.market.MarketBBOEvent;
import com.teachy.coin.huobi.model.market.MarketDepth;
import com.teachy.coin.huobi.model.market.MarketDepthEvent;
import com.teachy.coin.huobi.model.market.MarketDepthReq;
import com.teachy.coin.huobi.model.market.MarketDetail;
import com.teachy.coin.huobi.model.market.MarketDetailEvent;
import com.teachy.coin.huobi.model.market.MarketDetailMerged;
import com.teachy.coin.huobi.model.market.MarketDetailReq;
import com.teachy.coin.huobi.model.market.MarketTicker;
import com.teachy.coin.huobi.model.market.MarketTrade;
import com.teachy.coin.huobi.model.market.MarketTradeEvent;
import com.teachy.coin.huobi.model.market.MarketTradeReq;
import com.teachy.coin.huobi.model.market.MbpIncrementalUpdateEvent;
import com.teachy.coin.huobi.model.market.MbpRefreshUpdateEvent;
import com.teachy.coin.huobi.service.huobi.HuobiMarketService;
import com.teachy.coin.huobi.service.huobi.connection.HuobiWebSocketConnection;
import com.teachy.coin.huobi.utils.ResponseCallback;
import com.teachy.coin.huobi.utils.WebSocketConnection;

public interface MarketClient {

  List<Candlestick> getCandlestick(CandlestickRequest request);

  MarketDetailMerged getMarketDetailMerged(MarketDetailMergedRequest request);

  MarketDetail getMarketDetail(MarketDetailRequest request);

  List<MarketTicker> getTickers();

  MarketDepth getMarketDepth(MarketDepthRequest request);

  List<MarketTrade> getMarketTrade(MarketTradeRequest request);

  List<MarketTrade> getMarketHistoryTrade(MarketHistoryTradeRequest request);

  void subCandlestick(SubCandlestickRequest request, ResponseCallback<CandlestickEvent> callback);

  void subMarketDetail(SubMarketDetailRequest request, ResponseCallback<MarketDetailEvent> callback);

  void subMarketDepth(SubMarketDepthRequest request, ResponseCallback<MarketDepthEvent> callback);

  void subMarketTrade(SubMarketTradeRequest request, ResponseCallback<MarketTradeEvent> callback);

  void subMarketBBO(SubMarketBBORequest request, ResponseCallback<MarketBBOEvent> callback);

  void subMbpRefreshUpdate(SubMbpRefreshUpdateRequest request, ResponseCallback<MbpRefreshUpdateEvent> callback);

  WebSocketConnection subMbpIncrementalUpdate(SubMbpIncrementalUpdateRequest request, ResponseCallback<MbpIncrementalUpdateEvent> callback);

  WebSocketConnection reqMbpIncrementalUpdate(SubMbpIncrementalUpdateRequest request, WebSocketConnection connection);

  void reqCandlestick(ReqCandlestickRequest request, ResponseCallback<CandlestickReq> callback);

  void reqMarketDepth(ReqMarketDepthRequest request, ResponseCallback<MarketDepthReq> callback);

  void reqMarketTrade(ReqMarketTradeRequest request, ResponseCallback<MarketTradeReq> callback);

  void reqMarketDetail(ReqMarketDetailRequest request, ResponseCallback<MarketDetailReq> callback);

  static MarketClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiMarketService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }


}

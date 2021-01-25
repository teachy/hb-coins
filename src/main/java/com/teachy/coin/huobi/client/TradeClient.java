package com.teachy.coin.huobi.client;

import java.util.List;

import com.teachy.coin.huobi.client.req.trade.BatchCancelOpenOrdersRequest;
import com.teachy.coin.huobi.client.req.trade.CreateOrderRequest;
import com.teachy.coin.huobi.client.req.trade.FeeRateRequest;
import com.teachy.coin.huobi.client.req.trade.MatchResultRequest;
import com.teachy.coin.huobi.client.req.trade.OpenOrdersRequest;
import com.teachy.coin.huobi.client.req.trade.OrderHistoryRequest;
import com.teachy.coin.huobi.client.req.trade.OrdersRequest;
import com.teachy.coin.huobi.client.req.trade.ReqOrderListRequest;
import com.teachy.coin.huobi.client.req.trade.SubOrderUpdateRequest;
import com.teachy.coin.huobi.client.req.trade.SubOrderUpdateV2Request;
import com.teachy.coin.huobi.client.req.trade.SubTradeClearingRequest;
import com.teachy.coin.huobi.constant.Options;
import com.teachy.coin.huobi.constant.enums.ExchangeEnum;
import com.teachy.coin.huobi.exception.SDKException;
import com.teachy.coin.huobi.model.trade.BatchCancelOpenOrdersResult;
import com.teachy.coin.huobi.model.trade.BatchCancelOrderResult;
import com.teachy.coin.huobi.model.trade.FeeRate;
import com.teachy.coin.huobi.model.trade.MatchResult;
import com.teachy.coin.huobi.model.trade.Order;
import com.teachy.coin.huobi.model.trade.OrderDetailReq;
import com.teachy.coin.huobi.model.trade.OrderListReq;
import com.teachy.coin.huobi.model.trade.OrderUpdateEvent;
import com.teachy.coin.huobi.model.trade.OrderUpdateV2Event;
import com.teachy.coin.huobi.model.trade.TradeClearingEvent;
import com.teachy.coin.huobi.service.huobi.HuobiTradeService;
import com.teachy.coin.huobi.utils.ResponseCallback;

public interface TradeClient {

  Long createOrder(CreateOrderRequest request);

  Long cancelOrder(Long orderId);

  Integer cancelOrder(String clientOrderId);

  BatchCancelOpenOrdersResult batchCancelOpenOrders(BatchCancelOpenOrdersRequest request);

  BatchCancelOrderResult batchCancelOrder(List<Long> ids);

  List<Order> getOpenOrders(OpenOrdersRequest request);

  Order getOrder(Long orderId);

  Order getOrder(String clientOrderId);

  List<Order> getOrders(OrdersRequest request);

  List<Order> getOrdersHistory(OrderHistoryRequest request);

  List<MatchResult> getMatchResult(Long orderId);

  List<MatchResult> getMatchResults(MatchResultRequest request);

  List<FeeRate> getFeeRate(FeeRateRequest request);

  void subOrderUpdateV2(SubOrderUpdateV2Request request, ResponseCallback<OrderUpdateV2Event> callback);

  void subTradeClearing(SubTradeClearingRequest request, ResponseCallback<TradeClearingEvent> callback);

  static TradeClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiTradeService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }

}

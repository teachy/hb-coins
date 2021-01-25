package com.teachy.coin.huobi.client;

import com.teachy.coin.huobi.client.req.algo.CancelAlgoOrderRequest;
import com.teachy.coin.huobi.client.req.algo.CreateAlgoOrderRequest;
import com.teachy.coin.huobi.client.req.algo.GetHistoryAlgoOrdersRequest;
import com.teachy.coin.huobi.client.req.algo.GetOpenAlgoOrdersRequest;
import com.teachy.coin.huobi.constant.Options;
import com.teachy.coin.huobi.constant.enums.ExchangeEnum;
import com.teachy.coin.huobi.exception.SDKException;
import com.teachy.coin.huobi.model.algo.AlgoOrder;
import com.teachy.coin.huobi.model.algo.CancelAlgoOrderResult;
import com.teachy.coin.huobi.model.algo.CreateAlgoOrderResult;
import com.teachy.coin.huobi.model.algo.GetHistoryAlgoOrdersResult;
import com.teachy.coin.huobi.model.algo.GetOpenAlgoOrdersResult;
import com.teachy.coin.huobi.service.huobi.HuobiAlgoService;

public interface AlgoClient {

  CreateAlgoOrderResult createAlgoOrder(CreateAlgoOrderRequest request);

  CancelAlgoOrderResult cancelAlgoOrder(CancelAlgoOrderRequest request);

  GetOpenAlgoOrdersResult getOpenAlgoOrders(GetOpenAlgoOrdersRequest request);

  GetHistoryAlgoOrdersResult getHistoryAlgoOrders(GetHistoryAlgoOrdersRequest request);

  AlgoOrder getAlgoOrdersSpecific(String clientOrderId);


  static AlgoClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiAlgoService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}

package com.teachy.coin.huobi.client;

import java.util.List;

import com.teachy.coin.huobi.client.req.etf.ETFSwapListRequest;
import com.teachy.coin.huobi.client.req.etf.ETFSwapRequest;
import com.teachy.coin.huobi.model.etf.ETFConfig;
import com.teachy.coin.huobi.model.etf.ETFSwapRecord;

public interface ETFClient {

  ETFConfig getConfig(String etfName);

  void etfSwap(ETFSwapRequest request);

  List<ETFSwapRecord> getEtfSwapList(ETFSwapListRequest request);

}

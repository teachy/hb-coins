package com.teachy.coin.huobi.service.huobi.parser.algo;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.teachy.coin.huobi.model.algo.GetHistoryAlgoOrdersResult;
import com.teachy.coin.huobi.service.huobi.parser.HuobiModelParser;

public class GetHistoryAlgoOrdersResultParser implements HuobiModelParser<GetHistoryAlgoOrdersResult> {

  @Override
  public GetHistoryAlgoOrdersResult parse(JSONObject json) {
    return GetHistoryAlgoOrdersResult.builder()
        .list(new AlgoOrderParser().parseArray(json.getJSONArray("data")))
        .nextId(json.getLong("nextId"))
        .build();
  }

  @Override
  public GetHistoryAlgoOrdersResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<GetHistoryAlgoOrdersResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}

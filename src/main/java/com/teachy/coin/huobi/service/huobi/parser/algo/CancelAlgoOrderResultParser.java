package com.teachy.coin.huobi.service.huobi.parser.algo;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.teachy.coin.huobi.model.algo.CancelAlgoOrderResult;
import com.teachy.coin.huobi.service.huobi.parser.HuobiModelParser;

public class CancelAlgoOrderResultParser implements HuobiModelParser<CancelAlgoOrderResult> {

  @Override
  public CancelAlgoOrderResult parse(JSONObject json) {
    return json.toJavaObject(CancelAlgoOrderResult.class);
  }

  @Override
  public CancelAlgoOrderResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<CancelAlgoOrderResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}

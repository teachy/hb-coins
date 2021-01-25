package com.teachy.coin.huobi.service.huobi.parser.subuser;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.teachy.coin.huobi.model.subuser.GetSubUserDepositResult;
import com.teachy.coin.huobi.service.huobi.parser.HuobiModelParser;

public class GetSubUserDepositResultParser implements HuobiModelParser<GetSubUserDepositResult> {

  @Override
  public GetSubUserDepositResult parse(JSONObject json) {
    return GetSubUserDepositResult.builder()
        .nextId(json.getLong("nextId"))
        .list(new SubUserDepositParser().parseArray(json.getJSONArray("data")))
        .build();
  }

  @Override
  public GetSubUserDepositResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<GetSubUserDepositResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}

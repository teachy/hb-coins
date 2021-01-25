package com.teachy.coin.huobi.service.huobi.parser.subuser;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.teachy.coin.huobi.model.subuser.SubUserTransferabilityResult;
import com.teachy.coin.huobi.service.huobi.parser.HuobiModelParser;

public class SubUserTransferabilityResultParser implements HuobiModelParser<SubUserTransferabilityResult> {

  @Override
  public SubUserTransferabilityResult parse(JSONObject json) {
    return SubUserTransferabilityResult.builder()
        .list(new SubUserTransferabilityStateParser().parseArray(json.getJSONArray("data")))
        .build();
  }

  @Override
  public SubUserTransferabilityResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<SubUserTransferabilityResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}

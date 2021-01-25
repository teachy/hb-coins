package com.teachy.coin.huobi.service.huobi.parser.subuser;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.teachy.coin.huobi.model.subuser.GetApiKeyListResult;
import com.teachy.coin.huobi.service.huobi.parser.HuobiModelParser;

public class GetApiKeyListResultParser implements HuobiModelParser<GetApiKeyListResult> {


  @Override
  public GetApiKeyListResult parse(JSONObject json) {
    return GetApiKeyListResult.builder()
        .list(new ApiKeyInfoParser().parseArray(json.getJSONArray("data")))
        .build();
  }

  @Override
  public GetApiKeyListResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<GetApiKeyListResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}

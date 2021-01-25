package com.teachy.coin.huobi.service.huobi.parser.subuser;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.teachy.coin.huobi.model.subuser.SubUserCreationInfo;
import com.teachy.coin.huobi.service.huobi.parser.HuobiModelParser;

public class SubUserCreationInfoParser implements HuobiModelParser<SubUserCreationInfo> {

  @Override
  public SubUserCreationInfo parse(JSONObject json) {
    return null;
  }

  @Override
  public SubUserCreationInfo parse(JSONArray json) {
    return null;
  }

  @Override
  public List<SubUserCreationInfo> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(SubUserCreationInfo.class);
  }
}

package com.teachy.coin.huobi.client.req.market;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.teachy.coin.huobi.constant.enums.DepthLevels;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubMbpRefreshUpdateRequest {

  private String symbols;

  private DepthLevels levels;

}

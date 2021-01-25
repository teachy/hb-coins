package com.teachy.coin.huobi.client.req.crossmargin;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.teachy.coin.huobi.constant.enums.MarginTransferDirectionEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrossMarginTransferRequest {

  private MarginTransferDirectionEnum direction;

  private String currency;

  private BigDecimal amount;

}

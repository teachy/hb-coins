package com.teachy.coin.huobi.model.subuser;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubUserAccountInfo {

  private String accountType;

  private String activation;

  private Boolean transferrable;

  private List<SubUserAccount> accountIds;

}

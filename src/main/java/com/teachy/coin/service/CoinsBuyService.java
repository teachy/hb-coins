package com.teachy.coin.service;
import com.teachy.coin.model.CoinsBuy;
import com.teachy.coin.core.Service;


/**
 * Created by CodeGenerator on 2021/01/11.
 */
public interface CoinsBuyService extends Service<CoinsBuy> {
   void deleteBySymbol(String symbol);
}

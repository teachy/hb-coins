package com.teachy.coin.dao;

import com.teachy.coin.core.Mapper;
import com.teachy.coin.model.CoinsBuy;
import org.apache.ibatis.annotations.Param;

public interface CoinsBuyMapper extends Mapper<CoinsBuy> {
    void deleteBySymbol(@Param("symbol") String symbol);
}
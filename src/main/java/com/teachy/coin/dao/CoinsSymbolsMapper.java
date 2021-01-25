package com.teachy.coin.dao;

import com.teachy.coin.core.Mapper;
import com.teachy.coin.model.CoinsSymbols;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CoinsSymbolsMapper extends Mapper<CoinsSymbols> {
    CoinsSymbols getBySymbols(@Param("symbol") String symbol);

    void disableCoin(@Param("coinName")String coinName);

    void enableCoin(@Param("coinName")String coinName);

    List<CoinsSymbols> getEnableCoins();
}
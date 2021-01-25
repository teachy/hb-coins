package com.teachy.coin.service;
import com.teachy.coin.model.CoinsSymbols;
import com.teachy.coin.core.Service;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by CodeGenerator on 2021/01/09.
 */
public interface CoinsSymbolsService extends Service<CoinsSymbols> {
    void updateSymbols();

    void disableCoin(String coinName);

    void enableCoin(String coinName);

    List<CoinsSymbols> getEnableCoins();
}

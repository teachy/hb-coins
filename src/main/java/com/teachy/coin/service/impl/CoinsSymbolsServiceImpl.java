package com.teachy.coin.service.impl;

import com.teachy.coin.core.AbstractService;
import com.teachy.coin.dao.CoinsSymbolsMapper;
import com.teachy.coin.huobi.HBApi;
import com.teachy.coin.huobi.model.generic.Symbol;
import com.teachy.coin.model.CoinsSymbols;
import com.teachy.coin.service.CoinsSymbolsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2021/01/09.
 */
@Service
@Transactional
public class CoinsSymbolsServiceImpl extends AbstractService<CoinsSymbols> implements CoinsSymbolsService {
    @Resource
    private CoinsSymbolsMapper coinsSymbolsMapper;
    @Resource
    HBApi hbApi;

    @Override
    public void updateSymbols() {
        List<Symbol> symbols = hbApi.getSymbols();
        symbols.stream().forEach(symbol -> {
            CoinsSymbols coinsSymbols = new CoinsSymbols();
            BeanUtils.copyProperties(symbol, coinsSymbols);
            CoinsSymbols coinsSymbol = coinsSymbolsMapper.getBySymbols(coinsSymbols.getSymbol());
            if (coinsSymbol == null) {
                if(symbol.getQuoteCurrency().equals("usdt")){
                    coinsSymbols.setStatus(0);
                    coinsSymbolsMapper.insert(coinsSymbols);
                }
            } else {
                coinsSymbols.setId(coinsSymbol.getId());
                coinsSymbols.setStatus(coinsSymbol.getStatus());
                coinsSymbolsMapper.updateByPrimaryKey(coinsSymbols);
            }
        });

    }

    @Override
    public void disableCoin(String coinName) {
        coinsSymbolsMapper.disableCoin(coinName);
    }

    @Override
    public void enableCoin(String coinName) {
        coinsSymbolsMapper.enableCoin(coinName);
    }

    @Override
    public List<CoinsSymbols> getEnableCoins() {
        return coinsSymbolsMapper.getEnableCoins();
    }
}

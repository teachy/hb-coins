package com.teachy.coin.service.impl;

import com.teachy.coin.dao.CoinsBuyMapper;
import com.teachy.coin.model.CoinsBuy;
import com.teachy.coin.service.CoinsBuyService;
import com.teachy.coin.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2021/01/11.
 */
@Service
@Transactional
public class CoinsBuyServiceImpl extends AbstractService<CoinsBuy> implements CoinsBuyService {
    @Resource
    private CoinsBuyMapper coinsBuyMapper;

    @Override
    public void deleteBySymbol(String symbol) {
        coinsBuyMapper.deleteBySymbol(symbol);
    }
}

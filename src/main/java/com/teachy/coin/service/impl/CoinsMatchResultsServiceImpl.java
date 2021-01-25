package com.teachy.coin.service.impl;

import com.teachy.coin.dao.CoinsMatchResultsMapper;
import com.teachy.coin.model.CoinsMatchResults;
import com.teachy.coin.service.CoinsMatchResultsService;
import com.teachy.coin.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2021/01/11.
 */
@Service
@Transactional
public class CoinsMatchResultsServiceImpl extends AbstractService<CoinsMatchResults> implements CoinsMatchResultsService {
    @Resource
    private CoinsMatchResultsMapper coinsMatchResultsMapper;

}

package com.teachy.coin.web;
import com.teachy.coin.core.Result;
import com.teachy.coin.core.ResultGenerator;
import com.teachy.coin.model.CoinsMatchResults;
import com.teachy.coin.service.CoinsMatchResultsService;
import io.swagger.annotations.ApiOperation;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2021/01/11.
*/
@RestController
@RequestMapping("/coins/match/results")
public class CoinsMatchResultsController {
    @Resource
    private CoinsMatchResultsService coinsMatchResultsService;

    @ApiOperation("新增")
    @PostMapping("/add")
    public Result add(CoinsMatchResults coinsMatchResults) {
        coinsMatchResultsService.save(coinsMatchResults);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation("删除")
    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        coinsMatchResultsService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Result update(CoinsMatchResults coinsMatchResults) {
        coinsMatchResultsService.update(coinsMatchResults);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation("获取详情")
    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        CoinsMatchResults coinsMatchResults = coinsMatchResultsService.findById(id);
        return ResultGenerator.genSuccessResult(coinsMatchResults);
    }

    @ApiOperation("获取列表")
    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<CoinsMatchResults> list = coinsMatchResultsService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}

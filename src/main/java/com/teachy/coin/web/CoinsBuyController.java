package com.teachy.coin.web;
import com.teachy.coin.core.Result;
import com.teachy.coin.core.ResultGenerator;
import com.teachy.coin.model.CoinsBuy;
import com.teachy.coin.service.CoinsBuyService;
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
@RequestMapping("/coins/buy")
public class CoinsBuyController {
    @Resource
    private CoinsBuyService coinsBuyService;

    @ApiOperation("新增")
    @PostMapping("/add")
    public Result add(CoinsBuy coinsBuy) {
        coinsBuyService.save(coinsBuy);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation("删除")
    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        coinsBuyService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Result update(CoinsBuy coinsBuy) {
        coinsBuyService.update(coinsBuy);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation("获取详情")
    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        CoinsBuy coinsBuy = coinsBuyService.findById(id);
        return ResultGenerator.genSuccessResult(coinsBuy);
    }

    @ApiOperation("获取列表")
    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<CoinsBuy> list = coinsBuyService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}

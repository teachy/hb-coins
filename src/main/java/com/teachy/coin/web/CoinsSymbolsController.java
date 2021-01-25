package com.teachy.coin.web;

import com.teachy.coin.core.Result;
import com.teachy.coin.core.ResultGenerator;
import com.teachy.coin.service.CoinsSymbolsService;
import com.teachy.coin.tasks.OrderTasks;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeGenerator on 2021/01/09.
 */
@RestController
@RequestMapping("/coins/symbols")
public class CoinsSymbolsController {
    @Resource
    private CoinsSymbolsService coinsSymbolsService;
    @Resource
    OrderTasks orderTasks;
//    @ApiOperation("新增")
//    @PostMapping("/add")
//    public Result add(CoinsSymbols coinsSymbols) {
//        coinsSymbolsService.save(coinsSymbols);
//        return ResultGenerator.genSuccessResult();
//    }
//
//    @ApiOperation("删除")
//    @PostMapping("/delete")
//    public Result delete(@RequestParam Integer id) {
//        coinsSymbolsService.deleteById(id);
//        return ResultGenerator.genSuccessResult();
//    }
//
//    @ApiOperation("修改")
//    @PostMapping("/update")
//    public Result update(CoinsSymbols coinsSymbols) {
//        coinsSymbolsService.update(coinsSymbols);
//        return ResultGenerator.genSuccessResult();
//    }
//
//    @ApiOperation("获取详情")
//    @PostMapping("/detail")
//    public Result detail(@RequestParam Integer id) {
//        CoinsSymbols coinsSymbols = coinsSymbolsService.findById(id);
//        return ResultGenerator.genSuccessResult(coinsSymbols);
//    }
//
//    @ApiOperation("获取列表")
//    @PostMapping("/list")
//    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
//        PageHelper.startPage(page, size);
//        List<CoinsSymbols> list = coinsSymbolsService.findAll();
//        PageInfo pageInfo = new PageInfo(list);
//        return ResultGenerator.genSuccessResult(pageInfo);
//    }

    @ApiOperation("更新基础数据")
    @GetMapping("/updateSymbols")
    public Result updateSymbols() {
        coinsSymbolsService.updateSymbols();
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation("禁用指定的代币")
    @GetMapping("/disableCoin")
    public Result disableCoin(@RequestParam String coinName) {
        coinsSymbolsService.disableCoin(coinName);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation("启用指定的代币(usdt交易对)")
    @GetMapping("/enableCoin")
    public Result enableCoin(@RequestParam String coinName) {
        coinsSymbolsService.enableCoin(coinName);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation("系统基本信息")
    @GetMapping("/base")
    public Result base() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("sellCoins", orderTasks.sellCoins);
        objectMap.put("isOn", orderTasks.isOn);
        objectMap.put("MAX_COINS", orderTasks.MAX_COINS);
        objectMap.put("BUY_MAX", orderTasks.BUY_MAX);
        objectMap.put("uuid", orderTasks.UU_ID);
        return ResultGenerator.genSuccessResult(objectMap);
    }

    @ApiOperation("更新系统基本信息")
    @PostMapping("/updateBase")
    public Result updateBase(@RequestBody Map<String, String> objectMap) {
        String pass = objectMap.get("pass");
        if (pass == null || !"tu19891113".equals(pass)) {
            return ResultGenerator.genSuccessResult();
        }
        orderTasks.isOn = Boolean.parseBoolean(objectMap.get("isOn"));
        orderTasks.MAX_COINS = Integer.parseInt(objectMap.get("MAX_COINS"));
        orderTasks.BUY_MAX = objectMap.get("BUY_MAX");
        return ResultGenerator.genSuccessResult("success");
    }

//    @ApiOperation("更新初始化数据")
//    @GetMapping("/updateInit")
//    public Result updateInit() {
//        orderTasks.initSellCoins();
//        return ResultGenerator.genSuccessResult();
//    }
}

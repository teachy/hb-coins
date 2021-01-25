package com.teachy.coin.huobi.client;

import java.util.List;

import com.teachy.coin.huobi.client.req.margin.IsolatedMarginAccountRequest;
import com.teachy.coin.huobi.client.req.margin.IsolatedMarginApplyLoanRequest;
import com.teachy.coin.huobi.client.req.margin.IsolatedMarginLoanInfoRequest;
import com.teachy.coin.huobi.client.req.margin.IsolatedMarginLoanOrdersRequest;
import com.teachy.coin.huobi.client.req.margin.IsolatedMarginRepayLoanRequest;
import com.teachy.coin.huobi.client.req.margin.IsolatedMarginTransferRequest;
import com.teachy.coin.huobi.constant.Options;
import com.teachy.coin.huobi.constant.enums.ExchangeEnum;
import com.teachy.coin.huobi.exception.SDKException;
import com.teachy.coin.huobi.model.isolatedmargin.IsolatedMarginAccount;
import com.teachy.coin.huobi.model.isolatedmargin.IsolatedMarginLoadOrder;
import com.teachy.coin.huobi.model.isolatedmargin.IsolatedMarginSymbolInfo;
import com.teachy.coin.huobi.service.huobi.HuobiIsolatedMarginService;

public interface IsolatedMarginClient {

  Long transfer(IsolatedMarginTransferRequest request);

  Long applyLoan(IsolatedMarginApplyLoanRequest request);

  Long repayLoan(IsolatedMarginRepayLoanRequest request);

  List<IsolatedMarginLoadOrder> getLoanOrders(IsolatedMarginLoanOrdersRequest request);

  List<IsolatedMarginAccount> getLoanBalance(IsolatedMarginAccountRequest request);

  List<IsolatedMarginSymbolInfo> getLoanInfo(IsolatedMarginLoanInfoRequest request);

  static IsolatedMarginClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiIsolatedMarginService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}

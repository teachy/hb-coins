package com.teachy.coin.huobi.client;

import java.util.List;

import com.teachy.coin.huobi.client.req.crossmargin.CrossMarginApplyLoanRequest;
import com.teachy.coin.huobi.client.req.crossmargin.CrossMarginLoanOrdersRequest;
import com.teachy.coin.huobi.client.req.crossmargin.CrossMarginRepayLoanRequest;
import com.teachy.coin.huobi.client.req.crossmargin.CrossMarginTransferRequest;
import com.teachy.coin.huobi.client.req.crossmargin.GeneralLoanOrdersRequest;
import com.teachy.coin.huobi.client.req.crossmargin.GeneralRepayLoanRequest;
import com.teachy.coin.huobi.constant.Options;
import com.teachy.coin.huobi.constant.enums.ExchangeEnum;
import com.teachy.coin.huobi.exception.SDKException;
import com.teachy.coin.huobi.model.crossmargin.CrossMarginAccount;
import com.teachy.coin.huobi.model.crossmargin.CrossMarginCurrencyInfo;
import com.teachy.coin.huobi.model.crossmargin.CrossMarginLoadOrder;
import com.teachy.coin.huobi.model.crossmargin.GeneralRepayLoanRecord;
import com.teachy.coin.huobi.model.crossmargin.GeneralRepayLoanResult;
import com.teachy.coin.huobi.service.huobi.HuobiCrossMarginService;

public interface CrossMarginClient {

  Long transfer(CrossMarginTransferRequest request);

  Long applyLoan(CrossMarginApplyLoanRequest request);

  void repayLoan(CrossMarginRepayLoanRequest request);

  List<CrossMarginLoadOrder> getLoanOrders(CrossMarginLoanOrdersRequest request);

  CrossMarginAccount getLoanBalance();

  List<CrossMarginCurrencyInfo> getLoanInfo();

  static CrossMarginClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiCrossMarginService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }

  List<GeneralRepayLoanResult> repayLoan(GeneralRepayLoanRequest request);

  List<GeneralRepayLoanRecord> getRepaymentLoanRecords(GeneralLoanOrdersRequest request);

}

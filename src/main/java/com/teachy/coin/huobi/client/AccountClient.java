package com.teachy.coin.huobi.client;

import java.util.List;

import com.teachy.coin.huobi.client.req.account.AccountAssetValuationRequest;
import com.teachy.coin.huobi.client.req.account.AccountBalanceRequest;
import com.teachy.coin.huobi.client.req.account.AccountFuturesTransferRequest;
import com.teachy.coin.huobi.client.req.account.AccountHistoryRequest;
import com.teachy.coin.huobi.client.req.account.AccountLedgerRequest;
import com.teachy.coin.huobi.client.req.account.AccountTransferRequest;
import com.teachy.coin.huobi.client.req.account.PointRequest;
import com.teachy.coin.huobi.client.req.account.PointTransferRequest;
import com.teachy.coin.huobi.client.req.account.SubAccountUpdateRequest;
import com.teachy.coin.huobi.constant.Options;
import com.teachy.coin.huobi.constant.enums.ExchangeEnum;
import com.teachy.coin.huobi.exception.SDKException;
import com.teachy.coin.huobi.model.account.Account;
import com.teachy.coin.huobi.model.account.AccountAssetValuationResult;
import com.teachy.coin.huobi.model.account.AccountBalance;
import com.teachy.coin.huobi.model.account.AccountFuturesTransferResult;
import com.teachy.coin.huobi.model.account.AccountHistory;
import com.teachy.coin.huobi.model.account.AccountLedgerResult;
import com.teachy.coin.huobi.model.account.AccountTransferResult;
import com.teachy.coin.huobi.model.account.AccountUpdateEvent;
import com.teachy.coin.huobi.model.account.Point;
import com.teachy.coin.huobi.model.account.PointTransferResult;
import com.teachy.coin.huobi.model.subuser.SubUserState;
import com.teachy.coin.huobi.service.huobi.HuobiAccountService;
import com.teachy.coin.huobi.utils.ResponseCallback;

public interface AccountClient {

  /**
   * Get User Account List
   * @return
   */
  List<Account> getAccounts();

  /**
   * Get User Account Balance
   * @param request
   * @return
   */
  AccountBalance getAccountBalance(AccountBalanceRequest request);

  List<AccountHistory> getAccountHistory(AccountHistoryRequest request);

  AccountLedgerResult getAccountLedger(AccountLedgerRequest request);

  AccountTransferResult accountTransfer(AccountTransferRequest request);

  AccountFuturesTransferResult accountFuturesTransfer(AccountFuturesTransferRequest request);

  Point getPoint(PointRequest request);

  PointTransferResult pointTransfer(PointTransferRequest request);

  AccountAssetValuationResult accountAssetValuation(AccountAssetValuationRequest request);

  void subAccountsUpdate(SubAccountUpdateRequest request, ResponseCallback<AccountUpdateEvent> callback);

  static AccountClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiAccountService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}

package com.teachy.coin.huobi.client;

import java.util.List;

import com.teachy.coin.huobi.client.req.wallet.CreateWithdrawRequest;
import com.teachy.coin.huobi.client.req.wallet.DepositAddressRequest;
import com.teachy.coin.huobi.client.req.wallet.DepositWithdrawRequest;
import com.teachy.coin.huobi.client.req.wallet.WithdrawAddressRequest;
import com.teachy.coin.huobi.client.req.wallet.WithdrawQuotaRequest;
import com.teachy.coin.huobi.constant.Options;
import com.teachy.coin.huobi.constant.enums.ExchangeEnum;
import com.teachy.coin.huobi.exception.SDKException;
import com.teachy.coin.huobi.model.wallet.DepositAddress;
import com.teachy.coin.huobi.model.wallet.DepositWithdraw;
import com.teachy.coin.huobi.model.wallet.WithdrawAddressResult;
import com.teachy.coin.huobi.model.wallet.WithdrawQuota;
import com.teachy.coin.huobi.service.huobi.HuobiWalletService;

public interface WalletClient {

  List<DepositAddress> getDepositAddress(DepositAddressRequest request);

  WithdrawQuota getWithdrawQuota(WithdrawQuotaRequest request);

  WithdrawAddressResult getWithdrawAddress(WithdrawAddressRequest request);

  Long createWithdraw(CreateWithdrawRequest request);

  Long cancelWithdraw(Long withdrawId);

  List<DepositWithdraw> getDepositWithdraw(DepositWithdrawRequest request);

  static WalletClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiWalletService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}

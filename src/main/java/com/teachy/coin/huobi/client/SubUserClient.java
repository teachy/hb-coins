package com.teachy.coin.huobi.client;

import java.util.List;

import com.teachy.coin.huobi.client.req.account.TransferSubuserRequest;
import com.teachy.coin.huobi.client.req.subuser.GetApiKeyListRequest;
import com.teachy.coin.huobi.client.req.subuser.GetSubUserAccountListRequest;
import com.teachy.coin.huobi.client.req.subuser.GetSubUserDepositRequest;
import com.teachy.coin.huobi.client.req.subuser.GetSubUserListRequest;
import com.teachy.coin.huobi.client.req.subuser.SubUserApiKeyDeletionRequest;
import com.teachy.coin.huobi.client.req.subuser.SubUserApiKeyGenerationRequest;
import com.teachy.coin.huobi.client.req.subuser.SubUserApiKeyModificationRequest;
import com.teachy.coin.huobi.client.req.subuser.SubUserCreationRequest;
import com.teachy.coin.huobi.client.req.subuser.SubUserManagementRequest;
import com.teachy.coin.huobi.client.req.subuser.SubUserTradableMarketRequest;
import com.teachy.coin.huobi.client.req.subuser.SubUserTransferabilityRequest;
import com.teachy.coin.huobi.constant.Options;
import com.teachy.coin.huobi.constant.enums.ExchangeEnum;
import com.teachy.coin.huobi.exception.SDKException;
import com.teachy.coin.huobi.model.account.AccountBalance;
import com.teachy.coin.huobi.model.account.SubuserAggregateBalance;
import com.teachy.coin.huobi.model.subuser.GetApiKeyListResult;
import com.teachy.coin.huobi.model.subuser.GetSubUserAccountListResult;
import com.teachy.coin.huobi.model.subuser.GetSubUserDepositResult;
import com.teachy.coin.huobi.model.subuser.GetSubUserListResult;
import com.teachy.coin.huobi.model.subuser.SubUserApiKeyGenerationResult;
import com.teachy.coin.huobi.model.subuser.SubUserApiKeyModificationResult;
import com.teachy.coin.huobi.model.subuser.SubUserCreationInfo;
import com.teachy.coin.huobi.model.subuser.SubUserManagementResult;
import com.teachy.coin.huobi.model.subuser.SubUserState;
import com.teachy.coin.huobi.model.subuser.SubUserTradableMarketResult;
import com.teachy.coin.huobi.model.subuser.SubUserTransferabilityResult;
import com.teachy.coin.huobi.model.wallet.DepositAddress;
import com.teachy.coin.huobi.service.huobi.HuobiSubUserService;

public interface SubUserClient {


  List<SubUserCreationInfo> subuserCreation(SubUserCreationRequest request);


  GetSubUserListResult getSubUserList(GetSubUserListRequest request);

  SubUserState getSubuserState(Long subUid);

  SubUserManagementResult subuserManagement(SubUserManagementRequest request);

  GetSubUserAccountListResult getSubuserAccountList(GetSubUserAccountListRequest request);

  SubUserTransferabilityResult subuserTransferability(SubUserTransferabilityRequest request);

  SubUserTradableMarketResult subuserTradableMarket(SubUserTradableMarketRequest request);

  SubUserApiKeyGenerationResult subuserApiKeyGeneration(SubUserApiKeyGenerationRequest request);

  SubUserApiKeyModificationResult subuserApiKeyModification(SubUserApiKeyModificationRequest request);

  void subuserApiKeyDeletion(SubUserApiKeyDeletionRequest request);

  GetApiKeyListResult getApiKeyList(GetApiKeyListRequest request);

  List<DepositAddress> getSubUserDepositAddress(Long subUid, String currency);

  GetSubUserDepositResult getSubUserDeposit(GetSubUserDepositRequest request);
  /**
   * Transfer to sub-user
   * @param request
   * @return
   */
  long transferSubuser(TransferSubuserRequest request);

  /**
   * Get sub-user's account balance
   * @param subuserId
   * @return
   */
  List<AccountBalance> getSubuserAccountBalance(Long subuserId);

  /**
   * Get the aggregated balance of all sub-accounts of the current user.
   * @return
   */
  List<SubuserAggregateBalance> getSubuserAggregateBalance();

  static SubUserClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiSubUserService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }

  long getUid();
}

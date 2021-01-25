package com.teachy.coin.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "coins_symbols")
public class CoinsSymbols {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 交易对
     */
    private String symbol;

    /**
     * 交易对中的基础币种
     */
    @Column(name = "base_currency")
    private String baseCurrency;

    /**
     * 交易对中的报价币种
     */
    @Column(name = "quote_currency")
    private String quoteCurrency;

    /**
     * 交易对报价的精度（小数点后位数）
     */
    @Column(name = "price_precision")
    private Integer pricePrecision;

    /**
     * 交易对基础币种计数精度
     */
    @Column(name = "amount_precision")
    private Integer amountPrecision;

    /**
     * 交易区
     */
    @Column(name = "symbol_partition")
    private String symbolPartition;

    /**
     * 交易对交易金额的精度
     */
    @Column(name = "value_precision")
    private Integer valuePrecision;

    /**
     * 交易对杠杆最大倍数
     */
    @Column(name = "leverage_ratio")
    private Integer leverageRatio;

    /**
     * 交易对状态；可能值: [online，offline,suspend] online - 已上线；offline - 交易对已下线，不可交易；suspend -- 交易暂停；pre-online - 即将上线
     */
    private String state;

    /**
     * 是否启用0启用 1禁止
     */
    private Integer status;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取交易对
     *
     * @return symbol - 交易对
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * 设置交易对
     *
     * @param symbol 交易对
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 获取交易对中的基础币种
     *
     * @return base_currency - 交易对中的基础币种
     */
    public String getBaseCurrency() {
        return baseCurrency;
    }

    /**
     * 设置交易对中的基础币种
     *
     * @param baseCurrency 交易对中的基础币种
     */
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    /**
     * 获取交易对中的报价币种
     *
     * @return quote_currency - 交易对中的报价币种
     */
    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    /**
     * 设置交易对中的报价币种
     *
     * @param quoteCurrency 交易对中的报价币种
     */
    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    /**
     * 获取交易对报价的精度（小数点后位数）
     *
     * @return price_precision - 交易对报价的精度（小数点后位数）
     */
    public Integer getPricePrecision() {
        return pricePrecision;
    }

    /**
     * 设置交易对报价的精度（小数点后位数）
     *
     * @param pricePrecision 交易对报价的精度（小数点后位数）
     */
    public void setPricePrecision(Integer pricePrecision) {
        this.pricePrecision = pricePrecision;
    }

    /**
     * 获取交易对基础币种计数精度
     *
     * @return amount_precision - 交易对基础币种计数精度
     */
    public Integer getAmountPrecision() {
        return amountPrecision;
    }

    /**
     * 设置交易对基础币种计数精度
     *
     * @param amountPrecision 交易对基础币种计数精度
     */
    public void setAmountPrecision(Integer amountPrecision) {
        this.amountPrecision = amountPrecision;
    }

    /**
     * 获取交易区
     *
     * @return symbol_partition - 交易区
     */
    public String getSymbolPartition() {
        return symbolPartition;
    }

    /**
     * 设置交易区
     *
     * @param symbolPartition 交易区
     */
    public void setSymbolPartition(String symbolPartition) {
        this.symbolPartition = symbolPartition;
    }

    /**
     * 获取交易对交易金额的精度
     *
     * @return value_precision - 交易对交易金额的精度
     */
    public Integer getValuePrecision() {
        return valuePrecision;
    }

    /**
     * 设置交易对交易金额的精度
     *
     * @param valuePrecision 交易对交易金额的精度
     */
    public void setValuePrecision(Integer valuePrecision) {
        this.valuePrecision = valuePrecision;
    }

    /**
     * 获取交易对杠杆最大倍数
     *
     * @return leverage_ratio - 交易对杠杆最大倍数
     */
    public Integer getLeverageRatio() {
        return leverageRatio;
    }

    /**
     * 设置交易对杠杆最大倍数
     *
     * @param leverageRatio 交易对杠杆最大倍数
     */
    public void setLeverageRatio(Integer leverageRatio) {
        this.leverageRatio = leverageRatio;
    }

    /**
     * 获取交易对状态；可能值: [online，offline,suspend] online - 已上线；offline - 交易对已下线，不可交易；suspend -- 交易暂停；pre-online - 即将上线
     *
     * @return state - 交易对状态；可能值: [online，offline,suspend] online - 已上线；offline - 交易对已下线，不可交易；suspend -- 交易暂停；pre-online - 即将上线
     */
    public String getState() {
        return state;
    }

    /**
     * 设置交易对状态；可能值: [online，offline,suspend] online - 已上线；offline - 交易对已下线，不可交易；suspend -- 交易暂停；pre-online - 即将上线
     *
     * @param state 交易对状态；可能值: [online，offline,suspend] online - 已上线；offline - 交易对已下线，不可交易；suspend -- 交易暂停；pre-online - 即将上线
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取是否启用0启用 1禁止
     *
     * @return status - 是否启用0启用 1禁止
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否启用0启用 1禁止
     *
     * @param status 是否启用0启用 1禁止
     */
    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CoinsSymbols) {
            return this.getSymbol().equals(((CoinsSymbols) obj).getSymbol());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getSymbol().hashCode();
    }
}
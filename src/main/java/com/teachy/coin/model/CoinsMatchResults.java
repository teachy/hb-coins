package com.teachy.coin.model;

import javax.persistence.*;

@Table(name = "coins_match_results")
public class CoinsMatchResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 该成交记录创建的时间戳
     */
    @Column(name = "created_at")
    private Long createdAt;

    /**
     * 成交数量
     */
    @Column(name = "filled_amount")
    private String filledAmount;

    /**
     * 交易手续费（正值）或交易返佣金（负值）
     */
    @Column(name = "filled_fees")
    private String filledFees;

    /**
     * 交易手续费或交易返佣币种
     */
    @Column(name = "fee_currency")
    private String feeCurrency;

    /**
     * Unique trade ID
     */
    @Column(name = "trade_id")
    private Integer tradeId;

    private String price;

    /**
     * 订单来源
     */
    private String source;

    /**
     * 交易对
     */
    private String symbol;

    /**
     * 订单类型
     */
    private String type;

    /**
     * maker,taker
     */
    private String role;

    @Column(name = "base_currency")
    private String baseCurrency;

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
     * 获取该成交记录创建的时间戳
     *
     * @return created_at - 该成交记录创建的时间戳
     */
    public Long getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置该成交记录创建的时间戳
     *
     * @param createdAt 该成交记录创建的时间戳
     */
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取成交数量
     *
     * @return filled_amount - 成交数量
     */
    public String getFilledAmount() {
        return filledAmount;
    }

    /**
     * 设置成交数量
     *
     * @param filledAmount 成交数量
     */
    public void setFilledAmount(String filledAmount) {
        this.filledAmount = filledAmount;
    }

    /**
     * 获取交易手续费（正值）或交易返佣金（负值）
     *
     * @return filled_fees - 交易手续费（正值）或交易返佣金（负值）
     */
    public String getFilledFees() {
        return filledFees;
    }

    /**
     * 设置交易手续费（正值）或交易返佣金（负值）
     *
     * @param filledFees 交易手续费（正值）或交易返佣金（负值）
     */
    public void setFilledFees(String filledFees) {
        this.filledFees = filledFees;
    }

    /**
     * 获取交易手续费或交易返佣币种
     *
     * @return fee_currency - 交易手续费或交易返佣币种
     */
    public String getFeeCurrency() {
        return feeCurrency;
    }

    /**
     * 设置交易手续费或交易返佣币种
     *
     * @param feeCurrency 交易手续费或交易返佣币种
     */
    public void setFeeCurrency(String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    /**
     * 获取Unique trade ID
     *
     * @return trade_id - Unique trade ID
     */
    public Integer getTradeId() {
        return tradeId;
    }

    /**
     * 设置Unique trade ID
     *
     * @param tradeId Unique trade ID
     */
    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    /**
     * @return price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 获取订单来源
     *
     * @return source - 订单来源
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置订单来源
     *
     * @param source 订单来源
     */
    public void setSource(String source) {
        this.source = source;
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
     * 获取订单类型
     *
     * @return type - 订单类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置订单类型
     *
     * @param type 订单类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取maker,taker
     *
     * @return role - maker,taker
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置maker,taker
     *
     * @param role maker,taker
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return base_currency
     */
    public String getBaseCurrency() {
        return baseCurrency;
    }

    /**
     * @param baseCurrency
     */
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }
}
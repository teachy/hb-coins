package com.teachy.coin.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "coins_buy")
@Data
public class CoinsBuy {
    private String symbol;

    private String sellPrice;

    private String price;

    @Column(name = "base_urrency")
    @Id
    private String baseUrrency;


}
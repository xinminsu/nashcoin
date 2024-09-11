package com.bizzan.bitrade.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@Table
public class ContractCoin {
    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空")
    @Id
    private long id;

    public String name;

    /**
     * 交易对：BTC/USDT
     */
    private String symbol;
    /**
     *  基币
     */
    private String baseSymbol;
    /**
     *  币种
     */
    private String coinSymbol;

    public int sort;

    public int coinScale;

    public int baseCoinScale;

    public String type;

    public int enable;

    public int visible;

    public int exchangeable;

    public int enableOpenSell;

    public int enableOpenBuy;

    public int enableMarketSell;

    public int enableMarketBuy;

    public int enableTriggerEntrust;

    public int spreadType;

    public double spread;

    public int leverageType;

    public String leverage;

    public double shareNumber;

    public double minShare;

    public double maxShare;

    public int intervalHour;

    public double feePercent;

    public double maintenanceMarginRate;

    public double openFee;

    public double closeFee;

    public double takerFee;

    public double makerFee;

    public double totalProfit;

    public double totalLoss;

    public double totalOpenFee;

    public double totalCloseFee;

    public String currentTime;

    public String leftTime;

    public double currentPrice;

    public double usdtRate;

}
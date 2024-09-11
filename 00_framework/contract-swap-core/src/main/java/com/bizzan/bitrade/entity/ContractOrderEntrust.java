package com.bizzan.bitrade.entity;

public class ContractOrderEntrust {

    private long id;

    private long contractId;

    private long memberId;

    private String symbol;

    private String baseSymbol; // 基币/结算币

    private String coinSymbol; // 币种符号

    private String direction; // 开仓方向：做多/做空

    private int volume; // 开仓张数

    private int tradedVolume; // 已交易数量

    private double tradedPrice; // 成交价格

    private String principalUnit; // 保证金单位

    private double principalAmount; // 保证金数量

    private String createTime; // 开仓时间

    private String type;

    private double triggerPrice; // 触发价

    private double entrustPrice; // 委托价格

    private String entrustType; // 开仓

    private String triggeringTime;// 触发时间，暂时无效

    private String shareNumber;

    private double profitAndLoss; // 盈亏（仅平仓计算）

    private String patterns; // 仓位模式

    private String openFee; // 开仓手续费

    private String status; // 委托状态：委托中

    private String currentPrice;

    private String isBlast; // 不是爆仓单

    private String isFromSpot;


}

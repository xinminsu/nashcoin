package com.bizzan.bitrade.entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.bizzan.bitrade.constant.BooleanEnum;
import com.bizzan.bitrade.constant.ContractConstant;
import com.bizzan.bitrade.constant.TokenOrderConstant;

import java.math.BigDecimal;

@Entity
@Data
@Table
public class ContractOrder {
    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空")
    @Id
    private long id;

    private long contractId;

    private long memberId;

    private long contractOrderEntrustId;

    private String patterns;

    private String entrustType;

    private String direction;

    private String type;

    private String symbol;

    private String coinSymbol;

    private String baseSymbol;

    private int triggerPrice;

    private int entrustPrice;

    private double tradedPrice;

    private String principalUnit;

    private int principalAmount;

    private double currentPrice;

    private String openFee;

    private double closeFee;

    private double shareNumber;

    private int volume;

    private int tradedVolume;

    private double profitAndLoss;

    private String status;

    private long createTime;

    private int triggeringTime;

    private int isFromSpot;

    private int isBlast;

    private int isReward;
}
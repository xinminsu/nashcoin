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
public class ContractOrder {
    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空")
    @Id
    private String id;
    /**
     * 交易对
     */
    private String pairsName;
    /**
     * 币种
     */
    private String coinName;
    /**
     * 主币名称
     */
    private String mainCur;
    /**
     * 杠杆倍数
     */
    private BigDecimal leverNum;
    /**
     * 杠杆倍数描述
     */
    private String leverDesc;
    /**
     * 保证金
     */
    private BigDecimal margin;
    /**
     * 合约乘数
     */
    private BigDecimal contractHands;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 平仓价格
     */
    private BigDecimal matchPrice;
    /**
     * 营收
     */
    private BigDecimal matchFee;
    /**
     * 平仓类型
     */
    private TokenOrderConstant.Close_Type closeType;
    /**
     * 数量
     */
    private BigDecimal coinNum;
    /**
     * 可用合约手数
     */
    private BigDecimal isContractHands;
    /**
     * 用户
     */
    private String member;
    /**
     * 交易类型
     */
    private ContractConstant.Trade_Type tradeType;
    /**
     * 价格类型
     */
    private ContractConstant.Price_Type priceType;
    /**
     * 状态
     */
    private TokenOrderConstant.Order_State orderState;
    /**
     * 手续费
     */
    private BigDecimal takeFee;
    /**
     * 类型
     */
    private TokenOrderConstant.Order_Type orderType;
    /**
     * 配置类型Id
     */
    private String contractMulId;
    /**
     * 配置类型对象
     */
    private ContractMul contractMul;
    /**
     * 扛杆类型Id
     */
    private String leverId;
    /**
     * 杠杆类型对象
     */
    private Lever lever;
    /**
     * 创建时间
     */
    @Column(columnDefinition = "varchar(30) default '2000-01-01 01:00:00'  comment '创建时间'")
    private String createTime;
    /**
     * 修改时间
     */
    @Column(columnDefinition = "varchar(30) default '2000-01-01 01:00:00'  comment '修改时间'")
    private String updateTime;
}
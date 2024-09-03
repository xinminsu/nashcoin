package com.bizzan.bitrade.entity;


import com.bizzan.bitrade.constant.EntrustConstant;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.bizzan.bitrade.constant.ContractConstant;

import java.math.BigDecimal;

@Entity
@Data
@Table
public class ContractEntrust {
    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空")
    @Id
    private String id;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 成交价格
     */
    private BigDecimal matchPrice;
    /**
     * 交易对ID
     */
    private String pairs;
    /**
     * 交易对名称
     */
    private String pairsName;
    /**
     * 主币
     */
    private String mainCur;
    /**
     * 代币
     */
    private String tokenCur;
    /**
     * 数量
     */
    private BigDecimal count;
    /**
     * 百分比
     */
    private BigDecimal percentageCount;
    /**
     * 成交额
     */
    private BigDecimal matchFee;
    /**
     * 已成交数量
     */
    private BigDecimal matchCount;
    /**
     * 剩余数量
     */
    private BigDecimal surplusCount;
    /**
     * 用户
     */
    private String member;
    /**
     * 用户类型
     */
    private String memberType;
    /**
     * 委托类型
     */
    private EntrustConstant.Entrust_Type entrustType;
    /**
     * 成交类型
     */
    private EntrustConstant.Method_Type methodType;
    /**
     * 价格类型
     */
    private ContractConstant.Price_Type priceType;
    /**
     * 状态
     */
    private EntrustConstant.Order_State state;
    /**
     * 手续费
     */
    private BigDecimal tradeFee;
    /**
     * 手续费费率
     */
    private BigDecimal tradeRate;

    /**
     * 撮合用户
     */
    private String matchMember;
    /**
     * 排序
     */
    private long sort;
    /**
     * 操作类型
     */
    private String uld;
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

    private Integer executionStatus;
}
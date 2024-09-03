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
public class Position {
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
     * 保证金
     */
    private BigDecimal margin;
    /**
     * 未实现盈亏
     */
    private BigDecimal unProfitLoss;
    /**
     * 收益
     */
    private BigDecimal profit;
    /**
     * 收益率
     */
    private BigDecimal profitUp;
    /**
     * 仓位总价
     */
    private BigDecimal tokenPrice;
    /**
     * 仓位总数
     */
    private BigDecimal tokenNum;
    /**
     * 仓位可用数
     */
    private BigDecimal isTokenNum;
    /**
     * 杠杆倍数
     */
    private BigDecimal avgLevel;
    /**
     * 总手数
     */
    private BigDecimal hands;
    /**
     * 总单数
     */
    private BigDecimal orders;
    /**
     * 仓位均价
     */
    private BigDecimal avePrice;

    /**
     * 强平价格
     */
    private BigDecimal forcePrice;

    /**
     * 预估强平价格
     */
    private BigDecimal closePrice;
    /**
     * 止盈价格
     */
    private BigDecimal triggerPrice;
    /**
     * 止损价格
     */
    private BigDecimal ordPrice;
    /**
     * 用户
     */
    private String member;
    /**
     * 交易类型
     */
    private ContractConstant.Trade_Type tradeType;
    /**
     * 交易类型
     */
    private TokenOrderConstant.Order_State state;
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
package com.bizzan.bitrade.entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.bizzan.bitrade.constant.BooleanEnum;
import com.bizzan.bitrade.constant.TokenOrderConstant;

import java.math.BigDecimal;

@Entity
@Data
public class ContractOrderSettlement {
    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空")
    @Id
    private Long id;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 用户id
     */
    private String memberId;
    /**
     * 币种
     */
    private String currency;
    /**
     * 订单状态
     */
    private TokenOrderConstant.Order_State orderState;
    /**
     * 变化的钱
     */
    private BigDecimal changeFee;
    /**
     * 盈亏状态:盈(0)亏(1)
     */
    private Byte state;
    /**
     * 交易后(变化后)的余额（与币种对应）
     */
    private BigDecimal balance;
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
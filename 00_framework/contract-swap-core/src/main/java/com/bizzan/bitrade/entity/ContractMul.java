package com.bizzan.bitrade.entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.bizzan.bitrade.constant.BooleanEnum;

import java.math.BigDecimal;

@Entity
@Data
public class ContractMul {
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
     * 合约乘数
     */
    private BigDecimal contractMul;
    /**
     * 强平价格
     */
    private BigDecimal forcePrice;
    /**
     * 开仓手续费
     */
    private BigDecimal makerFee;
    /**
     * 平仓手续费
     */
    private BigDecimal takerFee;
    /**
     * 强平手续费
     */
    private BigDecimal forceFee;
    /**
     * 交易手续费
     */
    private BigDecimal tradeFee;
    /**
     * 提现手续费
     */
    private BigDecimal withdrawFee;
    /**
     * 维持保证金率
     */
    private BigDecimal ensure;

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
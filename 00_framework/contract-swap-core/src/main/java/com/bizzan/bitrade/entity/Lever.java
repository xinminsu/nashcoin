package com.bizzan.bitrade.entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.bizzan.bitrade.constant.BooleanEnum;

import java.math.BigDecimal;

@Entity
@Data
public class Lever {
    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空")
    @Id
    private String id;
    /**
     * 倍数
     */
    private BigDecimal lever;
    /**
     * 描述
     */
    private String leverDesc;
    /**
     * 交易对名称
     */
    private String pairsName;
    /**
     * 手数
     */
    private int handsMax;
}
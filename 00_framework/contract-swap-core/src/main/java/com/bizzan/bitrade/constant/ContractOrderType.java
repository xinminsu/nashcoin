package com.bizzan.bitrade.constant;

public enum ContractOrderType {
    MARKET_PRICE("MARKET_PRICE"), //市价
    LIMIT_PRICE("LIMIT_PRICE"), //限价
    SPOT_LIMIT("SPOT_LIMIT");  //计划委托

    private String contractOrderType;

    ContractOrderType(String contractOrderType) {
        this.contractOrderType = contractOrderType;
    }

    public String getContractOrderType() {
        return contractOrderType;
    }

    public void setContractOrderType(String contractOrderType) {
        this.contractOrderType = contractOrderType;
    }

    @Override
    public String toString() {
        return contractOrderType;
    }
}

package com.bizzan.bitrade.constant;

public enum ContractOrderDirection {
    BUY("BUY"),//买（平空）
    SELL("SELL");//卖（平多）

    private String contractOrderDirection;

    ContractOrderDirection(String contractOrderDirection) {
        this.contractOrderDirection = contractOrderDirection;
    }

    public String getContractOrderDirection() {
        return contractOrderDirection;
    }

    public void setContractOrderDirection(String contractOrderDirection) {
        this.contractOrderDirection = contractOrderDirection;
    }

    @Override
    public String toString() {
        return contractOrderDirection;
    }
}

package com.bizzan.bitrade.constant;

public enum ContractOrderEntrustStatus {
    ENTRUST_ING("ENTRUST_ING"),  // 委托中
    ENTRUST_CANCEL("ENTRUST_CANCEL"), //委托取消
    ENTRUST_SUCCESS("ENTRUST_SUCCESS"); //委托成功

    private String contractOrderEntrustStatus;

    ContractOrderEntrustStatus(String contractOrderEntrustStatus) {
        this.contractOrderEntrustStatus = contractOrderEntrustStatus;
    }

    public String getContractOrderEntrustStatus() {
        return contractOrderEntrustStatus;
    }

    public void setContractOrderEntrustStatus(String contractOrderEntrustStatus) {
        this.contractOrderEntrustStatus = contractOrderEntrustStatus;
    }

    @Override
    public String toString() {
        return contractOrderEntrustStatus;
    }
}

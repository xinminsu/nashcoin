package com.bizzan.bitrade.service;

import com.bizzan.bitrade.dao.ContractOrderRepository;
import com.bizzan.bitrade.service.Base.BaseService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ContractOrderService extends BaseService {

    @Autowired
    private ContractOrderRepository contractOrderRepository;
}
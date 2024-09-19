package com.bizzan.bitrade.dao;

import com.bizzan.bitrade.entity.SwapCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SwapCoinRepository extends JpaRepository<SwapCoin, String>, JpaSpecificationExecutor<SwapCoin>, QueryDslPredicateExecutor<SwapCoin> {

    SwapCoin findBySymbol(String symbol);

    @Query("select distinct a.baseSymbol from  SwapCoin a where a.enable = 1")
    List<String> findBaseSymbol();

    @Query("select distinct a.coinSymbol from  SwapCoin a where a.enable = 1 and a.baseSymbol = :baseSymbol")
    List<String> findCoinSymbol(@Param("baseSymbol")String baseSymbol);

    @Query("select distinct a.coinSymbol from  SwapCoin a where a.enable = 1")
    List<String> findAllCoinSymbol();
}

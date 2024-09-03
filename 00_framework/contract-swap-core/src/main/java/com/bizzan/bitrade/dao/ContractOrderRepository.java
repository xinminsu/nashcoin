package com.bizzan.bitrade.dao;

import com.bizzan.bitrade.entity.ContractOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.bizzan.bitrade.entity.ContractOrder;

import java.math.BigDecimal;
import java.util.List;

public interface ContractOrderRepository extends JpaRepository<ContractOrder, String>, JpaSpecificationExecutor<ContractOrder>, QueryDslPredicateExecutor<ContractOrder> {
    ContractOrder findByOrderId(String orderId);

    @Modifying
    @Query("update ContractOrder contract set contract.tradedAmount = contract.tradedAmount + ?1  where contract.orderId = ?2")
    int increaseTradeAmount(BigDecimal amount, String orderId);

    @Modifying
    @Query("update ContractOrder  contract set contract.status = :status where contract.orderId = :orderId")
    int updateStatus(@Param("orderId") String orderId, @Param("status") ContractOrderStatus status);

    @Query(value="select coin_symbol unit,FROM_UNIXTIME(completed_time/1000, '%Y-%m-%d'),sum(traded_amount) amount from contract_order where FROM_UNIXTIME(completed_time/1000, '%Y-%m-%d') = :date and direction = 1 and status = 1 group by unit",nativeQuery = true)
    List<Object[]> getcontractTurnoverCoin(@Param("date") String date);

    @Query(value="select base_symbol unit,FROM_UNIXTIME(completed_time/1000, '%Y-%m-%d'),sum(turnover) amount from contract_order where FROM_UNIXTIME(completed_time/1000, '%Y-%m-%d') = :date and direction = 1 and status = 1 group by unit",nativeQuery = true)
    List<Object[]> getcontractTurnoverBase(@Param("date") String date);

    @Query(value="select base_symbol , coin_symbol,FROM_UNIXTIME(completed_time/1000, '%Y-%m-%d'),sum(traded_amount),sum(turnover) from contract_order where FROM_UNIXTIME(completed_time/1000, '%Y-%m-%d') = :date and direction = 1 and status = 1 group by base_symbol,coin_symbol",nativeQuery = true)
    List<Object[]> getcontractTurnoverSymbol(@Param("date") String date) ;

    @Query(value = "select contract from ContractOrder contract where contract.time< :cancleTime and contract.status=0 and contract.memberId in (76895,119284)")
    List<ContractOrder> queryContractOrderByTime(@Param("cancleTime") long cancelTime);

    @Query(value = "select contract from ContractOrder contract where contract.time< :cancleTime and contract.status=0 and contract.memberId in (:sellMemberId,:buyMemberId)")
    List<ContractOrder> queryContractOrderByTimeById(@Param("cancleTime") long cancelTime,@Param("sellMemberId") long sellMemberId,@Param("buyMemberId") long buyMemberId);

    @Query(value = "select contract from ContractOrder contract where contract.time< :cancleTime and contract.status=0 and contract.orderResource=0")
    List<ContractOrder> queryContractOrderByTimeById(@Param("cancleTime") long cancelTime);

    @Query(value = "select * from contract_order contract where contract.member_id=1 and contract.time < :beforeTime and contract.status<>0 ORDER BY contract.order_id asc limit 0,:num",nativeQuery = true)
    List<ContractOrder> queryHistoryDeleteList(@Param("beforeTime") long beforeTime,@Param("num")Integer num);

    @Transactional
    @Modifying
    @Query("delete from ContractOrder contract where contract.memberId = 1 and contract.status <> 0 and contract.time < :beforeTime")
    int deleteHistory(@Param("beforeTime") long beforeTime);
}
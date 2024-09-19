package com.bizzan.bitrade.service;

import com.bizzan.bitrade.dao.SwapCoinRepository;
import com.bizzan.bitrade.entity.SwapCoin;
import com.bizzan.bitrade.pagination.Criteria;
import com.querydsl.core.types.Predicate;

import com.bizzan.bitrade.service.Base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Path;
import java.util.List;

@Slf4j
@Service
public class SwapCoinService extends BaseService {

    @Autowired
    private SwapCoinRepository coinRepository;

    public List<SwapCoin> findAllEnabled() {
        Specification<SwapCoin> spec = (root, criteriaQuery, criteriaBuilder) -> {
            Path<String> enable = root.get("enable");
            criteriaQuery.where(criteriaBuilder.equal(enable, 1));
            return null;
        };

        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
        Sort sort = new Sort(order);
        return coinRepository.findAll(spec, sort);
    }

    //获取所有可显示币种
    public List<SwapCoin> findAllVisible() {
        Specification<SwapCoin> spec = (root, criteriaQuery, criteriaBuilder) -> {
            Path<String> visible = root.get("visible");
            Path<String> enable = root.get("enable");
            criteriaQuery.where(criteriaBuilder.equal(enable, 1), criteriaBuilder.equal(visible, 1));
            return null;
        };
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
        Sort sort = new Sort(order);
        return coinRepository.findAll(spec, sort);
    }

    public List<SwapCoin> findAllByRobotType(int robotType) {
        Specification<SwapCoin> spec = (root, criteriaQuery, criteriaBuilder) -> {
            Path<String> visible = root.get("visible");
            Path<String> enable = root.get("enable");
            Path<String> rT = root.get("robotType");
            criteriaQuery.where(criteriaBuilder.equal(enable, 1), criteriaBuilder.equal(visible, 1), criteriaBuilder.equal(rT, robotType));
            return null;
        };
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
        Sort sort = new Sort(order);
        return coinRepository.findAll(spec, sort);
    }

    public List<SwapCoin> findAllByFlag(int flag) {
        Specification<SwapCoin> spec = (root, criteriaQuery, criteriaBuilder) -> {
            Path<String> enable = root.get("enable");
            Path<Integer> flagPath = root.get("flag");
            Path<Integer> visiblePath = root.get("visible");
            criteriaQuery.where(criteriaBuilder.equal(enable, 1), criteriaBuilder.equal(flagPath, flag), criteriaBuilder.equal(visiblePath, 1));
            return null;
        };
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
        Sort sort = new Sort(order);
        return coinRepository.findAll(spec, sort);
    }

    public SwapCoin findOne(String id) {
        return coinRepository.findOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletes(String[] ids) {
        for (String id : ids) {
            coinRepository.delete(id);
        }
    }

    public SwapCoin save(SwapCoin SwapCoin) {
        return coinRepository.save(SwapCoin);
    }

    public Page<SwapCoin> pageQuery(int pageNo, Integer pageSize) {
        Sort orders = Criteria.sortStatic("sort");
        PageRequest pageRequest = new PageRequest(pageNo - 1, pageSize, orders);
        return coinRepository.findAll(pageRequest);
    }

    public SwapCoin findBySymbol(String symbol) {
        return coinRepository.findBySymbol(symbol);
    }

    public List<SwapCoin> findAll() {
        return coinRepository.findAll();
    }

    public boolean isSupported(String symbol) {
        SwapCoin coin = findBySymbol(symbol);
        return coin != null && (coin.getEnable() == 1);
    }

    public Page<SwapCoin> findAll(Predicate predicate, Pageable pageable) {
        return coinRepository.findAll(predicate, pageable);
    }

    public List<String> getBaseSymbol() {
        return coinRepository.findBaseSymbol();
    }

    public List<String> getCoinSymbol(String baseSymbol) {
        return coinRepository.findCoinSymbol(baseSymbol);
    }

    public List<String> getAllCoin(){
        return coinRepository.findAllCoinSymbol();
    }

}

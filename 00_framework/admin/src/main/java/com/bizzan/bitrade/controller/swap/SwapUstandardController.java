package com.bizzan.bitrade.controller.swap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bizzan.bitrade.annotation.AccessLog;
import com.bizzan.bitrade.constant.AdminModule;
import com.bizzan.bitrade.constant.PageModel;
import com.bizzan.bitrade.controller.common.BaseAdminController;
import com.bizzan.bitrade.entity.*;
import com.bizzan.bitrade.model.screen.SwapCoinScreen;
import com.bizzan.bitrade.service.*;
import com.bizzan.bitrade.util.MessageResult;
import com.bizzan.bitrade.util.PredicateUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

/**
 * @author suxinmin  E-mail:suxinmin@gmail.com
 * @description U本位交易
 * @date 2024
 */
@RestController
@RequestMapping("swap-coin")
public class SwapUstandardController extends BaseAdminController {

    private Logger logger = LoggerFactory.getLogger(SwapUstandardController.class);

    @Autowired
    private LocaleMessageSourceService messageSource;

    @Autowired
    private SwapCoinService swapCoinService;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CoinService coinService;

    @RequiresPermissions("swap-coin:add")
    @PostMapping("add")
    @AccessLog(module = AdminModule.CONTRACTOPTION, operation = "新增U本位交易对swap-coin")
    public MessageResult SwapCoinAdd(
            @Valid SwapCoin swapCoin) {
        logger.info("Add swap coin: " + JSON.toJSONString(swapCoin));

        SwapCoin findResult = swapCoinService.findBySymbol(swapCoin.getSymbol());
        if(findResult != null) {
            return error("[" + swapCoin.getSymbol() + "]交易对已存在！");
        }
        Coin c1 = coinService.findByUnit(swapCoin.getBaseSymbol());
        if(c1 == null) {
            return error("[" + swapCoin.getBaseSymbol() + "] 结算币种不存在！");
        }
        Coin c2 = coinService.findByUnit(swapCoin.getCoinSymbol());
        if(c2 == null) {
            return error("[" + swapCoin.getCoinSymbol() + "] 交易币种不存在！");
        }
        swapCoin = swapCoinService.save(swapCoin);
        return MessageResult.getSuccessInstance(messageSource.getMessage("SUCCESS"), swapCoin);
    }

    @RequiresPermissions("swap-coin:page-query")
    @PostMapping("page-query")
    @AccessLog(module = AdminModule.CONTRACTOPTION, operation = "分页查找U本位交易对swapCoin")
    public MessageResult SwapCoinList(PageModel pageModel, SwapCoinScreen screen) {
        if (pageModel.getProperty() == null) {
            List<String> list = new ArrayList<>();
            list.add("sort");
            List<Sort.Direction> directions = new ArrayList<>();
            directions.add(Sort.Direction.ASC);
            pageModel.setProperty(list);
            pageModel.setDirection(directions);
        }
        Predicate predicate = getPredicate(screen);
        Page<SwapCoin> all = swapCoinService.findAll(predicate, pageModel.getPageable());

        //远程RPC服务URL,获取当前交易引擎支持的币种
        String serviceName = "SERVICE-EXCHANGE-TRADE";
        String exchangeUrl = "http://" + serviceName + "/monitor/engines";
        ResponseEntity<HashMap> result = restTemplate.getForEntity(exchangeUrl, HashMap.class);
        Map<String, Integer> engineSymbols = (HashMap<String, Integer>)result.getBody();

        String marketServiceName = "bitrade-market";
        String marketUrl = "http://" + marketServiceName + "/market/engines";
        ResponseEntity<HashMap> marketResult = restTemplate.getForEntity(marketUrl, HashMap.class);
        Map<String, Integer> marketEngineSymbols = (HashMap<String, Integer>)marketResult.getBody();


        return success(all);
    }
    private Predicate getPredicate(SwapCoinScreen screen) {
        ArrayList<BooleanExpression> booleanExpressions = new ArrayList<>();
        QExchangeCoin qExchangeCoin = QExchangeCoin.exchangeCoin;
        if (StringUtils.isNotBlank(screen.getCoinSymbol())) {
            booleanExpressions.add(qExchangeCoin.coinSymbol.equalsIgnoreCase(screen.getCoinSymbol()));
        }
        if (StringUtils.isNotBlank(screen.getBaseSymbol())) {
            booleanExpressions.add(qExchangeCoin.baseSymbol.equalsIgnoreCase(screen.getBaseSymbol()));
        }

        return PredicateUtils.getPredicate(booleanExpressions);
    }

    /**
     * 检测是否存在交易机器人
     * @param coin
     * @return
     */
    private boolean isRobotExists(ExchangeCoin coin) {
        if(coin.getRobotType() == 0) {
            String serviceName = "ROBOT-TRADE-NORMAL";
            String url = "http://" + serviceName + "/ernormal/getRobotParams?coinName=" + coin.getSymbol();
            try {
                ResponseEntity<JSONObject> resultStr = restTemplate.getForEntity(url, JSONObject.class);
                logger.info("Get robot config: " + resultStr.toString());
                JSONObject result = (JSONObject)resultStr.getBody();
                if(result.getIntValue("code") == 0) {
                    return true;
                }else {
                    return false;
                }
            }catch(Exception e) {
                e.printStackTrace();
                return false;
            }
        }else if(coin.getRobotType() == 1){ // 独立出来，后面修改控盘机器人方便一些，其实代码现在是一样的
            String serviceName = "ROBOT-TRADE-NORMAL"; // 控盘机器人也通过此处进行控制
            String url = "http://" + serviceName + "/ernormal/getRobotParams?coinName=" + coin.getSymbol();
            try {
                ResponseEntity<JSONObject> resultStr = restTemplate.getForEntity(url, JSONObject.class);
                logger.info("Get robot config: " + resultStr.toString());
                JSONObject result = (JSONObject)resultStr.getBody();
                if(result.getIntValue("code") == 0) {
                    return true;
                }else {
                    return false;
                }
            }catch(Exception e) {
                e.printStackTrace();
                return false;
            }
        }else if(coin.getRobotType() == 2) {
            return false;
        }else {
            return false;
        }
    }

}

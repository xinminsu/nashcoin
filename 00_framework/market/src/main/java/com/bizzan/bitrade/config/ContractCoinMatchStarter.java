package com.bizzan.bitrade.config;

import com.bizzan.bitrade.client.Client;
import com.bizzan.bitrade.service.KlineRobotMarketService;
import com.bizzan.bitrade.socket.client.WsClientHuobi;
import com.bizzan.bitrade.util.WebSocketConnectionManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContractCoinMatchStarter implements ApplicationRunner {

    private Logger log = LoggerFactory.getLogger(ContractCoinMatchStarter.class);

    @Autowired
    private Client client;


    @Autowired
    private KlineRobotMarketService klineRobotMarketService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        try {
            System.out.println("---ContractCoinMatchStarter -- run");
            WebSocketConnectionManage.setClient(client);

            WsClientHuobi w = new WsClientHuobi();
            w.setContractMarketService(klineRobotMarketService);
            w.run();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("！！！！！！！");
        }

    }
}

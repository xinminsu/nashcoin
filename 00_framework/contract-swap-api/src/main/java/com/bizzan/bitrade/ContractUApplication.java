package com.bizzan.bitrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author xinminsu
 * @date 2024年9月02日
 *
 *exclude = {MongoAutoConfiguration.class,MongoDataAutoConfiguration.class}
 * 禁用springboot自带的mongodb配置（localhost），不禁用虽然也可以，但每次启动都会出现报错信息
 */

@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class ContractUApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContractUApplication.class, args);
    }
}

package cn.beiminzhiyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/8 23:07
 */
@SpringBootApplication
@EnableEurekaClient
public class WeiXinApp {
    public static void main(String[] args) {
        SpringApplication.run(WeiXinApp.class, args);
    }
}

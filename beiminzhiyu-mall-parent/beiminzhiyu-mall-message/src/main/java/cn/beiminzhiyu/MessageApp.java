package cn.beiminzhiyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 22:37
 */
@SpringBootApplication
@EnableEurekaClient
public class MessageApp {
    public static void main(String[] args) {
        SpringApplication.run(MessageApp.class, args);
    }
}

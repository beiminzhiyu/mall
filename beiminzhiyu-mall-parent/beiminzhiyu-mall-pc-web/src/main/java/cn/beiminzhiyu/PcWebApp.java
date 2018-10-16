package cn.beiminzhiyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/8 11:02
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class PcWebApp {
    public static void main(String[] args) {
        SpringApplication.run(PcWebApp.class, args);
    }
}

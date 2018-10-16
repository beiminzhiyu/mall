package cn.beiminzhiyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 19:08
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.beiminzhiyu.dao")
public class MemberApp {
    public static void main(String[] args) {
        SpringApplication.run(MemberApp.class, args);
    }
}

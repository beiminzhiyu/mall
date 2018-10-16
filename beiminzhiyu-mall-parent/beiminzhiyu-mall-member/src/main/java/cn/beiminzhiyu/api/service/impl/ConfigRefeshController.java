package cn.beiminzhiyu.api.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/9 18:25
 */

@RefreshScope
@RestController
public class ConfigRefeshController {

    @Value("${messages.status}")
    private String status;

    @RequestMapping("/status")
    public String from() {
        return this.status;
    }
}

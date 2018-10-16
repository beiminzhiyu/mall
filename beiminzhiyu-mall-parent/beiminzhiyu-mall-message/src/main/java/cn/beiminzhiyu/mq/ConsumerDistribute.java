package cn.beiminzhiyu.mq;

import cn.beiminzhiyu.adapter.MessageAdapter;
import cn.beiminzhiyu.common.Constants;
import cn.beiminzhiyu.email.service.EmailService;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 22:42
 */
@Component
@Slf4j
public class ConsumerDistribute {

    @Autowired
    private EmailService emailService;

    private MessageAdapter messageAdapter;

    @JmsListener(destination = "messages_queue")
    public void distribute(String json){

        log.info("####ConsumerDistribute###distribute() 消息服务平台接受 json参数:" + json);
        if (StringUtils.isEmpty(json)) {
            return;
        }
        JSONObject jsonObecjt = new JSONObject().parseObject(json);
        JSONObject header = jsonObecjt.getJSONObject("header");
        String interfaceType = header.getString("interfaceType");

        if (StringUtils.isEmpty(interfaceType)) {
            return;
        }
        if (interfaceType.equals(Constants.SMS_MAIL)) {
            messageAdapter = emailService;
        }
        if (messageAdapter == null) {
            return;
        }
        JSONObject body = jsonObecjt.getJSONObject("content");
        messageAdapter.sendMessage(body);

    }
}

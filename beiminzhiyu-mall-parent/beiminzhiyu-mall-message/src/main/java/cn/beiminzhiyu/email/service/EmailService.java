package cn.beiminzhiyu.email.service;

import cn.beiminzhiyu.adapter.MessageAdapter;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 22:48
 */
@Service
@Slf4j
public class EmailService implements MessageAdapter {
    @Value("${msg.subject}")
    private String subject;
    @Value("${msg.text}")
    private String text;
    @Value("${spring.mail.username}")
    private String myEmail;

    @Autowired
    private JavaMailSender javaMailSender; // 自动注入的Bean

    @Override
    public void sendMessage(JSONObject body) {
        String mail = body.getString("mail");
        if (StringUtils.isEmpty(mail)) {
            return;
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 发送
        simpleMailMessage.setFrom(myEmail);
        simpleMailMessage.setTo(mail);
        // 标题
        simpleMailMessage.setSubject(subject);
        // 内容
        simpleMailMessage.setText(text.replace("{}", mail));
        log.info("#####发送邮件到账号："+mail+"#####");
        javaMailSender.send(simpleMailMessage);
    }
}

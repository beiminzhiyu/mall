package cn.beiminzhiyu.api.service.impl;

import cn.beiminzhiyu.api.service.UserService;
import cn.beiminzhiyu.common.CommonBaseService;
import cn.beiminzhiyu.common.Constants;
import cn.beiminzhiyu.common.ResponseData;
import cn.beiminzhiyu.dao.UserDao;
import cn.beiminzhiyu.entity.UserEntity;
import cn.beiminzhiyu.mq.RegisterMailboxProducer;
import cn.beiminzhiyu.utils.MD5Util;
import cn.beiminzhiyu.utils.TokenUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.catalina.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 21:33
 */
@RestController
@Slf4j
public class UserServiceImpl extends CommonBaseService implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RegisterMailboxProducer registerMailboxProducer;

    @Value("${messages.queue}")
    private String MESSAGESQUEUE;
    @Override
    public ResponseData findUserById(@RequestBody Long userId) {
        UserEntity userEntity = userDao.findByID(userId);
        if (userEntity==null){
            return setResultError("未查找到用户信息");
        }
        return setResultSuccess(userEntity);
    }

    @Override
    public ResponseData register(@RequestBody UserEntity userEntity) {
        userEntity.setCreated(new Date());
        userEntity.setUpdated(new Date());
        String passWord=userEntity.getPassword();
        String newPassWord= MD5Util.MD5(passWord);
        userEntity.setPassword(newPassWord);
        Integer insertUser = userDao.insertUser(userEntity);
        if (insertUser <= 0) {
            return setResultError("注册失败!");
        }
        String email = userEntity.getEmail();
        String json = getEmailJson(email);
        log.info("######发送消息到消息平台#####:"+json);
        sendMsg(json);
        return setResultSuccess();

    }

    @Override
    public ResponseData login(@RequestBody UserEntity userEntity) {
        // 1.验证参数
        String userName = userEntity.getUsername();
        if (StringUtils.isEmpty(userName)){
            return setResultError("用户名不能为空");
        }
        String password = userEntity.getPassword();
        if (StringUtils.isEmpty(password)){
            return setResultError("密码不能为空");
        }
        // 2.数据库查找账号密码是否正确
        String realPassword = MD5Util.MD5(password);
        UserEntity entity = userDao.login(userName, realPassword);
        if (entity == null){
            return setResultError("用户名或密码错误！！");
        }
        return loginResponse(entity);
    }



    @Override
    public ResponseData findUserByToken(@RequestBody String token) {
        // 1.验证参数
        if(StringUtils.isEmpty(token)){
            return setResultError("token不能为空");
        }
        // 2.使用token查找对应userId
        String userId = (String)commonRedisCommon.getString(token);
        if (StringUtils.isEmpty(userId)){
            return setResultError("token无效或已过期！！");
        }
        // 3.使用userId查询用户信息返回客户端
        UserEntity userEntity = userDao.findByID(Long.parseLong(userId));
        if (null == userEntity){
            return setResultError("未查到用户信息");
        }
        userEntity.setPassword(null);
        return setResultSuccess(userEntity);
    }

    @Override
    public ResponseData qqLogin(@RequestBody String openId) {
        // 1.检查参数
        if (StringUtils.isEmpty(openId)){
            return setResultError("openId不能为空");
        }
        // 2.根据openId查询用户
        UserEntity user = userDao.findUserByOpenId(openId);
        // 3.如果不存在返回未绑定状态
        if (null == user){
            return setResult(Constants.QQ_NOT_BINDING_201,"用户还未绑定", null);
        }
        // 4.如果存在进行自动登录操作
        return loginResponse(user);
    }

    @Override
    public ResponseData qqBindUser(@RequestBody UserEntity userEntity) {
        // 1.参数检查
        String openId = userEntity.getOpenId();
        if (StringUtils.isEmpty(openId)){
            return setResultError("未获得到openId");
        }
        // 2.登录确定用户是否存在
        ResponseData responseData = login(userEntity);
        if (!responseData.getCode().equals(Constants.HTTP_RES_CODE_200)){
            return responseData;
        }
        // 3.更新用户openId
        JSONObject jsonObject = (JSONObject) responseData.getData();
        String token = (String) jsonObject.get("memberToken");
        if (StringUtils.isEmpty(token)){
            return setResultError("token失效或已过期");
        }
        ResponseData data = findUserByToken(token);
        UserEntity userData = (UserEntity) data.getData();
        if (userData==null){
            return data;
        }
        Integer id = userData.getId();
        Integer rows = userDao.modifyOpenId(openId, id);
        if (rows<=0){
            return setResultError("绑定失败！！");
        }
        return setResultSuccess();
    }

    private ResponseData loginResponse(UserEntity entity){
        // 3.如果账号正确，对应生成token
        String memberToken = TokenUtil.getMemberToken();
        // 4.存放在redis中，key为token value为userId
        commonRedisCommon.setString(memberToken, entity.getId()+"",Constants.Token_MEMBER_TIME);
        // 5.直接返回token
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memberToken", memberToken);
        return setResultSuccess(jsonObject);
    }

    private String getEmailJson(String mail) {
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", Constants.SMS_MAIL);
        JSONObject content = new JSONObject();
        content.put("mail", mail);
        root.put("header", header);
        root.put("content", content);
        return root.toJSONString();
    }

    private void sendMsg(String json) {

        ActiveMQQueue activeMQQueue = new ActiveMQQueue(MESSAGESQUEUE);
        registerMailboxProducer.sendMsg(activeMQQueue, json);
    }
}

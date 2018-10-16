package cn.beiminzhiyu.api.service;

import cn.beiminzhiyu.common.ResponseData;
import cn.beiminzhiyu.entity.UserEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 21:32
 */
@RequestMapping("/user")
public interface UserService {

    @RequestMapping("/findUserById")
    ResponseData findUserById(@RequestBody Long userId);

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    ResponseData register(@RequestBody UserEntity userEntity);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseData login(@RequestBody UserEntity userEntity);

    @RequestMapping(value = "/findUserByToken", method = RequestMethod.POST)
    ResponseData findUserByToken(@RequestBody String token);

    @RequestMapping(value = "qqLogin", method = RequestMethod.GET)
    ResponseData qqLogin(@RequestBody String openId);

    @RequestMapping(value = "qqBindUser", method = RequestMethod.POST)
    ResponseData qqBindUser(@RequestBody UserEntity userEntity);
}

package cn.beiminzhiyu.controller;

import cn.beiminzhiyu.common.Constants;
import cn.beiminzhiyu.common.ResponseData;
import cn.beiminzhiyu.entity.UserEntity;
import cn.beiminzhiyu.feign.UserServiceFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/8 11:17
 */
@Controller
public class RegisterController {
    private static final String REGISTER = "register";
    private static final String LOGIN = "login";

    @Autowired
    private UserServiceFegin userServiceFegin;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(){

        return REGISTER;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPost(UserEntity userEntity, HttpServletRequest request){
        ResponseData responseData = userServiceFegin.register(userEntity);
        if (!responseData.getCode().equals(Constants.HTTP_RES_CODE_200)){
            request.setAttribute("error", "注册失败");
            return REGISTER;
        }
        return LOGIN;
    }
}

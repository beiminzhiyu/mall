package cn.beiminzhiyu.controller;

import cn.beiminzhiyu.common.Constants;
import cn.beiminzhiyu.common.ResponseData;
import cn.beiminzhiyu.feign.UserServiceFegin;
import cn.beiminzhiyu.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/8 11:07
 */
@Controller
@Slf4j
public class IndexController {

    private static final String INDEX = "index";
    private static final String LOGIN = "login";

    @Autowired
    private UserServiceFegin userServiceFegin;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        String token = CookieUtil.getUid(request, Constants.COOKIE_MEMBER_TOKEN);
        if (!StringUtils.isEmpty(token)){
            ResponseData responseData = userServiceFegin.findUserByToken(token);
            if (responseData.getCode().equals(Constants.HTTP_RES_CODE_200)){
                LinkedHashMap userData = (LinkedHashMap)responseData.getData();
                log.info(userData.toString());
                request.setAttribute("username", userData.get("username"));
                return INDEX;
            }
        }
        return LOGIN;
    }
}

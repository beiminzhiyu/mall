package cn.beiminzhiyu.controller;

import cn.beiminzhiyu.common.Constants;
import cn.beiminzhiyu.common.ResponseData;
import cn.beiminzhiyu.entity.UserEntity;
import cn.beiminzhiyu.feign.UserServiceFegin;
import cn.beiminzhiyu.utils.CookieUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/8 14:26
 */
@Controller
public class LoginController {

    @Autowired
    private UserServiceFegin userServiceFegin;

    private static final String LOGIN = "login";
    private static final String INDEX = "index";
    private static final String ERROR = "error";
    private static final String RELATION = "relation";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet() {
        return LOGIN;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {
        // 1.参数验证
        if (StringUtils.isEmpty(userEntity.getUsername())) {
            request.setAttribute("error", "用户名不能为空");
            return LOGIN;
        }
        if (StringUtils.isEmpty(userEntity.getPassword())) {
            request.setAttribute("error", "密码不能为空");
            return LOGIN;
        }
        // 2.调用登录接口，获取token
        ResponseData responseData = userServiceFegin.login(userEntity);
        if (!responseData.getCode().equals(Constants.HTTP_RES_CODE_200)) {
            request.setAttribute("error", "账号或密码错误");
            return LOGIN;
        }
        // 3.将token放入cookie
        LinkedHashMap data = (LinkedHashMap) responseData.getData();
        String memberToken = (String) data.get("memberToken");
        if (StringUtils.isEmpty(memberToken)) {
            request.setAttribute("error", "会话已经失效！");
            return LOGIN;
        }
        CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, memberToken, Constants.COOKIE_TOKEN_MEMBER_TIME);
        request.setAttribute("username", userEntity.getUsername());
        return INDEX;
    }

    @RequestMapping("/locaQQLogin")
    public String locaQQLogin(HttpServletRequest request) throws QQConnectException {
        String authorizeURL = new Oauth().getAuthorizeURL(request);
        return "redirect:" + authorizeURL;
    }

    @RequestMapping("/qqLoginCallback")
    public String qqLoginCallback(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession)
            throws QQConnectException {
        AccessToken accessTokenObj = new Oauth().getAccessTokenByRequest(request);
        if (accessTokenObj == null) {
            request.setAttribute("error", "qq授权失败!");
            return ERROR;
        }
        String accessToken = accessTokenObj.getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            request.setAttribute("error", "qq授权失败!");
            return ERROR;
        }
        // 获取openid
        OpenID openIdObj = new OpenID(accessToken);
        String userOpenID = openIdObj.getUserOpenID();
        ResponseData openIdUser = userServiceFegin.qqLogin(userOpenID);
        // 用戶沒有关联QQ账号
        if (openIdUser.getCode().equals(Constants.QQ_NOT_BINDING_201)) {
            // 跳转到管理账号
            httpSession.setAttribute("qqOpenid", userOpenID);
            return RELATION;
        }
        // 如果用户关联账号 直接登录
        LinkedHashMap dataMap = (LinkedHashMap) openIdUser.getData();
        String memberToken = (String) dataMap.get("memberToken");
        if (StringUtils.isEmpty(memberToken)) {
            request.setAttribute("error", "会话已经失效!");
            return LOGIN;
        }
        setCookie(memberToken, response);
        return INDEX;
    }

    public void setCookie(String memberToken, HttpServletResponse response) {
        CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, memberToken, Constants.COOKIE_TOKEN_MEMBER_TIME);
    }
}

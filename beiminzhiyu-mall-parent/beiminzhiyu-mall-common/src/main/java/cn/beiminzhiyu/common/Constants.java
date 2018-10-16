package cn.beiminzhiyu.common;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 15:32
 */
public interface Constants {

    Integer HTTP_RES_CODE_200 = 200;

    Integer HTTP_RES_CODE_500 = 500;

    Integer QQ_NOT_BINDING_201 = 201;

    String HTTP_RES_CODE_200_VALUE = "success";

    String HTTP_RES_CODE_500_VALUE = "failed";

    String SMS_MAIL = "email";

    String TOKEN_MEMBER = "token_member";

    Long Token_MEMBER_TIME = Long.valueOf(60*60*24*90);

    int COOKIE_TOKEN_MEMBER_TIME =(60*60*24*90);

    String COOKIE_MEMBER_TOKEN ="cookie_member_token";

}

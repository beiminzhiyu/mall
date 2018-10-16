package cn.beiminzhiyu.utils;

import cn.beiminzhiyu.common.Constants;

import java.util.UUID;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/8 09:46
 */
public class TokenUtil {

    public static String getMemberToken(){
        return Constants.TOKEN_MEMBER+"-"+ UUID.randomUUID();
    }
}

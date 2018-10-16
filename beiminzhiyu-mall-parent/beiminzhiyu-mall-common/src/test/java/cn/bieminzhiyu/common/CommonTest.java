package cn.bieminzhiyu.common;

import cn.beiminzhiyu.common.ResponseData;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 15:16
 */
public class CommonTest {

    public static void main(String[] args) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(123);
        System.out.println(responseData);
    }
}

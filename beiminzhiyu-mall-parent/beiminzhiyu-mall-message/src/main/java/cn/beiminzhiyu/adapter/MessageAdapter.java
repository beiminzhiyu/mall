package cn.beiminzhiyu.adapter;

import com.alibaba.fastjson.JSONObject;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 22:37
 */
public interface MessageAdapter {

    public void sendMessage(JSONObject body);

}

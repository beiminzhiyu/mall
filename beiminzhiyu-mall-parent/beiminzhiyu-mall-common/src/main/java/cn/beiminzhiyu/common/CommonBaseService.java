package cn.beiminzhiyu.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 20:01
 */
@Setter
@Getter
@Component
public class CommonBaseService {

    @Autowired
    protected CommonRedisCommon commonRedisCommon;

    public ResponseData setResultSuccess() {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
    }

    // 返回成功 ,data可传
    public ResponseData setResultSuccess(Object data) {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
    }

    // 返回失败
    public ResponseData setResultError(String msg) {
        return setResult(Constants.HTTP_RES_CODE_500, msg, null);
    }

    // 返回失败
    public ResponseData setResultError() {
        return setResult(Constants.HTTP_RES_CODE_500, Constants.HTTP_RES_CODE_500_VALUE, null);
    }

    // 自定义返回结果
    public ResponseData setResult(Integer code, String msg, Object data) {
        ResponseData responseBase = new ResponseData();
        responseBase.setCode(code);
        responseBase.setMsg(msg);
        if (data != null)
            responseBase.setData(data);
        return responseBase;
    }

}

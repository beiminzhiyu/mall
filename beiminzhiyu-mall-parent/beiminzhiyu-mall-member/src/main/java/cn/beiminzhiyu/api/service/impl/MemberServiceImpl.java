package cn.beiminzhiyu.api.service.impl;

import cn.beiminzhiyu.api.service.MemberService;
import cn.beiminzhiyu.common.CommonBaseService;
import cn.beiminzhiyu.common.ResponseData;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 18:51
 */
@RestController
public class MemberServiceImpl extends CommonBaseService implements MemberService {

    @Override
    public ResponseData testMember(Integer id, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        return setResultSuccess(map);
    }

    @Override
    public ResponseData testSetRedis(String key, String value) {
        commonRedisCommon.setString(key, value, null);
        return setResultSuccess();
    }

    @Override
    public ResponseData testGetRedis(String key) {
        Object value = commonRedisCommon.getString(key);
        return setResultSuccess(value);
    }

}

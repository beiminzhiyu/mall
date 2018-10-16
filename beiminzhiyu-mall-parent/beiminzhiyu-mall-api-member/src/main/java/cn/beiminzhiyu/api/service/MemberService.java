package cn.beiminzhiyu.api.service;

import org.springframework.web.bind.annotation.RequestMapping;
import cn.beiminzhiyu.common.*;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 18:02
 */
@RequestMapping("/member")
public interface MemberService {

    @RequestMapping("/testMember")
    public ResponseData testMember(Integer id, String name);

    @RequestMapping("/testSetRedis")
    public ResponseData testSetRedis(String key, String value);

    @RequestMapping("/testGetRedis")
    public ResponseData testGetRedis(String key);

}

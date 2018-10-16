package cn.beiminzhiyu.feign;

import cn.beiminzhiyu.api.service.UserService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/8 11:46
 */
@FeignClient("member-service")
@Service
public interface UserServiceFegin extends UserService {
}

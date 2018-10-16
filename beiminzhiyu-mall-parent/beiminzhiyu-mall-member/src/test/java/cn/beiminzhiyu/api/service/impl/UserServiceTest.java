package cn.beiminzhiyu.api.service.impl;

import cn.beiminzhiyu.MemberApp;
import cn.beiminzhiyu.dao.UserDao;
import cn.beiminzhiyu.entity.UserEntity;
import cn.beiminzhiyu.utils.MD5Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/8 09:17
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MemberApp.class,MockServletContext.class})
@WebAppConfiguration
@MapperScan("cn.beiminzhiyu.dao")
public class UserServiceTest {

    private MockMvc mvc;

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new UserServiceImpl()).build();
    }

    // 测试通过
    @Test
    public void testUserService() throws Exception {
        UserEntity lik = userDao.login("lik", MD5Util.MD5("111111"));
        System.out.println(lik);
    }
}

package cn.beiminzhiyu.controller;

import cn.beiminzhiyu.PcWebApp;
import cn.beiminzhiyu.entity.UserEntity;
import cn.beiminzhiyu.utils.MD5Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
 * @Date 2018/10/8 13:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PcWebApp.class, MockServletContext.class})
@WebAppConfiguration
public class RegisterControllerTest {

    private MockMvc mvc;


    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new RegisterController()).build();
    }

    // 测试通过
    @Test
    public void testUserService() throws Exception {

    }
}

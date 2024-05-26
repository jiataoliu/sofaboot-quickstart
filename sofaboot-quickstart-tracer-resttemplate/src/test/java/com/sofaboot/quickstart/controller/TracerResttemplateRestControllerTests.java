package com.sofaboot.quickstart.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TracerResttemplateRestControllerTests {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TracerResttemplateRestControllerTests.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void syncRest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/syncRest").param("name", "syncRest"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 添加 status 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true)) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").isNumber()) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Hello, syncRest!")) // 添加 jsonPath 断言
                .andDo(MockMvcResultHandlers.print()) // 添加执行
                .andReturn(); // 添加返回
    }

    @Test
    public void asyncRest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/asyncRest").param("name", "asyncRest"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 添加 status 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true)) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").isNumber()) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Hello, asyncRest!")) // 添加 jsonPath 断言
                .andDo(MockMvcResultHandlers.print()) // 添加执行
                .andReturn(); // 添加返回
    }

    /**
     * restTemplate 调用 case 需要先启动项目才能跑过，所以先屏蔽掉
     *
     * @throws Exception
     */
    @Test
    public void sync() throws Exception {
        // MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/sync"))
        //         .andExpect(MockMvcResultMatchers.status().isOk()) // 添加 status 断言
        //         .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true)) // 添加 jsonPath 断言
        //         .andExpect(MockMvcResultMatchers.jsonPath("$.count").isNumber()) // 添加 jsonPath 断言
        //         .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Hello, syncRest!")) // 添加 jsonPath 断言
        //         .andDo(MockMvcResultHandlers.print()) // 添加执行
        //         .andReturn(); // 添加返回
    }

    /**
     * restTemplate 调用 case 需要先启动项目才能跑过，所以先屏蔽掉
     *
     * @throws Exception
     */
    @Test
    public void async() throws Exception {
        // MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/async"))
        //         .andExpect(MockMvcResultMatchers.status().isOk()) // 添加 status 断言
        //         .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true)) // 添加 jsonPath 断言
        //         .andExpect(MockMvcResultMatchers.jsonPath("$.count").isNumber()) // 添加 jsonPath 断言
        //         .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Hello, asyncRest!")) // 添加 jsonPath 断言
        //         .andDo(MockMvcResultHandlers.print()) // 添加执行
        //         .andReturn(); // 添加返回
    }
}
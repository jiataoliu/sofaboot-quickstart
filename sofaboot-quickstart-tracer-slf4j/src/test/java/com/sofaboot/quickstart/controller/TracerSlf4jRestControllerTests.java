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
public class TracerSlf4jRestControllerTests {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TracerSlf4jRestControllerTests.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void slf4j() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/slf4j").param("name", "slf4j"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 添加 status 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true)) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").isNumber()) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Hello, slf4j!")) // 添加 jsonPath 断言
                .andDo(MockMvcResultHandlers.print()) // 添加执行
                .andReturn(); // 添加返回
    }

    @Test
    public void async() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/async"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 添加 status 断言
                .andExpect(MockMvcResultMatchers.content().string("")) // 添加断言
                .andDo(MockMvcResultHandlers.print()) // 添加执行
                .andReturn(); // 添加返回
    }
}
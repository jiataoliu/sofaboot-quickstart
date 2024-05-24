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

@SpringBootTest(properties = {
        "spring.datasource.druid.filters=stat,slf4j",
        "spring.datasource.druid.web-stat-filter.enabled=false"
})
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TracerDatasourceRestControllerTests {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TracerDatasourceRestControllerTests.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreate() throws Exception {
        final String sql = "DROP TABLE IF EXISTS TEST;"
                + "CREATE TABLE TEST("
                + "USER_ID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'userId',"
                + "USER_NAME VARCHAR(255) DEFAULT '' COMMENT 'userName',"
                + "CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime'"
                + ");"
                + "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES ('张三', CURRENT_TIMESTAMP);"
                + "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES ('李四', CURRENT_TIMESTAMP);"
                + "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES ('王五', CURRENT_TIMESTAMP);";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/create"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 添加 status 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true)) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(sql)) // 添加 jsonPath 断言
                .andDo(MockMvcResultHandlers.print()) // 添加执行
                .andReturn(); // 添加返回
    }

    @Test
    public void testCreateStep() throws Exception {
        final String sql = "DROP TABLE IF EXISTS TEST;CREATE TABLE TEST(USER_ID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'userId',USER_NAME VARCHAR(255) DEFAULT '' COMMENT 'userName',CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime');INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES (?, CURRENT_TIMESTAMP);";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/createStep"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 添加 status 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true)) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(sql)) // 添加 jsonPath 断言
                .andDo(MockMvcResultHandlers.print()) // 添加执行
                .andReturn(); // 添加返回
    }

    @Test
    public void testExecute() throws Exception {
        final String sql = "DROP TABLE IF EXISTS TEST;"
                + "CREATE TABLE TEST("
                + "USER_ID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'userId',"
                + "USER_NAME VARCHAR(255) DEFAULT '' COMMENT 'userName',"
                + "CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime'"
                + ");"
                + "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES ('张三', CURRENT_TIMESTAMP);"
                + "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES ('李四', CURRENT_TIMESTAMP);"
                + "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES ('王五', CURRENT_TIMESTAMP);";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/execute"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 添加 status 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true)) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(sql)) // 添加 jsonPath 断言
                .andDo(MockMvcResultHandlers.print()) // 添加执行
                .andReturn(); // 添加返回
    }

    @Test
    public void testSelectOne() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/selectOne/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 添加 status 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true)) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.userId").isNumber()) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.userName").isString()) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.createTime").isString()) // 添加 jsonPath 断言
                .andDo(MockMvcResultHandlers.print()) // 添加执行
                .andReturn(); // 添加返回
    }

    @Test
    public void testSelectAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/selectAll"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 添加 status 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true)) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[-1].userId").isNumber()) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[-1].userName").isString()) // 添加 jsonPath 断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[-1].createTime").isString()) // 添加 jsonPath 断言
                .andDo(MockMvcResultHandlers.print()) // 添加执行
                .andReturn(); // 添加返回
    }
}
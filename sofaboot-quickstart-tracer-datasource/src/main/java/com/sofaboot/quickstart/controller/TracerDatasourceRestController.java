package com.sofaboot.quickstart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ljt
 * @version: $Id: TracerDatasourceRestController.java, v 0.1 2024/05/22, ljt Exp $
 */
@RestController
public class TracerDatasourceRestController {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TracerDatasourceRestController.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate template;

    /**
     * Request http://localhost:8080/create
     *
     * @return Map of Result
     */
    @RequestMapping("/create")
    public Map<String, Object> create() {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try (Connection cn = dataSource.getConnection();
             Statement st = cn.createStatement()) {

            final String sql = "DROP TABLE IF EXISTS TEST;"
                    + "CREATE TABLE TEST("
                    + "USER_ID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'userId',"
                    + "USER_NAME VARCHAR(255) DEFAULT '' COMMENT 'userName',"
                    + "CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime'"
                    + ");"
                    + "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES ('张三', CURRENT_TIMESTAMP);"
                    + "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES ('李四', CURRENT_TIMESTAMP);"
                    + "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES ('王五', CURRENT_TIMESTAMP);";

            st.execute(sql);

            resultMap.put("success", true);
            resultMap.put("result", sql);
        } catch (Throwable throwable) {
            resultMap.put("success", false);
            resultMap.put("error", throwable.getMessage());
        }
        return resultMap;
    }

    /**
     * Request http://localhost:8080/createStep
     *
     * @return Map of Result
     */
    @RequestMapping("/createStep")
    public Map<String, Object> createStep() {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 假设您有一个已经设置好的 Connection 对象叫 conn
        try {
            Connection conn = dataSource.getConnection();

            // 1. 先执行 DROP TABLE IF EXISTS
            String dropTableSql = "DROP TABLE IF EXISTS TEST;";
            Statement stmt = conn.createStatement();
            stmt.execute(dropTableSql);
            stmt.close(); // 关闭 Statement

            // 2. 再执行 CREATE TABLE
            String createTableSql = "CREATE TABLE TEST(USER_ID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'userId'," +
                    "USER_NAME VARCHAR(255) DEFAULT '' COMMENT 'userName'," +
                    "CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime');";
            stmt = conn.createStatement();
            stmt.execute(createTableSql);
            stmt.close(); // 关闭 Statement

            // 3. 接着执行 INSERT 语句（可以循环或分别执行）
            String insertSqlTemplate = "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES (?, CURRENT_TIMESTAMP);";
            PreparedStatement pstmt = conn.prepareStatement(insertSqlTemplate);

            // 插入张三
            pstmt.setString(1, "张三");
            pstmt.executeUpdate();

            // 插入李四
            pstmt.setString(1, "李四");
            pstmt.executeUpdate();

            // 插入王五
            pstmt.setString(1, "王五");
            pstmt.executeUpdate();

            pstmt.close(); // 关闭 PreparedStatement

            resultMap.put("success", true);
            resultMap.put("result", dropTableSql + createTableSql + insertSqlTemplate);

        } catch (Throwable throwable) {
            resultMap.put("success", false);
            resultMap.put("error", throwable.getMessage());
        }

        return resultMap;
    }

    /**
     * Request http://localhost:8080/execute
     *
     * @return Map of Result
     */
    @RequestMapping("/execute")
    public Map<String, Object> execute() {
        Map<String, Object> resultMap = new HashMap<>();

        final String sql = "DROP TABLE IF EXISTS TEST;"
                + "CREATE TABLE TEST("
                + "USER_ID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'userId',"
                + "USER_NAME VARCHAR(255) DEFAULT '' COMMENT 'userName',"
                + "CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime'"
                + ");"
                + "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES ('张三', CURRENT_TIMESTAMP);"
                + "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES ('李四', CURRENT_TIMESTAMP);"
                + "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES ('王五', CURRENT_TIMESTAMP);";
        template.execute(sql);

        resultMap.put("success", true);
        resultMap.put("result", sql);

        return resultMap;
    }

    /**
     * Request http://localhost:8080/selectOne/1
     *
     * @return Map of Result
     */
    @RequestMapping("/selectOne/{userId}")
    public Map<String, Object> selectOne(@PathVariable(value = "userId") int userId) {
        Map<String, Object> resultMap = new HashMap<>();

        final String sql = "SELECT * FROM TEST WHERE USER_ID = ?";
        Map<String, Object> item = template.queryForObject(sql, new Object[]{userId}, (result, rowNum) ->
                new HashMap<String, Object>() {{
                    put("userId", result.getInt("USER_ID"));
                    put("userName", result.getString("USER_NAME"));
                    put("createTime", result.getDate("CREATE_TIME"));
                }}
        );

        resultMap.put("success", true);
        resultMap.put("result", item);

        return resultMap;
    }

    /**
     * Request http://localhost:8080/selectAll
     *
     * @return Map of Result
     */
    @RequestMapping("/selectAll")
    public Map<String, Object> selectAll() {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        final String sql = "SELECT * FROM TEST;";
        List<Map> items = template.query(sql, (result, rowNum) ->
                new HashMap<String, Object>() {{
                    put("userId", result.getInt("USER_ID"));
                    put("userName", result.getString("USER_NAME"));
                    put("createTime", result.getDate("CREATE_TIME"));
                }}
        );

        resultMap.put("success", true);
        resultMap.put("result", items);

        return resultMap;
    }
}
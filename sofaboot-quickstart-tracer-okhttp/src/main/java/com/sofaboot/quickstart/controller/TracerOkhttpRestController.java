package com.sofaboot.quickstart.controller;

import com.sofaboot.quickstart.instance.OkhttpClientInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: ljt
 * @version: $Id: TracerOkhttpRestController.java, v 0.1 2024/05/27, ljt Exp $
 */
@RestController
public class TracerOkhttpRestController {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TracerOkhttpRestController.class);

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * Request http://localhost:8080/okhttp?name=
     *
     * @param name name
     * @return Map of Result
     */
    @RequestMapping("/okhttp")
    public Map<String, Object> okhttp(@RequestParam(value = "name", defaultValue = "okhttp") String name) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("count", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));
        return resultMap;
    }

    /**
     * Request http://localhost:8080/sync
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/sync")
    public String sync() throws Exception {
        String httpGetUrl = "http://localhost:8080/okhttp";
        String responseStr = new OkhttpClientInstance().executeGet(httpGetUrl);
        logger.info("Response is {}", responseStr);

        return responseStr;
    }
}
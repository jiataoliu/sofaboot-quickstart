package com.sofaboot.quickstart.controller;

import com.sofaboot.quickstart.instance.HttpAsyncClientInstance;
import com.sofaboot.quickstart.instance.HttpClientInstance;
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
 * @version: $Id: TracerDatasourceRestController.java, v 0.1 2024/05/20, ljt Exp $
 */
@RestController
public class TracerHttpclientRestController {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TracerHttpclientRestController.class);

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * Request http://localhost:8080/httpclient?name=
     *
     * @param name name
     * @return Map of Result
     */
    @RequestMapping("/httpclient")
    public Map<String, Object> httpclient(@RequestParam(value = "name", defaultValue = "httpclient") String name) {
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
        String httpGetUrl = "http://localhost:8080/httpclient";
        // sync
        String responseStr = new HttpClientInstance(10 * 1000).executeGet(httpGetUrl);
        logger.info("Response is {}", responseStr);

        return responseStr;
    }

    /**
     * Request http://localhost:8080/async
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/async")
    public String async() throws Exception {
        String httpGetUrl = "http://localhost:8080/httpclient";
        // async
        String asyncResponseStr = new HttpAsyncClientInstance().executeGet(httpGetUrl);
        logger.info("Async Response is {}", asyncResponseStr);
        return asyncResponseStr;
    }
}
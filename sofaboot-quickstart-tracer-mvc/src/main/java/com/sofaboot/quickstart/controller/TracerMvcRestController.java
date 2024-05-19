package com.sofaboot.quickstart.controller;

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
 * @version: $Id: TracerMvcRestController.java, v 0.1 2024/05/15, ljt Exp $
 */
@RestController
public class TracerMvcRestController {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TracerMvcRestController.class);

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * http://localhost:8080/greeting
     *
     * @param name name
     * @return map
     */
    @RequestMapping("/greeting")
    public Map<String, Object> greeting(@RequestParam(value = "name", defaultValue = "SOFATracer SpringMVC DEMO") String name) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("id", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));
        return resultMap;
    }

    /**
     * http://localhost:8080/asyncGreeting
     *
     * @param name name
     * @return map
     */
    @RequestMapping("/asyncGreeting")
    public Map<String, Object> asyncGreeting(@RequestParam(value = "name", defaultValue = "SOFATracer SpringMVC DEMO") String name) throws InterruptedException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("id", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));
        Thread.sleep(2000);
        return resultMap;
    }
}
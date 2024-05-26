package com.sofaboot.quickstart.controller;

import com.sofa.alipay.tracer.plugins.rest.SofaTracerRestTemplateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: ljt
 * @version: $Id: TracerResttemplateRestController.java, v 0.1 2024/05/27, ljt Exp $
 */
@RestController
public class TracerResttemplateRestController {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TracerResttemplateRestController.class);

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * Request http://localhost:8080/syncRest?name=syncRest
     *
     * @param name name
     * @return Map of Result
     */
    @RequestMapping("/syncRest")
    public Map<String, Object> syncRest(@RequestParam(value = "name", defaultValue = "syncRest") String name) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("count", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));

        logger.info("syncRest execute finish ...");

        return resultMap;
    }

    /**
     * Request http://localhost:8080/asyncRest?name=asyncRest
     *
     * @param name name
     * @return Map of Result
     */
    @RequestMapping("/asyncRest")
    public Map<String, Object> asyncRest(@RequestParam(value = "name", defaultValue = "asyncRest") String name) throws InterruptedException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("count", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));

        Thread.sleep(2000);
        logger.info("asyncRest execute finish ...");

        return resultMap;
    }

    /**
     * Request http://localhost:8080/sync
     *
     * @return Map of Result
     */
    @RequestMapping("/sync")
    public ResponseEntity sync() {
        String httpGetUrl = "http://localhost:8080/syncRest";
        // sync
        RestTemplate restTemplate = SofaTracerRestTemplateBuilder.buildRestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(httpGetUrl, String.class);
        logger.info("Response is {}", responseEntity);

        return responseEntity;
    }

    /**
     * Request http://localhost:8080/async
     *
     * @return Map of Result
     */
    @RequestMapping("/async")
    public ResponseEntity async() {
        String httpGetUrl = "http://localhost:8080/asyncRest";
        // async
        RestTemplate restTemplate = SofaTracerRestTemplateBuilder.buildRestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(httpGetUrl, String.class);
        logger.info("Response is {}", responseEntity);

        return responseEntity;
    }
}
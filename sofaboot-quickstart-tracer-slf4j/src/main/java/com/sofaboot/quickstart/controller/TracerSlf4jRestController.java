package com.sofaboot.quickstart.controller;

import com.alipay.common.tracer.core.async.SofaTracerRunnable;
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
 * @version: $Id: TracerSlf4jRestController.java, v 0.1 2024/05/28, ljt Exp $
 */
@RestController
public class TracerSlf4jRestController {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger("MDC-EXAMPLE");
    // private static final Logger logger = LoggerFactory.getLogger(TracerSlf4jRestController.class);

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * Request http://localhost:8080/slf4j?name=slf4j
     *
     * @param name name
     * @return Map of Result
     */
    @RequestMapping("/slf4j")
    public Map<String, Object> slf4j(@RequestParam(value = "name", defaultValue = "SOFATracer SLF4J MDC EXAMPLE") String name) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("count", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));

        long id = Thread.currentThread().getId();
        logger.info("SOFATracer Print TraceId and SpanId ");

        // Asynchronous thread transparent transmission
        final SofaTracerRunnable sofaTracerRunnable = new SofaTracerRunnable(new Runnable() {
            @Override
            public void run() {
                logger.info("SOFATracer Print TraceId and SpanId In Child Thread.");
            }
        });

        Thread thread = new Thread(sofaTracerRunnable);
        thread.start();

        return resultMap;
    }
}
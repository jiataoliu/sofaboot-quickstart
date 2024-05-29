package com.sofaboot.quickstart.controller;

import com.alipay.common.tracer.core.async.SofaTracerCallable;
import com.alipay.common.tracer.core.async.SofaTracerRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

    /**
     * Request http://localhost:8080/async
     *
     * @throws Exception
     */
    @RequestMapping("/async")
    public void async() throws Exception {
        logger.info("async start: SOFATracer Print TraceId and SpanId ");

        // Async SofaTracerRunnable
        final SofaTracerRunnable sofaTracerRunnable = new SofaTracerRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // e.printStackTrace();
                }
                logger.info("SofaTracerRunnable: SOFATracer Print TraceId and SpanId In Child Thread.");
            }
        });
        Thread thread = new Thread(sofaTracerRunnable);
        thread.start();

        // Async SofaTracerCallable
        ExecutorService executor = Executors.newCachedThreadPool();
        final SofaTracerCallable<Object> sofaTracerSpanSofaTracerCallable = new SofaTracerCallable<Object>(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    // e.printStackTrace();
                }
                logger.info("SofaTracerCallable: SOFATracer Print TraceId and SpanId In Child Thread.");
                return new Object();
            }
        });
        Future<Object> futureResult = executor.submit(sofaTracerSpanSofaTracerCallable);
        // do something in current thread
        Thread.sleep(1000);
        // another thread execute success and get result
        Object objectReturn = futureResult.get();

        logger.info("async end: SOFATracer Print TraceId and SpanId ");
    }
}
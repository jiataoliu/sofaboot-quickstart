package com.sofaboot.quickstart.provider;

import com.sofaboot.quickstart.facade.SampleJvmService;

/**
 * @author: ljt
 * @version: $Id: SampleJvmServiceImpl.java, v 0.1 2024/05/12, ljt Exp $
 */
public class SampleJvmServiceImpl implements SampleJvmService {
    private String message;

    public SampleJvmServiceImpl(String message) {
        this.message = message;
    }

    public SampleJvmServiceImpl() {
    }

    @Override
    public String message() {
        System.out.println(message);
        return message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
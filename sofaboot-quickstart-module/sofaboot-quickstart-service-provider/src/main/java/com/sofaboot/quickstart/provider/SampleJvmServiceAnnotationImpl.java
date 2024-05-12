package com.sofaboot.quickstart.provider;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.sofaboot.quickstart.facade.SampleJvmService;

/**
 * @author: ljt
 * @version: $Id: SampleJvmServiceAnnotationImpl.java, v 0.1 2024/05/12, ljt Exp $
 */
@SofaService(uniqueId = "annotationImpl")
public class SampleJvmServiceAnnotationImpl implements SampleJvmService {
    @Override
    public String message() {
        String message = "Hello, jvm service annotation implementation.";
        System.out.println(message);
        return message;
    }
}
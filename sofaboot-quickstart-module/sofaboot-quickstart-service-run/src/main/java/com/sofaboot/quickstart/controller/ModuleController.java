package com.sofaboot.quickstart.controller;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sofaboot.quickstart.facade.SampleJvmService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author: ljt
 * @version: $Id: ModuleController.java, v 0.1 2024/05/12, ljt Exp $
 */
@RestController
public class ModuleController {
    @SofaReference
    private SampleJvmService sampleJvmService;

    @SofaReference(uniqueId = "annotationImpl")
    private SampleJvmService sampleJvmServiceAnnotationImpl;

    @SofaReference(uniqueId = "serviceClientImpl")
    private SampleJvmService sampleJvmServiceClientImpl;

    public ModuleController() {
    }

    @RequestMapping("/serviceWithoutUniqueId")
    public String serviceWithoutUniqueId() throws IOException {
        return sampleJvmService.message();
    }

    @RequestMapping("/annotationImplService")
    public String annotationImplService() throws IOException {
        return sampleJvmServiceAnnotationImpl.message();
    }

    @RequestMapping("/serviceClientImplService")
    public String serviceClientImplService() throws IOException {
        return sampleJvmServiceClientImpl.message();
    }
}
package com.sofaboot.quickstart.consumer;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.aware.ClientFactoryAware;
import com.alipay.sofa.runtime.api.client.ClientFactory;
import com.alipay.sofa.runtime.api.client.ReferenceClient;
import com.alipay.sofa.runtime.api.client.param.ReferenceParam;
import com.sofaboot.quickstart.facade.SampleJvmService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: ljt
 * @version: $Id: JvmServiceConsumer.java, v 0.1 2024/05/12, ljt Exp $
 */
public class JvmServiceConsumer implements ClientFactoryAware {
    private ClientFactory clientFactory;

    @Autowired
    private SampleJvmService sampleJvmService;

    @SofaReference(uniqueId = "annotationImpl")
    private SampleJvmService sampleJvmServiceByFieldAnnotation;

    public void init() {
        sampleJvmService.message();
        sampleJvmServiceByFieldAnnotation.message();

        ReferenceClient referenceClient = clientFactory.getClient(ReferenceClient.class);
        ReferenceParam<SampleJvmService> referenceParam = new ReferenceParam<SampleJvmService>();
        referenceParam.setInterfaceType(SampleJvmService.class);
        referenceParam.setUniqueId("serviceClientImpl");
        SampleJvmService sampleJvmServiceClientImpl = referenceClient.reference(referenceParam);
        sampleJvmServiceClientImpl.message();
    }

    public void setClientFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
}
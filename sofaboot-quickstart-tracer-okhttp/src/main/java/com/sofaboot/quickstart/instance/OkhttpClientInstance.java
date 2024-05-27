package com.sofaboot.quickstart.instance;

import com.alipay.common.tracer.core.utils.StringUtils;
import com.alipay.sofa.tracer.plugins.okhttp.SofaTracerOkHttpBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author: ljt
 * @version: $Id: OkhttpClientInstance.java, v 0.1 2024/05/27, ljt Exp $
 */
public class OkhttpClientInstance {

    private OkHttpClient okHttpClient;

    public OkhttpClientInstance() {
        this.okHttpClient = getOkHttpClient();
    }

    private OkHttpClient getOkHttpClient() {
        if (okHttpClient != null) {
            return okHttpClient;
        } else {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            return SofaTracerOkHttpBuilder.clientBuilder(builder).build();
        }
    }

    public String executeGet(String url) throws Exception {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        Request request = new Request.Builder().url(url).build();
        return okHttpClient.newCall(request).execute().body().string();
    }
}
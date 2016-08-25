package com.zy.fengchun.okhttpdemo.request.builder;

import com.zy.fengchun.okhttpdemo.request.CustomCallback;
import com.zy.fengchun.okhttpdemo.request.RequestCall;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public abstract class OkHttpRequest {
    protected String mUrl;

    /**
     * 绑定在请求上的标签，用于取消请求，如果请求未设置标签，则request本身作为标签绑定在请求上
     */
    protected Object mTag;

    protected Map<String, String> mParams;

    protected Map<String, String> mHeaders;

    protected int mId;

    protected Request.Builder mBuilder = new Request.Builder();

    protected OkHttpRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
        mId = id;
        mHeaders = headers;
        mParams = params;
        mTag = tag;

        if (mUrl == null) {
            throw new IllegalArgumentException("url can not be null");
        }

        initBuilder();
    }

    private void initBuilder() {

        mBuilder.url(mUrl).tag(mTag);
        appendHeaders();
    }

    protected abstract RequestBody buildRequestBody();

    /**
     * 可以将requestBody 与 CustomCallback包装在一起
     * @param requestBody
     * @param callback
     * @return
     */
    protected RequestBody wrapRequestBody(RequestBody requestBody, final CustomCallback callback) {
        return requestBody;
    }

    protected abstract Request buildRequest(RequestBody requestBody);

    public RequestCall build() {
        return new RequestCall(this);
    }

    public Request generateRequest(CustomCallback callback) {
        RequestBody wrappedRequestBody = wrapRequestBody(buildRequestBody(), callback);
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }

    /**
     * 将mHeaders里面的放入mBuilder中
     */
    private void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (mHeaders == null || mHeaders.isEmpty())
            return;

        for (String key : mHeaders.keySet()) {
            headerBuilder.add(key, mHeaders.get(key));
        }
        mBuilder.headers(headerBuilder.build());
    }

    public int getId() {
        return mId;
    }
}

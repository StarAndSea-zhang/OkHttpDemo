package com.zy.fengchun.okhttpdemo.request.builder;

import com.zy.fengchun.okhttpdemo.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {
    protected String mUrl;

    protected Object mTag;

    protected Map<String, String> mHeaders;

    protected Map<String, String> mParams;

    protected int mId;

    public T id(int id) {
        mId = id;
        return (T) this;
    }

    public T url(String url) {
        mUrl = url;
        return (T) this;
    }

    public T tag(Object tag) {
        mTag = tag;
        return (T) this;
    }

    public T headers(Map<String, String> headers) {
        mHeaders = headers;
        return (T) this;
    }

    public T addHeader(String key, String val) {
        if (mHeaders == null) {
            mHeaders = new LinkedHashMap<>();
        }
        mHeaders.put(key, val);
        return (T) this;
    }

    public abstract RequestCall build();
}

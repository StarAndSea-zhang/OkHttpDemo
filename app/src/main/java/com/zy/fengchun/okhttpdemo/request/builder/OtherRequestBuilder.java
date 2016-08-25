package com.zy.fengchun.okhttpdemo.request.builder;

import com.zy.fengchun.okhttpdemo.request.RequestCall;

import okhttp3.RequestBody;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class OtherRequestBuilder extends OkHttpRequestBuilder<OtherRequestBuilder>{

    private String mMethod;

    private String mContent;

    private RequestBody mRequestBody;

    public OtherRequestBuilder(String method) {
        this.mMethod = method;
    }

    @Override
    public RequestCall build() {
        return new OtherRequest(mRequestBody,mContent,mMethod,mUrl,mTag,mParams,mHeaders,mId).build();
    }


    public OtherRequestBuilder requestBody(RequestBody requestBody) {
        mRequestBody = requestBody;
        return this;
    }

    public OtherRequestBuilder requestBody(String content) {
        mContent = content;
        return this;
    }
}

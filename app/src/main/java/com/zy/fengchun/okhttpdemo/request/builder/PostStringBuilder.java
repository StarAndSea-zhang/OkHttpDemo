package com.zy.fengchun.okhttpdemo.request.builder;

import com.zy.fengchun.okhttpdemo.request.PostStringRequest;
import com.zy.fengchun.okhttpdemo.request.RequestCall;

import okhttp3.MediaType;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>{

    private String mContent;

    /**
     * appropriate to describe
     * the content type of an HTTP request or response body.
     */
    private MediaType mMediaType;

    public PostStringBuilder content(String content) {
        mContent = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        mMediaType = mediaType;
        return this;
    }
    @Override
    public RequestCall build() {
        return new PostStringRequest(mUrl,mTag,mParams,mHeaders,mContent,mMediaType,mId).build();
    }
}

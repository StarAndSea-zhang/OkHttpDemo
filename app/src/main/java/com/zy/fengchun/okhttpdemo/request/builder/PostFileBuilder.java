package com.zy.fengchun.okhttpdemo.request.builder;

import com.zy.fengchun.okhttpdemo.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder>{

    private File mFile;

    private MediaType mMediaType;

    public OkHttpRequestBuilder file(File file){
        mFile = file;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFileRequest(mUrl,mTag,mParams,mHeaders,mId,mFile,mMediaType).build();
    }
}

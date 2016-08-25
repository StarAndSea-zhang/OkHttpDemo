package com.zy.fengchun.okhttpdemo.request;

import java.util.Map;

import com.zy.fengchun.okhttpdemo.request.builder.OkHttpRequest;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class PostStringRequest extends OkHttpRequest {
    
    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    
    private String mContent;
    
    private MediaType mMediaType;
    
    public PostStringRequest(String url,
                             Object tag,
                             Map<String, String> params,
                             Map<String, String> headers,
                             String content,
                             MediaType mediaType,
                             int id) {
        super(url, tag, params, headers, id);
        mContent = content;
        mMediaType = mediaType;
        
        if (mContent == null) {
            throw new IllegalArgumentException("the content can not be null !");
        }
        if (mMediaType == null) {
            mMediaType = MEDIA_TYPE_PLAIN;
        }
        
    }
    
    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mMediaType, mContent);
    }
    
    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return mBuilder.post(requestBody).build();
    }
}

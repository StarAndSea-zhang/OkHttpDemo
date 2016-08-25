package com.zy.fengchun.okhttpdemo.request.builder;

import android.net.Uri;

import com.zy.fengchun.okhttpdemo.request.RequestCall;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> implements HasParamsable{
    @Override
    public RequestCall build() {
        if (mParams != null) {
            mUrl = appendParams(mUrl, mParams);
        }
        return null;
    }

    private String appendParams(String mUrl, Map<String, String> mParams) {
        if (mUrl == null || mParams == null || mParams.isEmpty()) {
            return mUrl;
        }
        Uri.Builder builder = Uri.parse(mUrl).buildUpon();
        Set<String> keys = mParams.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, mParams.get(key));
        }
        return builder.build().toString();
    }

    @Override
    public OkHttpRequestBuilder params(Map<String, String> params) {
        mParams = params;
        return this;
    }

    @Override
    public OkHttpRequestBuilder addParams(String key,  String val) {
        if (mParams ==null){
            mParams = new LinkedHashMap<>();
        }
        mParams.put(key,val);
        return this;
    }
}

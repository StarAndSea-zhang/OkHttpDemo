package com.zy.fengchun.okhttpdemo.request.builder;

import java.util.Map;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public interface HasParamsable {

    OkHttpRequestBuilder params(Map<String,String> params);

    OkHttpRequestBuilder addParams(String key,String val);
}

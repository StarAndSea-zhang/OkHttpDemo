package com.zy.fengchun.okhttpdemo.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.zy.fengchun.okhttpdemo.request.RequestParamsUtils;
import com.zy.fengchun.okhttpdemo.response.ResponseCallback;
import com.zy.fengchun.okhttpdemo.model.BaseResultModel;
import com.zy.fengchun.okhttpdemo.request.NetworkRequest;

import java.io.Serializable;

/**
 * @author: fengchun on 2016/8/3 10:38
 * @description
 */
public class NetWorkUtils {

    public static synchronized void start(final Context context,
                                          final String url,
                                          final String action,
                                          final Serializable requestModel,
                                          final ResponseCallback callback) {
        start(context, url, action, requestModel, false, callback, "", "");
    }

    public static synchronized void start(final Context context,
                                          final String url,
                                          final String action,
                                          final Serializable requestModel,
                                          final boolean isShowDialog,
                                          final ResponseCallback callback,
                                          final String pid,
                                          final String secret) {
        start(context, url, action, requestModel, isShowDialog, "", callback, pid, secret);
    }

    public static synchronized void start(final Context context,
                                          final String url,
                                          final String action,
                                          final Serializable requestModel,
                                          final boolean isShowDialog,
                                          final ResponseCallback callback) {
        start(context, url, action, requestModel, isShowDialog, "", callback, "", "");
    }

    public static synchronized void start(final Context context,
                                          final String url,
                                          final String action,
                                          final Serializable requestModel,
                                          final boolean isShowDialog,
                                          final String dialogHint,
                                          final ResponseCallback callback,
                                          final String pid,
                                          final String secret) {
        ResponseCallback responseCallback = new ResponseCallback() {

            @Override
            public void onLoading() {
                callback.onLoading();
            }

            @Override
            public void onSuccess(String result) {
                BaseResultModel baseResult = JSON.parseObject(result, BaseResultModel.class);

                if (baseResult.success||baseResult.code == 0){
                    callback.onSuccess(baseResult.data);
                }
                else{
                    //登录失效
                    if (baseResult.code == 7){

                    }
                }
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        };
        String params = RequestParamsUtils.createRequestParams(requestModel,action,context,pid,secret);
        NetworkRequest.start(context,url,params,isShowDialog,dialogHint,responseCallback);
    }
}

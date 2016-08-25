package com.zy.fengchun.okhttpdemo.response;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public interface ResponseCallback {
    /**
     *
     */
    void onLoading();

    /**
     * 结果
     * @param result
     */
    void onSuccess(String result);

    /**
     * 错误
     * @param error
     */
    void onError(Error error);
}

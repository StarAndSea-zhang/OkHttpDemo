package com.zy.fengchun.okhttpdemo.request;

import com.zy.fengchun.okhttpdemo.request.builder.OkHttpRequest;
import com.zy.fengchun.okhttpdemo.utils.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class RequestCall {

    private OkHttpRequest mOkHttpRequest;

    private Request mRequest;

    private Call mCall;

    /**
     * 连接超时
     */
    private long mReadTimeOut;

    /**
     * 读取超时
     */
    private long mWriteTimeOut;

    /**
     * 连接超时
     */
    private long mConnTimeOut;

    /**
     *
     */
    private OkHttpClient mCloneClient;

    public RequestCall(OkHttpRequest request) {
        mOkHttpRequest = request;
    }

    public RequestCall readTimeOut(long readTimeOut) {
        mReadTimeOut = readTimeOut;
        return this;
    }

    public RequestCall writeTimeOut(long writeTimeOut){
        mWriteTimeOut = writeTimeOut;
        return this;
    }

    public RequestCall connTimeOut(long connTimeOut) {
        mConnTimeOut = connTimeOut;
        return this;
    }

    public Call buildCall(CustomCallback callback) {
        mRequest = generateRequest(callback);

        //如果有任意超时的情况，都需要克隆一个client？
        if (mReadTimeOut > 0 || mWriteTimeOut > 0 || mConnTimeOut > 0) {

            mReadTimeOut = mReadTimeOut > 0 ? mReadTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
            mWriteTimeOut = mWriteTimeOut > 0 ? mWriteTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
            mConnTimeOut = mConnTimeOut > 0 ? mConnTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;

            mCloneClient = OkHttpUtils.getInstance()
                    .getOkHttpClient()
                    .newBuilder()
                    .readTimeout(mReadTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(mWriteTimeOut, TimeUnit.MILLISECONDS)
                    .connectTimeout(mConnTimeOut, TimeUnit.MILLISECONDS)
                    .build();

            mCall = mCloneClient.newCall(mRequest);
        }
        else {
            mCall = OkHttpUtils.getInstance().getOkHttpClient().newCall(mRequest);
        }

        return mCall;
    }

    public Call getCall() {
        return mCall;
    }

    public OkHttpRequest getOkHttpRequest(){
        return mOkHttpRequest;
    }

    private Request generateRequest(CustomCallback callback) {
        return mOkHttpRequest.generateRequest(callback);
    }

}

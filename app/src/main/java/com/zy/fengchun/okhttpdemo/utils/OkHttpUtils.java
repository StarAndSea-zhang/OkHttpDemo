package com.zy.fengchun.okhttpdemo.utils;

import java.io.IOException;
import java.util.concurrent.Executor;

import com.zy.fengchun.okhttpdemo.Platform;
import com.zy.fengchun.okhttpdemo.request.CustomCallback;
import com.zy.fengchun.okhttpdemo.request.RequestCall;
import com.zy.fengchun.okhttpdemo.request.builder.GetBuilder;
import com.zy.fengchun.okhttpdemo.request.builder.HeadBuilder;
import com.zy.fengchun.okhttpdemo.request.builder.OtherRequestBuilder;
import com.zy.fengchun.okhttpdemo.request.builder.PostFileBuilder;
import com.zy.fengchun.okhttpdemo.request.builder.PostFormBuilder;
import com.zy.fengchun.okhttpdemo.request.builder.PostStringBuilder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 10_000L;

    private volatile static OkHttpUtils mInstance;

    private OkHttpClient mOkHttpClient;

    private Platform mPlatform;

    private OkHttpUtils() {
        mOkHttpClient = new OkHttpClient();
        mPlatform = Platform.get();
    }

    public static OkHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null)
                    mInstance = new OkHttpUtils();
            }
        }
        return mInstance;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public void execute(final RequestCall requestCall, CustomCallback callback) {
        if (callback == null)
            callback = CustomCallback.CALLBACK_DEFAULT;
        final CustomCallback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new Callback() {

            public void onFailure(Call call, IOException e) {
                if (!call.isCanceled()) {
                    sendFailResultCallback(call, e, finalCallback, id);
                }
            }


            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (!call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!" + response.code()), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is :" + response.code()), finalCallback, id);
                        return;
                    }


                    Object obj = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(obj,finalCallback,id);

                } catch (Exception e) {
                    sendFailResultCallback(call,  e, finalCallback, id);
                }finally {
                    if (response.body()!=null){
                        response.body().close();
                    }
                }
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call :mOkHttpClient.dispatcher().queuedCalls()){
            if (tag.equals(call.request().tag())){
                call.cancel();
            }
        }

        for (Call call:mOkHttpClient.dispatcher().runningCalls()){
            if (tag.equals(call.request().tag())){
                call.cancel();
            }
        }
    }

    public void sendSuccessResultCallback(final Object response, final CustomCallback callback, final int id) {
        if (callback == null)
            return;
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(response,id);
                callback.onAfter(id);
            }
        });
    }

    public void sendFailResultCallback(final Call call, final Exception e, final CustomCallback callback, final int id) {
        if (call == null)
            return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }


    public static class METHOD {
        public static final String HEAD = "HEAD";

        public static final String DELETE = "DELETE";

        public static final String PUT = "PUT";

        public static final String PATCH = "PATCH";
    }
}

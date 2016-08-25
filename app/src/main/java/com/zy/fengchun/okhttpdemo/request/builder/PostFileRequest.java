package com.zy.fengchun.okhttpdemo.request.builder;

import java.io.File;
import java.util.Map;

import com.zy.fengchun.okhttpdemo.request.CountingRequestBody;
import com.zy.fengchun.okhttpdemo.request.CustomCallback;
import com.zy.fengchun.okhttpdemo.utils.OkHttpUtils;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class PostFileRequest extends OkHttpRequest {

    private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private File mFile;

    private MediaType mMediaType;

    protected PostFileRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id, File file, MediaType mediaType) {
        super(url, tag, params, headers, id);
        mFile = file;
        mMediaType = mediaType;

        if (mFile == null) {
            throw new IllegalArgumentException("the file can not be null !");
        }

        if (mMediaType == null) {
            mMediaType = MEDIA_TYPE_STREAM;
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mMediaType, mFile);
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final CustomCallback callback) {
        if (callback == null)
            return requestBody;

        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {

            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength) {
                OkHttpUtils.getInstance().getDelivery().execute(new Runnable() {

                    @Override
                    public void run() {
                        callback.inProgress(bytesWritten
                                        * 1.0f
                                        / contentLength,
                                contentLength,
                                mId);
                    }
                });
            }
        });
        return countingRequestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return mBuilder.post(requestBody).build();
    }
}

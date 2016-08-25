package com.zy.fengchun.okhttpdemo.request.builder;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class PostFormRequest extends OkHttpRequest{

    private List<PostFormBuilder.FileInput> mFiles;

    public PostFormRequest(String url, Object tag, Map<String, String> params, Map<String, String> header, List<PostFormBuilder.FileInput> files, int id) {
        super(url,tag,params,header,id);
        mFiles = files;
    }


    @Override
    protected RequestBody buildRequestBody() {
        //如果文件为null就去创建一个
        if (mFiles == null||mFiles.isEmpty()){
            FormBody.Builder builder = new FormBody.Builder();
            addParams(builder);

            FormBody formbody = builder.build();
            return formbody;
        }else{
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            addParams(builder);

            for (int i = 0; i < mFiles.size(); i++) {
                PostFormBuilder.FileInput fileInput = mFiles.get(i);
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.mFileName)),
                        fileInput.mFile);
                builder.addFormDataPart(fileInput.mKey, fileInput.mFileName, fileBody);
            }
        }
        
        return null;
    }

    private void addParams(FormBody.Builder builder) {
        if (mParams!=null && mParams.isEmpty()){
            for (String key:mParams.keySet()){
                builder.add(key,mParams.get(key));
            }
        }
    }

    private void addParams(MultipartBody.Builder builder) {
        if (mParams!=null && mParams.isEmpty()){
            for (String key:mParams.keySet()){
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),RequestBody.create(null, mParams.get(key)));
            }
        }
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return mBuilder.post(requestBody).build();
    }
}

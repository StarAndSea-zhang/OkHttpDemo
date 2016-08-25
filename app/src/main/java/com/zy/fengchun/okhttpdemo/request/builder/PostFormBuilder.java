package com.zy.fengchun.okhttpdemo.request.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zy.fengchun.okhttpdemo.request.RequestCall;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class PostFormBuilder extends OkHttpRequestBuilder<PostFormBuilder> implements HasParamsable {
    
    private List<FileInput> mFiles = new ArrayList<>();
    
    @Override
    public OkHttpRequestBuilder params(Map<String, String> params) {
        this.mParams = params;
        return this;
    }
    
    @Override
    public OkHttpRequestBuilder addParams(String key, String val) {
        if (mParams == null){
            mParams = new LinkedHashMap<>();
        }
        mParams.put(key,val);
        return this;
    }
    
    @Override
    public RequestCall build() {
        return new PostFormRequest(mUrl, mTag, mParams, mHeaders, mFiles, mId).build();
    }
    
    public static class FileInput {
        public String mKey;
        
        public String mFileName;
        
        public File mFile;
        
        public FileInput(String name, String filename, File file) {
            mKey = name;
            mFileName = filename;
            mFile = file;
        }
        
        @Override
        public String toString() {
            return "FileInput{" + "key='" + mKey + '\'' + ", filename='" + mFileName + '\'' + ", file=" + mFile + '}';
        }
    }
}

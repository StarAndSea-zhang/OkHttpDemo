package com.zy.fengchun.okhttpdemo.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class BaseRequestData<T> implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @JSONField(name = "pid")
    public String mPid;

    /**
     *
     */
    @JSONField(name = "method")
    public String mMethod;

    /**
     *
     */
    @JSONField(name = "requestid")
    public String mRequestid;

    /**
     *
     */
    @JSONField(name = "timestamp")
    public String mTimestamp;

    /**
     *
     */
    @JSONField(name = "token")
    public String mToken;

    /**
     *
     */
    @JSONField(name = "data")
    public T mData;

    /**
     *
     */
    @JSONField(name = "mcode")
    public String mCode;

    /**
     *
     */
    @JSONField(name = "clienttype")
    public int clienttype;

    /**
     *
     */
    @JSONField(name = "version")
    public String mVision;

    /**
     *
     */
    @JSONField(name = "sessionID")
    public String sessionID;
}


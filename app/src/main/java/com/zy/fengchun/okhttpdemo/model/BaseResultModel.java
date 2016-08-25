package com.zy.fengchun.okhttpdemo.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: fengchun on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class BaseResultModel {
    private static final long serialVersionUID = 1L;

    /**
     * 是否成功 true 成功 false 失败
     */
    @JSONField(name = "success")
    public boolean success;

    /**
     * 返回值编号
     */
    @JSONField(name = "code")
    public int code = -1;

    /**
     * 返回信息
     */
    @JSONField(name = "message")
    public String message;

    /**
     * 返回数据 具体的返回数据，封装为json格式的字符串，具体格式详见每个接口的data返回说明
     */
    @JSONField(name = "data")
    public String data;

    /**
     * 请求编号 合作商请求时发过来的请求特征值
     */
    @JSONField(name = "requestid")
    public String requestid;

    /**
     * 会话标示
     */
    @JSONField(name = "token")
    public String token;
}

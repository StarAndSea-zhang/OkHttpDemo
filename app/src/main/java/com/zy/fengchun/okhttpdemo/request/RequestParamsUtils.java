package com.zy.fengchun.okhttpdemo.request;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.zy.fengchun.okhttpdemo.BaseApplication;
import com.zy.fengchun.okhttpdemo.model.BaseRequestData;
import com.zy.fengchun.okhttpdemo.BuildConfig;
import com.zy.fengchun.okhttpdemo.utils.DeviceUtil;
import com.zy.fengchun.okhttpdemo.utils.Md5Sign;
import com.zy.fengchun.okhttpdemo.utils.PackageUtils;
import com.zy.fengchun.okhttpdemo.utils.SPUtils;
import com.zy.fengchun.okhttpdemo.utils.StringUtils;

import org.json.JSONException;

/**
 * @author: zhangyu on 2016/8/3 10:38
 * @emial : 18142561429
 * @description
 */
public class RequestParamsUtils {

    /**
     * 验证的pid
     */
    private static String mPid;

    /**
     * 验证的secret
     */
    private static String mSecret;

    public static String createRequestParams(Serializable requestModel, String requestMethod, Context context, String pid, String secret) {
        if ("".equals(pid) && "".equals(secret)) {  
            //默认的签名验证
            //注意这里的PID
            mPid = BuildConfig.PID;
            mSecret = BuildConfig.PID_SECRET;
        } else { //专属不同业务的签名验证
            mPid = pid;
            mSecret = secret;
        }

        BaseRequestData baseRequestData = new BaseRequestData();
        baseRequestData.mPid = mPid;
        baseRequestData.mMethod = requestMethod;
        baseRequestData.mVision = PackageUtils.getVersionCode(context)+"";
        baseRequestData.mRequestid = getRequestID();
        baseRequestData.mTimestamp = getTimestamp();

        SPUtils spUtils = new SPUtils(context);
        baseRequestData.mToken = spUtils.getValue("sessionId", "sessionId");

        baseRequestData.mData = requestModel;
        baseRequestData.mCode = getCode();

        // baseRequest.requestType = 2;
        baseRequestData.clienttype = 1;
        String string = JSON.toJSONString(baseRequestData);
        string = string.replace("\\n", "");
        string = string.replace("\\r", "");
        string = string.replace(" ", "");
        Pattern p = Pattern.compile("\"data\"\\s*?:\\s*?(.*[,|}])?\\s*?[,|}]");
        Matcher mm = p.matcher(string);
        String preSigns = null;
        while (mm.find()) {
            preSigns = mm.group();
            preSigns = preSigns.substring(7, preSigns.length() - 1);
        }

        String sign = sign(baseRequestData, preSigns);
        return null;
    }

    @SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
    @SuppressWarnings("rawtypes")
    public static String sign(BaseRequestData baseRequest, String preSign) {

        if (baseRequest == null) {
            return "";
        }
        org.json.JSONObject json = null;
        try {
            String jStr = JSON.toJSONString(baseRequest);
            json = new org.json.JSONObject(jStr);
            Iterator it = json.keys();
            ArrayList<String> arr = new ArrayList<String>();
            /** 只是对这几个key排序，BaseRequestData中有多余的需要剔除 */
            String[] arrays = new String[] { "pid", "data", "clienttype", "timestamp", "method", "token" };
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                if (Arrays.asList(arrays).contains(key)) {
                    arr.add(key);
                }

            }
            Collections.sort(arr);
            StringBuffer sb = new StringBuffer();
            String value = null;
            for (String str : arr) {
                /** 对data内部排序 */
                if ("data".equals(str)) {
                    value = preSign;
                }
                else {
                    value = json.optString(str);
                }
                if (!StringUtils.isNullOrEmpty(value)) {
                    sb.append('&').append(str).append('=').append(value);
                }
            }
            /** 去掉第一个& */
            String sign = sb.substring(1);
            Calendar mCalendar = Calendar.getInstance();
            Date date = mCalendar.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            sign = sign + simpleDateFormat.format(date) + getPidSafeCode() + getSecret() + getPID();
            sign = sign.replace(" ", "");// 去掉空格
            String afterSign = Md5Sign.md5(sign);
            afterSign = afterSign.toUpperCase();
            StringBuilder stringBuilder = new StringBuilder(jStr);
            stringBuilder.replace(jStr.length() - 1, jStr.length(), ",\"sign\":\"" + afterSign + "\"}");
            String finalString = stringBuilder.toString();
            return finalString;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

    }
    public static String getRequestID() {
        return System.currentTimeMillis() + "";
    }

    public static String getTimestamp() {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        Date date = new Date();
        return sd.format(date);
    }

    public static String getCode() {
        return "ANDROID_" + DeviceUtil.getDeviceUniqueID(BaseApplication.getInstance());
    }

    /**
     * @return
     */
    public static String getPidSafeCode() {
        return "";
    }

    public static String getSecret() {
        return mSecret;
    }

    public static String getPID() {
        return mPid;
    }
}

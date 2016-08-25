package com.zy.fengchun.okhttpdemo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class Md5Sign {
    /**
     * TODO 添加方法注释.
     *
     * @param plainText
     *            种子
     * @return String MD5字符串
     */
    public static String md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] b = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {

                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            // System.out.println("result: " + buf.toString());
            // // 32位的加密
            // System.out.println("result: " + buf.toString().substring(8, 24));
            // 16位的加密
            return buf.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

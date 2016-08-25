package com.zy.fengchun.okhttpdemo.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class DeviceUtil {
    /**
     * 获取设备唯一码
     *
     * @param context
     * @return
     */
    public static String getDeviceUniqueID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);//
        String uniqueId = "";
        if (tm.getDeviceId() != null && !StringUtils.isNullOrEmpty(tm.getDeviceId())
                && (numberOfDulp(tm.getDeviceId()) <= 10)) {
            uniqueId = "I" + tm.getDeviceId();// 1
        }
        else {
            String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            if (androidId != null) {
                uniqueId = "A" + androidId;// 2
            }
            else {
                String uuid = getMyUUID();
                if (uuid != null) {
                    uniqueId = "U" + getMyUUID().replace("-", ""); // 3
                }
                else {
                    try {
                        uniqueId = "H" + getHardInfo();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return uniqueId;
    }

    private static int numberOfDulp(String strs) {
        int max = 1;
        for (int i = 0; i < strs.length() - 1; i++) {
            int count = 1;
            for (int j = i + 1; j < strs.length(); j++) {
                if (strs.charAt(i) == strs.charAt(j)) {
                    count++;
                }
            }
            if (max < count) {
                max = count;
            }

        }
        return +max;
    }

    private static String getMyUUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        return uniqueId;
    }

    @SuppressWarnings("deprecation")
    private static String getHardInfo() {
        String szDevIDShort = "35" + Build.BOARD.length() % 10
                + Build.BRAND.length() % 10
                + Build.CPU_ABI.length() % 10
                + Build.DEVICE.length() % 10
                + Build.DISPLAY.length() % 10
                + Build.HOST.length() % 10
                + Build.ID.length() % 10
                + Build.MANUFACTURER.length() % 10
                + Build.MODEL.length() % 10
                + Build.PRODUCT.length() % 10
                + Build.TAGS.length() % 10
                + Build.TYPE.length() % 10
                + Build.USER.length() % 10;
        return szDevIDShort;
    }
}

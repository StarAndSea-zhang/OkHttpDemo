package com.zy.fengchun.okhttpdemo.model;

import android.util.Log;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class L {
        private static boolean debug = false;

        public static void e(String msg) {
            if (debug) {
                Log.e("OkHttp", msg);
            }
        }

}

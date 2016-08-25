package com.zy.fengchun.okhttpdemo.request;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.zy.fengchun.okhttpdemo.utils.LoadingDialog;
import com.zy.fengchun.okhttpdemo.response.ResponseCallback;
import com.zy.fengchun.okhttpdemo.utils.OkHttpUtils;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class NetworkRequest {
    private static LoadingDialog mDialog;
    /**
     * 获取网络返回d字段数据
     *
     * @param context
     * @param url
     *            请求根地址
     * @param params
     *            请求参数
     * @param isShowDialog
     *            是否显示对话框,true显示，false不显示
     * @param callback
     *            {@link ResponseCallback}
     */
    public static synchronized void start(final Context context,
                                          final String url,
                                          final String params,
                                          final boolean isShowDialog,
                                          final String dialogHint,
                                          final ResponseCallback callback) {
        if (!isNetworkConnected(context) && context instanceof Activity) {
            // TODO 显示无网络对话框
            Toast.makeText(context,"无网络连接",Toast.LENGTH_SHORT);
            return;
        }

    }
    /**
     * 关闭对话框
     */
    public static void dismissLoadingDialog(Context context) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public static void showLoadingDialog(Context context) {
        showLoadingDialog(context, "");
    }

    /**
     * 唤起正在加载的dialog
     *
     * @param message
     *            context
     * @param context
     *            需要显示的消息
     */
    public static void showLoadingDialog(final Context context, final String message) {
        mDialog = new LoadingDialog(context, message);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                cancelRequestByTag(context);
            }
        });
        mDialog.show();
    }
    /**
     * 检测网络连接是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        /////////////////////////////////context!=null//////////////////////////////////////////////
        if (context != null) {
            ConnectivityManager mConnectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取网络返回d字段数据
     *
     * @param context
     * @param url
     *            请求根地址
     * @param params
     *            请求参数
     * @param isShowDialog
     *            是否显示对话框,true显示，false不显示
     * @param callback
     *            {@link ResponseCallback}
     */
    public static synchronized void start(final Context context,
                                          final String url,
                                          final String params,
                                          final boolean isShowDialog,
                                          final ResponseCallback callback) {
        start(context, url, params, isShowDialog, "", callback);
    }

    public static boolean isShowLoadingDialog() {
        if (mDialog == null) {
            return false;
        }
        return mDialog.isShowing();
    }

    public static void cancelRequestByTag(Object object) {
        OkHttpUtils.getInstance().cancelTag(object);
    }
}

package com.zy.fengchun.okhttpdemo.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zy.fengchun.okhttpdemo.R;
import com.zy.fengchun.okhttpdemo.request.NetworkRequest;
import com.zy.fengchun.okhttpdemo.utils.StringUtils;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class LoadingDialog extends Dialog {

    private String mHint;

    public LoadingDialog(Context context, String hint) {
        super(context, R.style.CommanDialogStyle);
        mHint = hint;
        initView();
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        setCanceledOnTouchOutside(false);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading, null);
        setContentView(view);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_loading);
        TextView textView = (TextView) view.findViewById(R.id.tv_hint);

        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();

        if (!StringUtils.isNullOrEmpty(mHint)) {
            textView.setText(mHint);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NetworkRequest.cancelRequestByTag(getContext());
    }
}

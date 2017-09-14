package com.zwd.express.util;

import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwd.express.R;

/**
 * Created by asus-pc on 2017/7/8.
 */

public class CountDownUtil extends CountDownTimer {
    private TextView textView;
    private ImageView imageView;

    public CountDownUtil(long millisInFuture, long countDownInterval,
                         TextView textView, ImageView imageView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.imageView = imageView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        imageView.setImageResource(R.mipmap.icon_unverify);
        imageView.setClickable(false);
        textView.setText("("+millisUntilFinished /1000+")");
    }

    @Override
    public void onFinish() {
        imageView.setImageResource(R.mipmap.icon_verify);
        imageView.setClickable(true);
        textView.setText("("+"重新获取"+")");
    }
}

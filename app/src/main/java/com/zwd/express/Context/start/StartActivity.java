package com.zwd.express.Context.start;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.express.Context.login.View.LoginActivity;
import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends BaseActivity {


    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.btn)
    TextView btn;

    @Override
    protected int getContentView() {
        return R.layout.activity_start;
    }

    @Override
    protected void initViews() {
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        new Thread(new Runnable() {
            @Override
            public void run() {
                StartActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(StartActivity.this).load(R.mipmap
                                .gif_start).into(img);
                        btn.setText("开启你的旅行吧");
                        btn.setBackgroundDrawable(getResources()
                                .getDrawable(R.drawable.btn_start));
                    }
                });
            }
        }).start();

    }


    @OnClick(R.id.btn)
    public void onViewClicked() {
        goActivity(LoginActivity.class);
    }



}

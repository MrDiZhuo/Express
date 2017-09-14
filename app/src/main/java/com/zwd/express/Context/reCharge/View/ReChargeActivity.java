package com.zwd.express.Context.reCharge.View;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.layout.NormalToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReChargeActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.txt_10)
    TextView txt_10;
    @BindView(R.id.txt_20)
    TextView txt_20;
    @BindView(R.id.txt_50)
    TextView txt_50;
    @BindView(R.id.txt_100)
    TextView txt_100;
    @BindView(R.id.img01)
    ImageView img01;
    @BindView(R.id.img02)
    ImageView img02;
    @BindView(R.id.edit)
    EditText edit;

    @Override
    protected int getContentView() {
        return R.layout.activity_re_charge;
    }

    @Override
    protected void initViews() {
        initActionBar(toolbar);
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setTitle("钱包充值");
        toolbar.setBackground(getResources().getDrawable(R.mipmap
                .bg_nomal_tool));
        toolbar.hideRight();
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @OnClick({R.id.txt_10, R.id.txt_20, R.id.txt_50, R.id.txt_100,R.id.pay01, R.id.pay02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_10:
                txt_10.setBackgroundResource(R.mipmap.bg_yello);
                txt_20.setBackgroundResource(R.mipmap.bg_gray);
                txt_50.setBackgroundResource(R.mipmap.bg_gray);
                txt_100.setBackgroundResource(R.mipmap.bg_gray);
                edit.setHint("10.00");
                break;
            case R.id.txt_20:
                txt_10.setBackgroundResource(R.mipmap.bg_gray);
                txt_20.setBackgroundResource(R.mipmap.bg_yello);
                txt_50.setBackgroundResource(R.mipmap.bg_gray);
                txt_100.setBackgroundResource(R.mipmap.bg_gray);
                edit.setHint("20.00");
                break;
            case R.id.txt_50:
                txt_10.setBackgroundResource(R.mipmap.bg_gray);
                txt_20.setBackgroundResource(R.mipmap.bg_gray);
                txt_50.setBackgroundResource(R.mipmap.bg_yello);
                txt_100.setBackgroundResource(R.mipmap.bg_gray);
                edit.setHint("50.00");
                break;
            case R.id.txt_100:
                txt_10.setBackgroundResource(R.mipmap.bg_gray);
                txt_20.setBackgroundResource(R.mipmap.bg_gray);
                txt_50.setBackgroundResource(R.mipmap.bg_gray);
                txt_100.setBackgroundResource(R.mipmap.bg_yello);
                edit.setHint("100.00");
                break;
            case R.id.pay01:
                img01.setImageResource(R.drawable.circle_yello);
                img02.setImageResource(R.drawable.circle_empty03);
                break;
            case R.id.pay02:
                img01.setImageResource(R.drawable.circle_empty03);
                img02.setImageResource(R.drawable.circle_yello);
                break;
        }
    }


}

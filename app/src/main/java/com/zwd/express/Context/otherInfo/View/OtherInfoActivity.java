package com.zwd.express.Context.otherInfo.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.express.Context.otherInfo.Module.OtherInfoGet;
import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class OtherInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.img_status)
    ImageView imgStatus;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_sex)
    TextView txtSex;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_credit)
    TextView txtCredit;
    @BindView(R.id.img_credit)
    ImageView imgCredit;

    private int id;
    private Bundle bundle;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "User_ReturnOrderinfo.aspx";

    @Override
    protected int getContentView() {
        return R.layout.activity_other_info;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("mTargetId");
        Log.d("---", id + "");
        initActionBar(toolbar);
        Url_();
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.hideTitle();
        toolbar.hideRight();
    }

    private void Url_() {
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.returnOrderinfo(id, baseUrl, url, new
                CustomCallBack<RemoteDataResult<OtherInfoGet>>() {

            @Override
            public void onSuccess(Response<RemoteDataResult<OtherInfoGet>>
                                          response) {
                OtherInfoGet get = response.body().getData();
                initView(get);
            }

            @Override
            public void onFail(String message) {
                Log.d("---fail",message);
            }
        });
    }

    private void initView(OtherInfoGet get) {
        if (get.getHeading() != null && get.getHeading().length() > 0) {
            Glide.with(this).load(get.getHeading()).into(head);
        } else {
            Glide.with(this).load(R.mipmap.pic_head).into(head);
        }
        if (get.getUserstatus() == 0) {
            imgStatus.setImageResource(R.mipmap.icon_uncertify);
        } else {
            imgStatus.setImageResource(R.mipmap.icon_certify);
        }
        txtName.setText(get.getName());
        txtSex.setText(get.getSex());
        txtPhone.setText(get.getUsername());
        txtCredit.setText(String.valueOf(get.getCredit()));
        if (get.getCredit() < 60) {
            imgCredit.setImageResource(R.mipmap.icon_fail);
        } else if (get.getCredit() > 59 && get.getCredit() < 90) {
            imgCredit.setImageResource(R.mipmap.icon_well);
        } else {
            imgCredit.setImageResource(R.mipmap.icon_excellent);
        }
    }


}

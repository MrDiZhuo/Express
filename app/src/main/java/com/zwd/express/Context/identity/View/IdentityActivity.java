package com.zwd.express.Context.identity.View;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwd.express.Context.identity.Module.IndentityGet;
import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class IdentityActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.ll_un)
    LinearLayout llUn;
    @BindView(R.id.ll_over)
    LinearLayout llOver;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_number)
    TextView txtNumber;

    private int id;
    private String Qiniu_token;
    private Bundle bundle;

    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "User_ReturnIdcard.aspx";

    @Override
    protected int getContentView() {
        return R.layout.activity_identity;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        Qiniu_token = bundle.getString("Qiniu_token");
        initActionBar(toolbar);
        Url_getStatus();
    }

    private void Url_getStatus() {
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.returnIdcard(id, baseUrl, url, new
                CustomCallBack<RemoteDataResult<IndentityGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<IndentityGet>>
                                          response) {
                IndentityGet get = response.body().getData();
                initView(get);

            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void initView(IndentityGet get) {
        if (get.getUserstatus() == 0) {
            llUn.setVisibility(View.VISIBLE);
            llOver.setVisibility(View.GONE);
        } else if (get.getUserstatus() == 1) {
            llUn.setVisibility(View.GONE);
            llOver.setVisibility(View.VISIBLE);
            txtName.setText(get.getName());
            txtNumber.setText(get.getNumber());
        }
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.hideRight();
        toolbar.hideTitle();
        toolbar.setBack(R.mipmap.bg_nomal_tool);
    }


    @OnClick({R.id.txt, R.id.btn})
    public void onViewClicked(View view) {
        bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("Qiniu_token",Qiniu_token);
        switch (view.getId()) {
            case R.id.txt:
                finish();
                goActivity(UnVerifyActivity.class, bundle);
                break;
            case R.id.btn:
                finish();
                goActivity(UnVerifyActivity.class, bundle);
                break;
        }
    }

}

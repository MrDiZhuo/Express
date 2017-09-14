package com.zwd.express.Context.integrate.View;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwd.express.Context.integrate.Module.GetCouponPost;
import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.base.BaseAppManager;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class IntegrateActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.ll_declare)
    LinearLayout ll_declare;
    @BindView(R.id.ll_chit)
    LinearLayout ll_chit;

    private int id;
    private Bundle bundle;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Wallet_AddCoupon.aspx";
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    @Override
    protected int getContentView() {
        return R.layout.activity_integrate;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        initActionBar(toolbar);
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setBack(R.mipmap.bg_nomal_tool);
        toolbar.hideRight();
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("我的积分");
    }


    @OnClick({R.id.chit, R.id.declare,R.id.two, R.id.five, R.id.ten})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chit:
                ll_chit.setVisibility(View.VISIBLE);
                ll_declare.setVisibility(View.GONE);
                break;
            case R.id.declare:
                ll_declare.setVisibility(View.VISIBLE);
                ll_chit.setVisibility(View.GONE);
                break;
            case R.id.two:
                Url_getCoupon(2.00,200);
                break;
            case R.id.five:
                Url_getCoupon(5.00,500);
                break;
            case R.id.ten:
                Url_getCoupon(10.00,1000);
                break;
        }
    }

    ///兑换代金券
    private void Url_getCoupon(double money, int mark) {
        GetCouponPost post = new GetCouponPost(id, money, mark);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.getCoupon(post, baseUrl, url, new
                CustomCallBack<RemoteDataResult>() {

            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                dialog_("兑换成功!");
            }

            @Override
            public void onFail(String message) {
                Log.d("---fail",message);
                dialog_("兑换失败!");
            }
        });
    }

    private void dialog_(String str) {
        builder = new AlertDialog.Builder(BaseAppManager.getInstance()
                .getForwardActivity());
        View view1 = LayoutInflater.from(BaseAppManager.getInstance()
                .getForwardActivity()).inflate(R.layout
                .dialog_back, null);
        TextView no = (TextView) view1.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView txt = (TextView) view1.findViewById(R.id.txt);
        txt.setText(str);
        builder.setView(view1);
        dialog = builder.create();
        dialog.show();
    }


}

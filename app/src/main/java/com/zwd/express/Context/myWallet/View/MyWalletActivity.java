package com.zwd.express.Context.myWallet.View;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zwd.express.Context.bill.View.BillActivity;
import com.zwd.express.Context.myWallet.Module.ChitGet;
import com.zwd.express.Context.myWallet.Module.MyWalletGet;
import com.zwd.express.Context.reCharge.View.ReChargeActivity;
import com.zwd.express.Context.withdraw.View.WithdrawActivity;
import com.zwd.express.R;
import com.zwd.express.adapter.ChitAdapter;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;
import com.zwd.express.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class MyWalletActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    NormalToolBar toolBar;
    @BindView(R.id.surplus)
    TextView surplus;
    @BindView(R.id.coupon)
    TextView coupon;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private ChitAdapter adapter;
    private List<ChitGet> chitGets = new ArrayList<>();
    private int id;
    private Bundle bundle;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Wallet_ReturnWallet.aspx";
    private String url_chit = "Wallet_ReturnCouponList.aspx";

    @Override
    protected int getContentView() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        initActionBar(toolBar);
        Url_wallet();
        Url_Chit();
    }


    private void initActionBar(NormalToolBar toolBar) {
        toolBar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolBar.setTitle("我的钱包");
        toolBar.setRightImg(R.mipmap.icon_bill);
        toolBar.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putInt("id",id);
                goActivity(BillActivity.class,bundle);
            }
        });
    }

    @OnClick({R.id.recharge, R.id.withdraw, R.id.chit})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.recharge:
                goActivity(ReChargeActivity.class);
                break;
            case R.id.withdraw:
                goActivity(WithdrawActivity.class);
                break;
            case R.id.chit:
                if(chitGets.size()>0){
                    builder = new AlertDialog.Builder(this);
                    View view = LayoutInflater.from(this).inflate(R.layout
                            .dialog_chit, null);
                    RecyclerView rv = (RecyclerView) view.findViewById(R.id
                            .recycler);
                    rv.setAdapter(adapter);
                    builder.setView(view);
                    dialog = builder.create();
                    dialog.show();
                }else {
                    ToastUtil.showShort(MyWalletActivity.this,"没有代金券!");
                }

                break;
        }
    }

    ////钱包
    private void Url_wallet() {
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.returnWallet(id, baseUrl, url, new
                CustomCallBack<RemoteDataResult<MyWalletGet>>() {

            @Override
            public void onSuccess(Response<RemoteDataResult<MyWalletGet>>
                                          response) {
                MyWalletGet get = response.body().getData();
                surplus.setText("¥"+get.getSurplus());
                coupon.setText(get.getCoupon()+"张");
            }

            @Override
            public void onFail(String message) {
                Log.d("----fail",message);
            }
        });
    }

    ///代金券列表
    private void Url_Chit(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.couponList(id, baseUrl, url_chit, new CustomCallBack<RemoteDataResult<List<ChitGet>>>() {


            @Override
            public void onSuccess(Response<RemoteDataResult<List<ChitGet>>>
                                          response) {
                chitGets.clear();
                chitGets = response.body().getData();
                if (chitGets.size()>0){
                    adapter = new ChitAdapter(MyWalletActivity.this, chitGets);
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }


}

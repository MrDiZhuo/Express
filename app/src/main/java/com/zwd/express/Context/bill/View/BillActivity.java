package com.zwd.express.Context.bill.View;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwd.express.Context.bill.Module.BillGet;
import com.zwd.express.Context.homePage.View.HomePageActivity;
import com.zwd.express.R;
import com.zwd.express.Widget02.timePicker.WheelView;
import com.zwd.express.Widget02.timePicker.WheelView_bill;
import com.zwd.express.adapter.BillAdapter;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class BillActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private BillAdapter adapter;
    private List<BillGet> billGets = new ArrayList<>();
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    WheelView_bill wv01,wv02;
    private List<String> year = new ArrayList<>();
    private List<String> month = new ArrayList<>();
    private String choose_year,choose_month,choose;

    private int id;
    private Bundle bundle;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url="Wallet_ReturnWalletList.aspx";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_bill;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        initActionBar(toolbar);
        initRefresh();
        Url_Bill();

        initTimeList();
    }
    private void initRefresh() {
        refreshLayout.setColorSchemeResources(R.color.yello);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setProgressViewEndTarget(true, 100);
        refreshLayout.setOnRefreshListener(this);
    }
    //下拉刷新
    @Override
    public void onRefresh() {
        Url_Bill();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initTimeList() {
        year.add("2013"); year.add("2014");year.add("2015");year.add("2016");year.add("2017");

        for(int i=1;i<10;i++){
            month.add("0"+i);
        }
        for(int i=10;i<13;i++){
            month.add(i+"");
        }
    }

    private void initRecycler() {
        txt_date.setText("2017-01");
        adapter = new BillAdapter(this,filter("2017-01"));
        recycler.setAdapter(adapter);
    }


    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("账单");
        toolbar.hideRight();
    }

    @OnClick(R.id.calendar)
    public void onViewClicked() {
        builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bill,null);
        final TextView txt = (TextView)view.findViewById(R.id.txt);
        wv01 = (WheelView_bill)view.findViewById(R.id.wheelview01);
        wv02 = (WheelView_bill)view.findViewById(R.id.wheelview02);
        wv01.setSeletion(2); wv02.setSeletion(6);
        wv01.setOffset(1);wv01.setItems(year);
        wv02.setOffset(1);wv02.setItems(month);
        choose_year = wv01.getSeletedItem();choose_month = wv02.getSeletedItem();
        txt.setText(choose_year+"-"+choose_month);
        wv01.setOnWheelViewListener(new WheelView_bill.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                //selectedIndex当前高亮的位置
                //item当前高亮的位置的内容
                choose_year = item;
                txt.setText(choose_year+"-"+choose_month);
            }
        });
        wv02.setOnWheelViewListener(new WheelView_bill.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                //selectedIndex当前高亮的位置
                //item当前高亮的位置的内容
                choose_month = item;
                txt.setText(choose_year+"-"+choose_month);
            }
        });
        ImageView yes = (ImageView)view.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose = txt.getText().toString();
                txt_date.setText(choose);
                adapter = new BillAdapter(view.getContext(),filter(choose));
                recycler.setAdapter(adapter);
                dialog.dismiss();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private List<BillGet> filter(String choose){
        List<BillGet> get = new ArrayList<>();
        for(int i=0;i<billGets.size();i++){
            String str = billGets.get(i).getDtime().substring(0,7);
            if(str.equals(choose)){
                get.add(billGets.get(i));
            }
        }
        return get;
    }

    ///列表
    private void Url_Bill(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.returnBill(id, baseUrl, url, new CustomCallBack<RemoteDataResult<List<BillGet>>>() {

            @Override
            public void onSuccess(Response<RemoteDataResult<List<BillGet>>>
                                          response) {
                billGets.clear();
                billGets = response.body().getData();
                initRecycler();

            }

            @Override
            public void onFail(String message) {
                Log.d("---fail",message);
            }
        });
    }
}

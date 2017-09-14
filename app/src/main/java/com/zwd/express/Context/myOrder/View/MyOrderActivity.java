package com.zwd.express.Context.myOrder.View;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwd.express.Context.myOrder.Module.MyOrderGet;
import com.zwd.express.Context.orderDetail.View.OrderDetailActivity;
import com.zwd.express.Context.orderDetail.View.OrderDetail_UnfinishActivity;
import com.zwd.express.Context.orderDetail.View
        .OrderDetail_Unfinish_TakeActivity;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.adapter.MyOrderAdapter;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;

public class MyOrderActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.txt_place)
    TextView txt_place;
    @BindView(R.id.view_place)
    View view_place;
    @BindView(R.id.txt_receive)
    TextView txt_receive;
    @BindView(R.id.view_receive)
    View view_receive;
    @BindView(R.id.img_all)
    ImageView img_all;
    @BindView(R.id.img_unfinish)
    ImageView img_unfinish;
    @BindView(R.id.img_finish)
    ImageView img_finish;
    @BindView(R.id.img_cancle)
    ImageView img_cancle;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private MyOrderAdapter adapter;
    private List<MyOrderGet> myOrderGets = new ArrayList<>();

    private int type = 0,state = 4;
    private Bundle bundle;
    private String Qiniu_token;
    private int id;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Accept_ReturnOrderList.aspx";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected int getContentView() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        Qiniu_token = bundle.getString("Qiniu_token");
        id=bundle.getInt("id");
        initToolBar(toolbar);
        initRefresh();
        url_order();
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
        url_order();
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
    private void url_order(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.orderList(id, baseUrl, url, new CustomCallBack<RemoteDataResult<List<MyOrderGet>>>() {

            @Override
            public void onSuccess(Response<RemoteDataResult<List<MyOrderGet>>> response) {
                myOrderGets.clear();
                myOrderGets = response.body().getData();
                initFilter(type,state);
            }

            @Override
            public void onFail(String message) {
                Log.d("------message--",message);
            }
        });
    }

    private void initFilter(int type,int state){
        List<MyOrderGet> myOrderGets_type = new ArrayList<>();
        List<MyOrderGet> myOrderGets_state = new ArrayList<>();
        for(int i=0;i<myOrderGets.size();i++){
            if(myOrderGets.get(i).getType()==type){
                myOrderGets_type.add(myOrderGets.get(i));
            }
        }
        if(state==4){
            myOrderGets_state = myOrderGets_type;
        }else {
            for(int i=0;i<myOrderGets_type.size();i++){
                if(state ==1){
                    if(myOrderGets_type.get(i).getState()==state||
                            myOrderGets_type.get(i).getState()==0){
                        myOrderGets_state.add(myOrderGets_type.get(i));
                    }
                }else if(myOrderGets_type.get(i).getState()==state){
                    myOrderGets_state.add(myOrderGets_type.get(i));
                }
            }
        }
        adapter = new MyOrderAdapter(this,myOrderGets_state);
        recycler.setAdapter(adapter);
        final List<MyOrderGet> finalMyOrderGets_state = myOrderGets_state;
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, ViewHolder holder, int
                    position) {
                Bundle bundle = new Bundle();
                if(finalMyOrderGets_state.get(position).getState()==1){
                    if(finalMyOrderGets_state.get(position).getType()==1){
                        bundle = new Bundle();
                        bundle.putInt("orderId",finalMyOrderGets_state.get(position).getId());
                        bundle.putInt("stu",finalMyOrderGets_state.get(position).getStu());
                        bundle.putInt("type", finalMyOrderGets_state.get(position).getType());
                        bundle.putString("Qiniu_token",Qiniu_token);
                        goActivity(OrderDetail_Unfinish_TakeActivity.class,bundle);
                    }else if (finalMyOrderGets_state.get(position).getType()==0){
                        bundle.putInt("orderId",finalMyOrderGets_state.get(position).getId());
                        bundle.putInt("stu",finalMyOrderGets_state.get(position).getStu());
                        bundle.putInt("type", finalMyOrderGets_state.get(position).getType());
                        goActivity(OrderDetail_UnfinishActivity.class,bundle);
                    }

                }else {
                    bundle.putInt("type", finalMyOrderGets_state.get(position).getType());
                    bundle.putInt("state", finalMyOrderGets_state.get(position).getState());
                    bundle.putInt("id",id);
                    bundle.putInt("orderId",finalMyOrderGets_state.get(position).getId());
                    bundle.putInt("stu",finalMyOrderGets_state.get(position).getStu());
                    goActivity(OrderDetailActivity.class,bundle);
                }

            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });

    }



    private void initToolBar(NormalToolBar toolbar) {
        toolbar.setTitle("我的订单");
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.hideRight();
        toolbar.setBackground(getResources().getDrawable(R.mipmap.bg_nomal_tool));
    }



    @OnClick({R.id.ll_place, R.id.ll_receive, R.id.ll_all, R.id.ll_unfinish,
            R.id.ll_finish, R.id.ll_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_place:
                txt_place.setTextColor(getResources().getColor(R.color.black));
                view_place.setVisibility(View.VISIBLE);
                txt_receive.setTextColor(getResources().getColor(R.color.gray_05));
                view_receive.setVisibility(View.INVISIBLE);
                type = 0;
                initFilter(type,state);
                break;
            case R.id.ll_receive:
                txt_place.setTextColor(getResources().getColor(R.color.gray_05));
                view_place.setVisibility(View.INVISIBLE);
                txt_receive.setTextColor(getResources().getColor(R.color.black));
                view_receive.setVisibility(View.VISIBLE);
                type = 1;
                initFilter(type,state);
                break;
            case R.id.ll_all:
                img_all.setVisibility(View.VISIBLE);img_unfinish.setVisibility(View.INVISIBLE);
                img_finish.setVisibility(View.INVISIBLE);img_cancle.setVisibility(View.INVISIBLE);
                state = 4;
                initFilter(type,state);
                break;
            case R.id.ll_unfinish:
                img_all.setVisibility(View.INVISIBLE);img_unfinish.setVisibility(View.VISIBLE);
                img_finish.setVisibility(View.INVISIBLE);img_cancle.setVisibility(View.INVISIBLE);
                state=1;
                initFilter(type,state);
                break;
            case R.id.ll_finish:
                img_all.setVisibility(View.INVISIBLE);img_unfinish.setVisibility(View.INVISIBLE);
                img_finish.setVisibility(View.VISIBLE);img_cancle.setVisibility(View.INVISIBLE);
                state=2;
                initFilter(type,state);
                break;
            case R.id.ll_cancle:
                img_all.setVisibility(View.INVISIBLE);img_unfinish.setVisibility(View.INVISIBLE);
                img_finish.setVisibility(View.INVISIBLE);img_cancle.setVisibility(View.VISIBLE);
                state=3;
                initFilter(type,state);
                break;
        }
    }
}

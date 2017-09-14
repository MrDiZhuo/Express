package com.zwd.express.Context.liveTake.View;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwd.express.Context.liveTake.Module.LiveTakeGet;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.adapter.LiveTakeAdapter;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.base.BaseAppManager;
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
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import retrofit2.Response;

public class LiveTakeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.txt_num)
    TextView txt_num;
    @BindView(R.id.money_all)
    TextView txt_moneyAll;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private LiveTakeAdapter adapter;
    private List<LiveTakeGet> liveTakeGets = new ArrayList<>();
    private List<Boolean> flag_choose = new ArrayList<>();
    private int num = 0;
    private double moneyAll = 0.00;
    private int liveId;
    private String livename;
    private Bundle bundle;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private String baseUrl="http://139.224.164.183:8008/";
    private String url = "Playing_ReturnPlayingAcceptForPlaying.aspx";
    private String url_take = "Playing_PlayingAcceptForErrand.aspx";
    List<String> targetId = new ArrayList<>();
    List<Integer> orderId = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_live_take;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        liveId = bundle.getInt("liveId");
        livename = bundle.getString("livename");
        initActionBar(toolbar);
        initRefresh();
        Url_takeList();
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
        Url_takeList();
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

    private void Url_takeList(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.liveTakeList(liveId, baseUrl, url, new CustomCallBack<RemoteDataResult<List<LiveTakeGet>>>() {

            @Override
            public void onSuccess(Response<RemoteDataResult<List<LiveTakeGet>>> response) {
                liveTakeGets.clear();
                liveTakeGets = response.body().getData();
                if (liveTakeGets.size()>0){
                    for(int i=0;i<liveTakeGets.size();i++){
                        flag_choose.add(false);
                    }
                    Log.d("---size",liveTakeGets.size()+"");
                    initRecycler();
                }

            }

            @Override
            public void onFail(String message) {

            }
        });
    }


    private void initRecycler() {
        adapter = new LiveTakeAdapter(this, liveTakeGets);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter
                .OnItemClickListener() {

            @Override
            public void onItemClick(View view, ViewHolder holder, int
                    position) {
                ImageView choose = (ImageView) holder
                        .getConvertView().findViewById(R.id
                                .choose);
                if (flag_choose.get(position)) {
                    flag_choose.set(position, false);
                    choose.setImageResource(R.mipmap.order_no);
                    num--;
                    moneyAll -= Double.valueOf(liveTakeGets.get(position)
                            .getMoney());
                    txt_num.setText("共" + num + "单");
                    txt_moneyAll.setText("¥"+moneyAll);
                    targetId.remove(String.valueOf(liveTakeGets.get(position).getUserid()));
                } else {
                    flag_choose.set(position, true);
                    choose.setImageResource(R.mipmap.order_yes);
                    num++;
                    moneyAll += Double.valueOf(liveTakeGets.get(position)
                            .getMoney());
                    txt_num.setText("共" + num + "单");
                    txt_moneyAll.setText("¥"+moneyAll);
                    targetId.add(String.valueOf(liveTakeGets.get(position).getUserid()));
                }
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int
                    position) {
                return false;
            }
        });
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setTitle("接单表");
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.hideRight();
        toolbar.setBack(R.mipmap.bg_nomal_tool);
    }

    @OnClick({R.id.yes})
    public void click(View v){
        switch (v.getId()){
            case R.id.yes:
                Url_Take();
                break;
        }

    }

    ///接单
    private void Url_Take(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.liveTake(orderId, baseUrl, url_take, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                ////////创建讨论组
                RongIM.getInstance().createDiscussionChat(LiveTakeActivity.this, targetId, livename, new RongIMClient.CreateDiscussionCallback() {
                    @Override
                    public void onSuccess(String s) {
                        ////s是讨论组id

                    }
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }

            @Override
            public void onFail(String message) {

            }
        });
    }



}

package com.zwd.express.Context.roast.View;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zwd.express.Context.roast.Module.ReplyGet;
import com.zwd.express.Context.roastDetail.View.CommentActivity;
import com.zwd.express.Context.roastDetail.View.RoastDetailActivity;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.adapter.ReplyAdapter;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class ReplyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private List<ReplyGet> replyGets = new ArrayList<>();
    private Bundle bundle;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Chat_ReturnReplyList.aspx";
    private int id;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected int getContentView() {
        return R.layout.activity_reply;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        initActionBar(toolbar);
        initRefresh();
        Url_Reply();
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
        Url_Reply();
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


    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.hideRight();
        toolbar.setTitle("回复我的");
        toolbar.setBack(R.mipmap.bg_nomal_tool);
    }

    private void initRecycler() {
        final ReplyAdapter adapter = new ReplyAdapter(this,replyGets);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, ViewHolder holder, int
                    position) {
                bundle = new Bundle();
                bundle.putString("context",replyGets.get(position).getContext());
                bundle.putString("name",replyGets.get(position).getName());
                bundle.putInt("userid",id);
                bundle.putInt("chatid",replyGets.get(position).getTragetid());
                if(replyGets.get(position).getType()==0){
                    goActivity(RoastDetailActivity.class,bundle);
                }else {
                    goActivity(CommentActivity.class,bundle);
                }
                replyGets.get(position).setFlag_look(1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
    }


    private void Url_Reply(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.myReplyList(id, baseUrl, url, new CustomCallBack<RemoteDataResult<List<ReplyGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<ReplyGet>>>
                                          response) {
                replyGets.clear();
                replyGets = response.body().getData();

                if (replyGets!=null&&replyGets.size()>0){
                    for(int i=0;i<replyGets.size();i++){

                        Log.d("---success",replyGets.get(i).getContext());
                       // replyGets.get(i).setFlag_look(0);
                    }
                }
                initRecycler();
            }

            @Override
            public void onFail(String message) {
                Log.d("------message--",message);
            }
        });

    }

}

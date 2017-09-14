package com.zwd.express.Context.roast.View;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwd.express.Context.liveList.View.LivelistActivity;
import com.zwd.express.Context.liveRoom.View.LiveRoom_PlaceActivity;
import com.zwd.express.Context.roast.Module.IfReturnGet;
import com.zwd.express.Context.roast.Module.ProvePost;
import com.zwd.express.Context.roast.Module.RoastListGet;
import com.zwd.express.Context.roastDetail.View.RoastDetailActivity;
import com.zwd.express.R;
import com.zwd.express.Widget02.popupWindow.PopupWindow;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.adapter.RoastAdapter;
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

public class RoastActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private List<RoastListGet> roastListGets =new ArrayList<>();
    private RoastAdapter adapter;

    private PopupWindow popupWindow;
    private View view;
    private Bundle bundle;
    public int id;
    private String Qiniu_token;

    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Chat_ReturnChatList.aspx";
    private String url_retrun = "Chat_ReturnReply.aspx";


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected int getContentView() {
        return R.layout.activity_roast;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        Qiniu_token = bundle.getString("Qiniu_token");
        initActionBar(toolbar);
        initRefresh();
        Url_roastlist();
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
        Url_roastlist();
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



    private void initPopupWindow() {
        view = LayoutInflater.from(this).inflate(R.layout.pop_roast, null);
        popupWindow = new PopupWindow(this);
        final ImageView img = (ImageView)view.findViewById(R.id.img);
        Url_ifReturn(img);
        TextView publish = (TextView)view.findViewById(R.id.publish);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putInt("id",id);
                bundle.putString("Qiniu_token",Qiniu_token);
                goActivity(PublishRoastActivity.class,bundle);
                popupWindow.dismiss();
            }
        });
        LinearLayout reply = (LinearLayout)view.findViewById(R.id.reply);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putInt("id",id);
                goActivity(ReplyActivity.class,bundle);
                popupWindow.dismiss();
            }
        });
        popupWindow.setAnimationStyle(R.style.circlepop_anim_style);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);//一定要设置
        popupWindow.setContentView(view);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);

    }

    public void Url_roastlist(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.roastList(id, baseUrl, url, new CustomCallBack<RemoteDataResult<List<RoastListGet>>>() {

            @Override
            public void onSuccess(Response<RemoteDataResult<List<RoastListGet>>>
                                          response) {
                roastListGets.clear();
                roastListGets = response.body().getData();
                initRecycler();
            }

            @Override
            public void onFail(String message) {
                Log.d("------message--",message);
            }
        });
    }

    private void initRecycler() {
        NoScrollLinearLayoutManager linearLayoutManager = new
                NoScrollLinearLayoutManager(this);
        linearLayoutManager.setScrollEnabled(false);
        recycler.setLayoutManager(linearLayoutManager);
        adapter = new RoastAdapter(this, roastListGets);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, ViewHolder holder, int
                    position) {
                bundle = new Bundle();
                bundle.putString("object","");
                bundle.putString("name","");
                bundle.putInt("userid",id);
                bundle.putInt("chatid",roastListGets.get(position).getId());
                goActivity(RoastDetailActivity.class,bundle);
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void initActionBar(final NormalToolBar toolbar) {
        toolbar.setBack(R.mipmap.bg_nomal_tool);
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setRightImg(R.mipmap.icon_roast);
        toolbar.setTitle("吐槽区");
        toolbar.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("---------", "show");
                int[] location = new int[2];
                toolbar.getLocationOnScreen(location);
                initPopupWindow();
                popupWindow.showAtLocation(toolbar, Gravity.NO_GRAVITY,
                        (int) (location[0] + toolbar.getWidth() * 0.65)
                        , (int) (location[1] + toolbar.getHeight() * 0.55));
            }
        });
    }

    @OnClick({R.id.livelist,R.id.live_01})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.livelist:
                bundle = new Bundle();
                bundle.putInt("id",id);
                goActivity(LivelistActivity.class,bundle);
                break;
            case R.id.live_01:
                goActivity(LiveRoom_PlaceActivity.class);
                break;
        }

    }


    class NoScrollLinearLayoutManager extends LinearLayoutManager {
        private boolean isScrollEnabled = false;

        public NoScrollLinearLayoutManager(Context context) {
            super(context);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            //Similarly you can customize "canScrollHorizontally()" for
            // managing horizontal scroll
            return isScrollEnabled && super.canScrollVertically();
        }
    }

    ///是否有人回复
    private void Url_ifReturn(final ImageView img){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.ifReturn(id, baseUrl, url_retrun, new CustomCallBack<RemoteDataResult<IfReturnGet>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<IfReturnGet>> response) {
                IfReturnGet get = response.body().getData();
                if(get.getTatus()==0){
                    img.setVisibility(View.INVISIBLE);
                }else {
                    img.setVisibility(View.VISIBLE);
                }
                Log.d("---success",get.getTatus()+"");
            }
            @Override
            public void onFail(String message) {
                Log.d("---fail",message);
            }
        });
    }


}

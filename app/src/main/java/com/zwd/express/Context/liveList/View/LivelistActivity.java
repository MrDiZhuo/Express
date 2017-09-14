package com.zwd.express.Context.liveList.View;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zwd.express.Context.homePage.Module.ReturnFiveGet;
import com.zwd.express.Context.homePage.View.HomePageActivity;
import com.zwd.express.Context.liveList.Module.CreateRoomGet;
import com.zwd.express.Context.liveList.Module.CreateRoomToServerGet;
import com.zwd.express.Context.liveList.Module.CreateRoomToServerPost;
import com.zwd.express.Context.liveList.Module.LiveListGet;
import com.zwd.express.Context.liveList.Module.RoomPublishGet;
import com.zwd.express.Context.liveRoom.View.LiveRoom_PlaceActivity;
import com.zwd.express.Context.liveRoom.View.LiveRoom_TakeActivity;
import com.zwd.express.R;
import com.zwd.express.Widget02.live.LiveKit;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.adapter.LiveListAdapter;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LivelistActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private AlertDialog dialog_1,dialog_2;
    private AlertDialog.Builder builder;

    private LiveListAdapter adapter ;
    private List<ReturnFiveGet> returnFiveGetList = new ArrayList<>();
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Playing_AddPlaying.aspx";
    private String url_list = "Playing_ReturnPlayingList.aspx";
    private String baseUrl_createRoom = "http://139.224.164.183:8009/";
    private String url_createRoom = "CreatStream.aspx";
    private String url_roomPublish = "RTMPpublishURL.aspx";
    private String url_roomPlay = "Play.aspx";
    private String roomId = null;
    private String publishUrl,playUrl;
    private int liveId;
    private Bundle bundle;
    private int id;
    private boolean flag_create=false;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_livelist;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        initAction(toolbar);
        initRefresh();
        Url_playList();
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
        Url_playList();
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


    private void initAction(NormalToolBar toolbar) {
        toolbar.setTitle("直播");
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setBack(R.mipmap.bg_nomal_tool);
        toolbar.setRightImg(R.mipmap.icon_add);
        toolbar.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                builder = new AlertDialog.Builder(v.getContext());
                View view = LayoutInflater.from(v.getContext())
                        .inflate(R.layout.dialog_live_create, null);
                TextView yes = (TextView) view.findViewById(R.id.yes);
                final EditText edit_name = (EditText)view.findViewById(R.id.edit_name);
                final EditText edit_description = (EditText)view.findViewById(R.id.edit_description);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edit_name.getText().toString().length()>0&&edit_description.getText().toString().length()>0){
                            url_createToServer(edit_name,edit_description);
                        }else {
                            ToastUtil.showShort(v.getContext(),"请填写完整");
                        }
                    }
                });
                builder.setView(view);
                dialog_1 = builder.create();
                dialog_1.show();
            }
        });
    }
    ///直播列表
    private void Url_playList(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.returnPlayingList(baseUrl, url_list, new CustomCallBack<RemoteDataResult<List<ReturnFiveGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<ReturnFiveGet>>> response) {
                returnFiveGetList.clear();
                returnFiveGetList = response.body().getData();
                Log.d("----size",returnFiveGetList.size()+"");
                if (returnFiveGetList.size()>0){
                    for(int i=0;i<returnFiveGetList.size();i++){
                        returnFiveGetList.get(i).setTYPE(1);
                    }
                    ReturnFiveGet get = new ReturnFiveGet();
                    get.setTYPE(2);
                    returnFiveGetList.add(1,get);
                }
                initRecycler();
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void initRecycler() {
        recycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter = new LiveListAdapter(this,returnFiveGetList);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, ViewHolder holder, int
                    position) {
                if(returnFiveGetList.get(position).getTYPE()==1){
                    bundle = new Bundle();
                    bundle.putString("rongid",returnFiveGetList.get(position).getRongid());
                    bundle.putString("playUrl",returnFiveGetList.get(position).getTokenurl());
                    bundle.putInt("id",id);
                    bundle.putInt("roomId",returnFiveGetList.get(position).getId());
                    bundle.putString("playingname",returnFiveGetList.get(position).getPlayingname());
                    goActivity(LiveRoom_PlaceActivity.class,bundle);
                }

            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
    }

    ////直播推流
    private void Url_roomPublish(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.roomPublish(roomId, baseUrl_createRoom, url_roomPublish, new Callback<RoomPublishGet>() {

            @Override
            public void onResponse(Call<RoomPublishGet> call,
                                   Response<RoomPublishGet> response) {
                RoomPublishGet get = response.body();
                publishUrl = get.getPublishUrl();
                if (publishUrl!=null&&publishUrl.length()>0){
                    flag_create = true;
                }else {
                    flag_create = false;
                }
            }

            @Override
            public void onFailure(Call<RoomPublishGet> call, Throwable t) {
                flag_create = false;
            }
        });
    }
    ////直播拉流
    private void Url_roomPlay(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.roomPlay(roomId, baseUrl_createRoom, url_roomPlay, new Callback<RoomPublishGet>() {

            @Override
            public void onResponse(Call<RoomPublishGet> call,
                                   Response<RoomPublishGet> response) {
                RoomPublishGet get = response.body();
                playUrl = get.getPublishUrl();
                Log.d("----playUrl",playUrl);
                if (playUrl!=null&&playUrl.length()>0){
                    flag_create = true;
                }else {
                    flag_create = false;
                }
            }

            @Override
            public void onFailure(Call<RoomPublishGet> call, Throwable t) {
                flag_create = false;
            }
        });
    }
    /////创建房间 拿到房间id
    private void Url_createRoom(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.createRoom(baseUrl_createRoom, url_createRoom, new Callback<CreateRoomGet>() {

            @Override
            public void onResponse(Call<CreateRoomGet> call,
                                   Response<CreateRoomGet> response) {
                CreateRoomGet get = response.body();
                roomId = get.getRoomId();
                if(roomId!=null&&roomId.length()>0){
                    Url_roomPublish();
                    Url_roomPlay();
                }else {
                    flag_create = false;
                }

            }
            @Override
            public void onFailure(Call<CreateRoomGet> call, Throwable t) {
                flag_create = false;
            }
        });
    }
    private void dialog_fail(){
        builder = new AlertDialog.Builder(BaseAppManager.getInstance().getForwardActivity());
        View view1 = LayoutInflater.from(BaseAppManager.getInstance().getForwardActivity()).inflate(R.layout
                .dialog_back, null);
        TextView no = (TextView) view1.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_2.dismiss();
            }
        });
        TextView txt = (TextView)view1.findViewById(R.id.txt) ;
        txt.setText("创建失败!");
        builder.setView(view1);
        dialog_2 = builder.create();
        dialog_2.show();
    }

    ////给server
    private void url_createToServer(final EditText edit_name, EditText edit_introduce){
        Url_createRoom();
        if (flag_create){
            CreateRoomToServerPost post = new CreateRoomToServerPost(id,publishUrl
                    ,playUrl,roomId,edit_name.getText().toString(),edit_introduce.getText().toString());
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.createRoomToServer(post, baseUrl, url, new CustomCallBack<RemoteDataResult<CreateRoomToServerGet>>() {
                @Override
                public void onSuccess(Response<RemoteDataResult<CreateRoomToServerGet>> response) {
                    CreateRoomToServerGet get = response.body().getData();
                    liveId = get.getId();
                    bundle  = new Bundle();
                        bundle.putString("publishUrl",publishUrl);
                        bundle.putInt("id",id);
                        bundle.putInt("liveId",liveId);
                        bundle.putString("roomId",roomId);
                    bundle.putString("livename",edit_name.getText().toString());
                        Log.d("----roomid",roomId+":::"+publishUrl+":::"+playUrl+"::"+liveId);
                        goActivity(LiveRoom_TakeActivity.class,bundle);
                        dialog_1.dismiss();
                }
                @Override
                public void onFail(String message) {
                    dialog_fail();
                }
            });
        }else {
            dialog_fail();
        }
    }


}

package com.zwd.express.Context.liveRoom.View;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zwd.express.Context.homePage.View.HomePageActivity;
import com.zwd.express.Context.liveRoom.Module.JoinLivePost;
import com.zwd.express.Context.liveRoom.Module.LiveOrderPost;
import com.zwd.express.Context.myWallet.Module.ChitGet;
import com.zwd.express.Context.myWallet.View.MyWalletActivity;
import com.zwd.express.R;
import com.zwd.express.Widget02.live.ChatListView;
import com.zwd.express.Widget02.live.LiveKit;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.adapter.ChatListAdapter;
import com.zwd.express.adapter.ChitAdapter;
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
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.MessageContent;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.TextMessage;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import retrofit2.Response;

public class LiveRoom_PlaceActivity extends BaseActivity implements Handler
        .Callback {

    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.chat_listview)
    ChatListView chat_listview;
    @BindView(R.id.edit)
    EditText edit;

    private AlertDialog dialog,dialog_2,dialog_3,dialog_4;
    private AlertDialog.Builder builder;

    private Handler handler = new Handler(this);
    private ChatListAdapter chatListAdapter;

    private String chatRoomId;
    private String playUrl;
    private int id;
    private int liveId;
    private String playingname;
    private Bundle bundle;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url="Playing_AddOnLine.aspx";
    private String url_out = "Playing_LeaveOnLine.aspx";
    private String url_order = "Playing_AddPlayingAccept.aspx";
    private String url_chit = "Wallet_ReturnCouponList.aspx";
    private boolean flag_out=false;
    private String addr,name,phone,introduce;
    private double acceptmoney,reward,couponpay,realpayDecimal;
    private int couponid=1;//代金券id
    private ChitAdapter chitAdapter;
    private List<ChitGet> chitGets = new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.activity_live_room__place;
    }

    @Override
    protected void initViews() {
        if(!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;
        bundle = getIntent().getExtras();
        chatRoomId = bundle.getString("rongid");
        playUrl = bundle.getString("playUrl");
        id = bundle.getInt("id");
        liveId = bundle.getInt("roomId");
        playingname = bundle.getString("playingname");
        Url_joinLive();
        initActionBar(toolbar);
        initVideo();
        initChat();
        Url_Chit();

    }

    private void initChat() {
        LiveKit.init(this);
        LiveKit.addEventHandler(handler);
        chatListAdapter = new ChatListAdapter();
        chat_listview.setAdapter(chatListAdapter);
        joinChatRoom(chatRoomId);

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
                chitAdapter = new ChitAdapter(LiveRoom_PlaceActivity.this, chitGets);
            }

            @Override
            public void onFail(String message) {

            }
        });
    }
    ///加入房间
    private void Url_joinLive(){
        JoinLivePost post = new JoinLivePost(id,liveId);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.joinLive(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {

            }
            @Override
            public void onFail(String message) {

            }
        });
    }
    ///退出房间
    private boolean Url_outLive(){
        JoinLivePost post = new JoinLivePost(id,liveId);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.joinLive(post, baseUrl, url_out, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                LiveKit.quitChatRoom(new RongIMClient.OperationCallback() {
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {}
                });
                flag_out = true;
            }
            @Override
            public void onFail(String message) {
                flag_out = false;
            }
        });
        return flag_out;
    }
    private void joinChatRoom(final String roomId) {
        LiveKit.joinChatRoom(roomId, 4, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                final InformationNotificationMessage content = InformationNotificationMessage.obtain("来啦");

                LiveKit.sendMessage(content);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Toast.makeText(LiveRoom_PlaceActivity.this, "聊天室加入失败! errorCode = " + errorCode, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case LiveKit.MESSAGE_ARRIVED: {
                MessageContent content = (MessageContent) msg.obj;
                chatListAdapter.addMessage(content);
                break;
            }
            case LiveKit.MESSAGE_SENT: {
                MessageContent content = (MessageContent) msg.obj;
                chatListAdapter.addMessage(content);
                break;
            }
            case LiveKit.MESSAGE_SEND_ERROR: {
                break;
            }
            default:
        }
        chatListAdapter.notifyDataSetChanged();
        return false;
    }
    @OnClick({R.id.btn_send,R.id.btn})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.btn:
                builder = new AlertDialog.Builder(LiveRoom_PlaceActivity.this);
                View view = LayoutInflater.from(LiveRoom_PlaceActivity.this)
                        .inflate(R.layout.dialog_live, null);
                TextView btn = (TextView) view.findViewById(R.id.btn);
                final EditText edit_addr = (EditText)view.findViewById(R.id.edit_addr);
                final EditText edit_name = (EditText)view.findViewById(R.id.edit_name);
                final EditText edit_phone = (EditText)view.findViewById(R.id.edit_phone);
                final EditText edit_money = (EditText)view.findViewById(R.id.edit_money);
                final EditText edit_thing = (EditText)view.findViewById(R.id.edit_thing);
                final EditText edit_give = (EditText)view.findViewById(R.id.edit_give);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edit_addr.getText().toString().length()>0&&edit_name.getText().toString().length()>0
                                &&edit_phone.getText().toString().length()>0&&edit_money.getText().toString().length()>0
                                &&edit_thing.getText().toString().length()>0&&edit_give.getText().toString().length()>0){
                            addr = edit_addr.getText().toString();
                            name = edit_name.getText().toString();
                            phone =edit_phone.getText().toString();
                            acceptmoney = Double.valueOf(edit_money.getText().toString());
                            reward = Double.valueOf(edit_give.getText().toString());
                            introduce= edit_thing.getText().toString();

                            dialog_2 = new AlertDialog.Builder(LiveRoom_PlaceActivity.this).create();
                            dialog_2.show();
                            Window window = dialog_2.getWindow();
                            window.setGravity(Gravity.BOTTOM);
                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                            window.setWindowAnimations(R.style.dialogStyle);
                            View view02 = LayoutInflater.from(LiveRoom_PlaceActivity.this)
                                    .inflate(R.layout.dialog_place, null);
                            window.setContentView(view02);
                            final TextView txt_realpay = (TextView)view02.findViewById(R.id.realpay);
                            txt_realpay.setText("¥"+(acceptmoney+reward-couponpay));
                            TextView needmoney = (TextView)view02.findViewById(R.id.needmoney);
                            needmoney.setText("¥"+(acceptmoney+reward));

                            final TextView chit_num = (TextView) view02.findViewById(R.id
                                    .chit_num);
                            RelativeLayout chit = (RelativeLayout) view02.findViewById(R.id
                                    .chit);
                            TextView yes = (TextView) view02.findViewById(R.id.yes);
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog_2.dismiss();
                                    Url_order();
                                }
                            });
                            chit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    builder = new AlertDialog.Builder(view.getContext());
                                    View view3 = LayoutInflater.from(view.getContext())
                                            .inflate(R.layout.dialog_chit, null);
                                    RecyclerView rv = (RecyclerView) view3.findViewById(R
                                            .id.recycler);
                                    chitAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {

                                        @Override
                                        public void onItemClick(View view, ViewHolder holder, int position) {
                                            if (chitGets.get(position).getMark()==1000) {
                                                chit_num.setText("¥10");
                                                couponpay = 10;

                                            } else if (chitGets.get(position).getMark()==500) {
                                                chit_num.setText("¥5");
                                                couponpay = 5;
                                            } else {
                                                chit_num.setText("¥2");
                                                couponpay = 2;
                                            }
                                            if ((acceptmoney+reward)>=couponpay){
                                                txt_realpay.setText("¥"+(acceptmoney+reward-couponpay));
                                                dialog_4.dismiss();
                                            }else {
                                                couponid=1;couponpay=0;
                                                ToastUtil.showShort(view.getContext(),"无法使用!");
                                            }
                                        }

                                        @Override
                                        public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                                            return false;
                                        }
                                    });
                                    rv.setAdapter(chitAdapter);

                                    builder.setView(view3);
                                    dialog_4 = builder.create();
                                    dialog_4.show();
                                }
                            });

                        }else {
                            ToastUtil.showShort(LiveRoom_PlaceActivity.this,"请填写完整");
                        }

                    }
                });

                builder.setView(view);
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.btn_send:
                if(edit.getText().toString().length()>0){
                    TextMessage textMsg = TextMessage.obtain(edit.getText().toString());
                    Log.d("msg",textMsg.getContent());
                    LiveKit.sendMessage(textMsg);
                    edit.setText("");
                }
                break;
        }


    }

    ///下单
    private void Url_order(){
        realpayDecimal = acceptmoney+reward-couponpay;
        LiveOrderPost post = new LiveOrderPost(id,liveId,addr,name,phone
                ,acceptmoney,reward,introduce,couponid,couponpay,realpayDecimal);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.liveOrder(post, baseUrl, url_order, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                Log.d("----fail",response.body().getData()+"");
                builder = new AlertDialog.Builder(LiveRoom_PlaceActivity.this);
                View view1 = LayoutInflater.from(LiveRoom_PlaceActivity.this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        dialog_3.dismiss();


                    }
                });
                TextView txt = (TextView)view1.findViewById(R.id.txt) ;
                txt.setText("下单成功!");
                builder.setView(view1);
                dialog_3 = builder.create();
                dialog_3.show();
            }
            @Override
            public void onFail(String message) {
                builder = new AlertDialog.Builder(LiveRoom_PlaceActivity.this);
                View view1 = LayoutInflater.from(LiveRoom_PlaceActivity.this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog_3.dismiss();

                    }
                });
                TextView txt = (TextView)view1.findViewById(R.id.txt) ;
                txt.setText("下单失败!");
                builder.setView(view1);
                dialog_3 = builder.create();
                dialog_3.show();
            }
        });
    }
    private void initVideo() {
        videoView.setVideoURI(Uri.parse(playUrl));
        videoView.start();
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Url_outLive()) {
                    finish();
                } else {
                    dialog_fail();
                }
            }
        });
        toolbar.setTitle(playingname);
        toolbar.hideRight();
    }
    private void dialog_fail() {
        builder = new AlertDialog.Builder(BaseAppManager.getInstance()
                .getForwardActivity());
        View view1 = LayoutInflater.from(BaseAppManager.getInstance()
                .getForwardActivity()).inflate(R.layout
                .dialog_back, null);
        TextView no = (TextView) view1.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_2.dismiss();
            }
        });
        TextView txt = (TextView) view1.findViewById(R.id.txt);
        txt.setText("直播间退出失败!");
        builder.setView(view1);
        dialog_2 = builder.create();
        dialog_2.show();
    }

    /**
     * 点击键盘以外的区域隐藏键盘
     *
     * @param ev
     * @return
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShoudHideKeyBoard(v, ev)) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShoudHideKeyBoard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right
                    = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() <
                    bottom && event.getY() > top) {
                return false;
            } else {
                return true;
            }
        }
        return false;

    }
}

package com.zwd.express.Context.homePage.View;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bumptech.glide.Glide;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.zwd.express.Context.Locationservice.Controller.MyLocationService;
import com.zwd.express.Context.homePage.Module.DialogOrderPost;
import com.zwd.express.Context.homePage.Module.PlacePost;
import com.zwd.express.Context.homePage.Module.ReturnFiveGet;
import com.zwd.express.Context.homePage.Module.DialogGet;
import com.zwd.express.Context.homePage.Module.WavePost;
import com.zwd.express.Context.liveRoom.View.LiveRoom_PlaceActivity;
import com.zwd.express.Context.myWallet.Module.ChitGet;
import com.zwd.express.Context.roast.View.RoastActivity;
import com.zwd.express.R;
import com.zwd.express.Widget02.banner.BannerViewPager;
import com.zwd.express.Widget02.banner.OnPageClickListener;
import com.zwd.express.Widget02.banner.ViewPagerAdapter;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.Widget02.timePicker.WheelView;
import com.zwd.express.Widget02.xiuxiu.RippleSpreadView;
import com.zwd.express.adapter.ChitAdapter;
import com.zwd.express.adapter.HomepageAdpater;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.base.BaseAppManager;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.HomeToolBar;
import com.zwd.express.util.PxUtil;
import com.zwd.express.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;
import retrofit2.Response;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

public class HomePageActivity extends BaseActivity implements
        AMapLocationListener,
        LocationSource, AMap.OnCameraChangeListener, GeocodeSearch
        .OnGeocodeSearchListener {
    @BindView(R.id.homepage_map)
    MapView homepageMap;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    HomeToolBar homeToolBar;

    @BindView(R.id.centerimg_location)
    ImageView centerimg_location;

    @BindView(R.id.locate)
    ImageView btn_locate;
    @BindView(R.id.banner)
    BannerViewPager banner;
    @BindView(R.id.viewpager)
    RelativeLayout viewpager;
    @BindView(R.id.ll_bottom)
    View ll_bottom;
    @BindView(R.id.ll_part)
    View ll_part;
    @BindView(R.id.btn_order)
    LinearLayout btn_order;
    @BindView(R.id.order_out)
    ImageView order_out;
    @BindView(R.id.take_img)
    ImageView take_img;
    @BindView(R.id.buy_img)
    ImageView buy_img;
    @BindView(R.id.buy_content)
    LinearLayout buy_content;
    @BindView(R.id.take_content)
    LinearLayout take_content;
    @BindView(R.id.order_set)
    TextView order_set;
    @BindView(R.id.order_take)
    TextView order_take;
    @BindView(R.id.ll_bottom_take)
    LinearLayout ll_bottom_take;
    @BindView(R.id.ripperView)
    RippleSpreadView ripperView;
    @BindView(R.id.txt_location)
    TextView txt_location;
    @BindView(R.id.txt_destin)
    TextView txt_destination;
    @BindView(R.id.txt_reachtime)
    TextView txt_reachtime;
    @BindView(R.id.txt_take)
    TextView txt_take;
    @BindView(R.id.money_thing)
    EditText moneyThing;
    @BindView(R.id.reward)
    EditText reward;
    @BindView(R.id.money_all)
    TextView moneyAll;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.take_description)
    EditText take_description;
    @BindView(R.id.buy_description)
    EditText buy_description;

    private double money;

    private long exitTime = 0;

    private String Qiniu_token="";
    private String heading;

    private String cityAddr = "";
    private AMap aMap;
    private OnLocationChangedListener onLocationChangedListener;
    private AMapLocationClientOption mapLocationClientOption = null;
    private AMapLocationClient aMapLocationClient;
    private GeocodeSearch geocodeSearch;
    private UiSettings mUiSettings;
    private Marker locationMarker;
    private boolean flag_move = false;
    private MyLocationStyle myLocationStyle;
    private ViewPagerAdapter adapter;
    private String addrName;

    private int h, w;
    private ObjectAnimator am_in, am_out;
    private boolean flag_order = false;


    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    WheelView wv01, wv02, wv03, wv04, wv06, wv07, wv08, wv09;
    private List<String> month = new ArrayList<>();
    private List<String> day = new ArrayList<>();
    private List<String> hour = new ArrayList<>();
    private List<String> minute = new ArrayList<>();

    private boolean flag_location = false;
    private boolean flag_destination = false;
    private boolean flag_take = false;

    private HomepageAdpater homepPageAdapter;
    private List<DialogGet> dialogGets = new ArrayList<>();
    private List<Boolean> flag_choose = new ArrayList<>();
    private int num = 0;
    private Bundle bundle;

    private ChitAdapter chitAdapter;
    private List<ChitGet> chitGets = new ArrayList<>();

    public int id;
    private String str_name;
    private String str_username;
    private String sex;
    private int credit;
    private int mark;
    private int userstatus;
    private int type=0;//0拿东西 1买东西
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url_viewpager="Playing_ReturnPlayingForfive.aspx";

    ////viewpager
    private List<ReturnFiveGet> returnFiveGetList = new ArrayList<>();
    ///下单
    private String url_place = "Errand_AddErrand.aspx";
    private double goodsaddressN,goodsaddressE;//物品位置
    private String goodsaddressword="";
    private double errandaddressN,errandaddressE;//送达位置
    private String addressword="";
    private String starttime="";
    private String endtime="";//送达时间
    private  String description="";
    private int couponid=1;//代金券id
    private double couponpay;//代金券金额
    private double realpay;//实付金额

    ///代金券列表
    private String url_chit = "Wallet_ReturnCouponList.aspx";
    private List<Integer> ids = new ArrayList<>();
    private List<String> rongIds = new ArrayList<>();
    //接单
    private String url_waveList = "Accept_ReadyAccept.aspx";
    private String url_waveOrder = "Accept_AcceptForErrand.aspx";
    private double addressN=0.0,addressE=0.0;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String date = sdf.format(new Date());

    @Override
    protected int getContentView() {
        return R.layout.activity_home_page;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        heading = bundle.getString("heading");
        Qiniu_token = bundle.getString("Qiniu_token");
        str_username = bundle.getString("username");
        str_name = bundle.getString("name");
        sex = bundle.getString("sex");
        credit = bundle.getInt("credit");
        mark = bundle.getInt("mark");
        userstatus = bundle.getInt("userstatus");
        ///传值给leftfragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        LeftFragment leftFragment = new LeftFragment();
        bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putInt("userstatus",userstatus);
        bundle.putString("sex",sex);
        bundle.putString("heading",heading);
        bundle.putString("Qiniu_token",Qiniu_token);
        bundle.putString("username",str_username);
        bundle.putString("name",str_name);
        bundle.putInt("credit",credit);
        bundle.putInt("mark",mark);
        leftFragment.setArguments(bundle);
        transaction.add(R.id.menu,leftFragment);
        transaction.commit();

        ///实时上传位置信息
        startService(new Intent(HomePageActivity.this, MyLocationService.class));

        initToolBar(homeToolBar);
        initDrawer();
        initViewPager();
        initBottom();
        initWaveView();

        ///代金券
        Url_Chit();


    }

    private void Url_place(){
            PlacePost post = new PlacePost(id,goodsaddressword,goodsaddressN,goodsaddressE
                    ,addressword,errandaddressN,errandaddressE,starttime,endtime
                    ,name.getText().toString(),phone.getText().toString(),type
                    ,description,Double.valueOf(moneyThing.getText().toString())
                    ,Double.valueOf(reward.getText().toString()),0,couponid,couponpay,realpay);
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.place(post, baseUrl, url_place, new CustomCallBack<RemoteDataResult>() {

                @Override
                public void onSuccess(Response<RemoteDataResult> response) {
                    builder = new AlertDialog.Builder(HomePageActivity.this);
                    View view1 = LayoutInflater.from(HomePageActivity.this).inflate(R.layout
                            .dialog_back, null);
                    TextView no = (TextView) view1.findViewById(R.id.no);
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            closeBottom();
                            dialog.dismiss();

                        }
                    });
                    TextView txt = (TextView)view1.findViewById(R.id.txt) ;
                    txt.setText("下单成功!");
                    builder.setView(view1);
                    dialog = builder.create();
                    dialog.show();
                }

                @Override
                public void onFail(String message) {
                    builder = new AlertDialog.Builder(HomePageActivity.this);
                    View view1 = LayoutInflater.from(HomePageActivity.this).inflate(R.layout
                            .dialog_back, null);
                    TextView no = (TextView) view1.findViewById(R.id.no);
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    TextView txt = (TextView)view1.findViewById(R.id.txt) ;
                    txt.setText("下单失败!");
                    builder.setView(view1);
                    dialog = builder.create();
                    dialog.show();
                    Log.d("---",message);
                }
            });


    }


    private boolean check(){
        if (goodsaddressword.length()!=0&&addressword.length()!=0
                &&starttime.length()!=0&&name.getText().toString().length()!=0
                &&phone.getText().toString().length()!=0 &&reward.getText().toString().length()!=0){
            if (type==0){
                moneyThing.setText("00.00");
                description = take_description.getText().toString();
            }else {
                description=buy_description.getText().toString();
            }
            if(description.length()!=0&&moneyThing.getText().toString().length()!=0){
                return true;
            }else return false;

        }else return false;
    }
    private void initWaveView() {
        ripperView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case ACTION_DOWN:
                        ripperView.startAnim();
                        if(userstatus==0||credit<60){
                            ToastUtil.showShort(view.getContext(),"您无权接单!");
                        }else {
                            if (addressN==0.0&&addressE==0.0){
                                ToastUtil.showShort(view.getContext(),"请填写完整!");
                            }else {
                                Url_waveList();
                            }
                        }
                        break;
                    case ACTION_UP:
                        ripperView.stopOutAnim();
                        break;
                }
                return true;
            }
        });
    }

    ///
    private void initDialog_wave(){
        builder = new AlertDialog.Builder(HomePageActivity
                .this);
        View view1 = LayoutInflater.from(HomePageActivity
                .this).inflate(R.layout.dialog_order, null);
        final RecyclerView recycler = (RecyclerView)
                view1.findViewById(R.id.recycler);
        homepPageAdapter = new HomepageAdpater(HomePageActivity.this, dialogGets);
        recycler.setAdapter(homepPageAdapter);
        TextView take = (TextView) view1.findViewById(R.id
                .take);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ids.size()>0){
                    Url_waveGet(dialog);
                }else {
                    ToastUtil.showShort(HomePageActivity.this,"请选择订单!");
                }
            }
        });
        TextView txt = (TextView) view1.findViewById(R.id
                .close);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        final TextView txt_num = (TextView)
                view1.findViewById(R.id.txt_num);
        homepPageAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder
                    holder, int position) {
                ImageView choose = (ImageView) holder
                        .getConvertView().findViewById(R.id
                                .choose);
                if (flag_choose.get(position)) {
                    flag_choose.set(position, false);
                    choose.setImageResource(R.mipmap.order_no);
                    num--;
                    if(ids.size()>0){
                        for(int i=0;i<ids.size();i++){
                            if(dialogGets.get(position).getId()==ids.get(i)){
                                ids.remove(i);
                            }
                            if(String.valueOf(dialogGets.get(position).getUserid()).equals(rongIds.get(i)));{
                                rongIds.remove(i);
                            }
                        }
                    }
                    txt_num.setText("共" + num + "单");
                } else {
                    flag_choose.set(position, true);
                    choose.setImageResource(R.mipmap.order_yes);
                    num++;
                    ids.add(dialogGets.get(position).getId());
                    rongIds.add(String.valueOf(dialogGets.get(position).getUserid()));
                    txt_num.setText("共" + num + "单");
                }
            }

            @Override
            public boolean onItemLongClick(View view,
                                           ViewHolder holder,
                                           int position) {
                return false;
            }
        });

        builder.setView(view1);
        dialog = builder.create();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialogGets!=null&&dialogGets.size()>0){
                    dialog.show();
                }else {
                    ToastUtil.showShort(HomePageActivity.this,"暂无订单!");
                }
                ripperView.stopOutAnim();
            }
        }, 4000);
    }

    ///扫描接单
    private void Url_waveGet(final Dialog dialog_1){
        DialogOrderPost post = new DialogOrderPost(addressN,addressE,addrName,id,ids);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.waveOrder(post, baseUrl, url_waveOrder, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                builder = new AlertDialog.Builder(HomePageActivity.this);
                View view1 = LayoutInflater.from(HomePageActivity.this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog_1.dismiss();
                        dialog.dismiss();
                    }
                });
                TextView txt = (TextView)view1.findViewById(R.id.txt) ;
                txt.setText("接单成功!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
                TextMessage myTextMessage = TextMessage
                        .obtain("我是您的跑腿人,请保持联系哦!");
                for(int i=0;i<rongIds.size();i++){
                    RongIM.getInstance().sendMessage(Conversation
                                    .ConversationType.PRIVATE, rongIds.get(i),
                            myTextMessage, "push", date,
                            new RongIMClient.SendMessageCallback() {
                                @Override
                                public void onError(Integer integer,
                                                    RongIMClient
                                                            .ErrorCode
                                                            errorCode) {
                                }
                                @Override
                                public void onSuccess(Integer integer) {}
                            });
                }

                ids.clear();
                rongIds.clear();

            }

            @Override
            public void onFail(String message) {
                builder = new AlertDialog.Builder(HomePageActivity.this);
                View view1 = LayoutInflater.from(HomePageActivity.this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                TextView txt = (TextView)view1.findViewById(R.id.txt) ;
                txt.setText("接单失败!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
                Log.d("---",message);
            }
        });
    }
    ///扫描订单
    private void Url_waveList(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        WavePost post = new WavePost(addressN,addressE,date);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.waveList(post, baseUrl, url_waveList, new CustomCallBack<RemoteDataResult<List<DialogGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<DialogGet>>>
                                          response) {
                dialogGets.clear();
                dialogGets = response.body().getData();

                if (dialogGets!=null&&dialogGets.size()>0){
                    for (int i = 0; i < dialogGets.size(); i++) {
                        flag_choose.add(false);
                    }
                }
                initDialog_wave();
            }
            @Override
            public void onFail(String message) {
                ToastUtil.showShort(HomePageActivity.this,"获取失败!");
                Log.d("---fail",message);
            }
        });
    }
    private void initBottom() {
        ll_bottom.measure(w, h);
        ll_part.measure(w, h);
        int height_bottom = ll_bottom.getMeasuredHeight();
        int height_part = ll_part.getMeasuredHeight();
        Log.d("hei", (height_bottom - height_part) + "");
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                        .WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.setMargins(0, 0, 0, -(height_bottom - height_part));
        ll_bottom.setLayoutParams(lp);

        am_in = ObjectAnimator.ofFloat(ll_bottom, "translationY", -
                (height_bottom - height_part));
        am_in.setDuration(400);
        am_out = ObjectAnimator.ofFloat(ll_bottom, "translationY", 0);
        am_out.setDuration(400);

        ///时间选择器
        initTimeList();

        ///钱
        moneyThing.addTextChangedListener(new EditChangeListener());
        reward.addTextChangedListener(new EditChangeListener());
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
                if (chitGets!=null&&chitGets.size()>0){
                    chitAdapter = new ChitAdapter(HomePageActivity.this, chitGets);
                }

            }

            @Override
            public void onFail(String message) {

            }
        });
    }


    class EditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int
                i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1,
                                  int i2) {
            if (charSequence.toString().length()>0){
                money += Double.valueOf(charSequence.toString());
                moneyAll.setText("¥"+money);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
    private void initTimeList() {
        for (int i = 1; i < 13; i++) {
            month.add(i + "月");
        }
        for (int i = 1; i < 31; i++) {
            day.add(i + "日");
        }
        hour.add("00点");hour.add("01点");hour.add("02点");hour.add("03点");
        hour.add("04点");hour.add("05点");hour.add("06点");hour.add("07点");
        hour.add("08点");hour.add("09点");hour.add("10点");hour.add("11点");
        hour.add("12点");hour.add("13点");hour.add("14点");hour.add("15点");
        hour.add("16点");hour.add("17点");hour.add("18点");hour.add("19点");
        hour.add("20点");hour.add("21点");hour.add("22点");hour.add("23点");
        minute.add("00分");minute.add("01分");minute.add("02分");minute.add("03分");
        minute.add("04分");minute.add("05分");minute.add("06分");minute.add("07分");
        minute.add("08分");minute.add("09分");minute.add("10分");minute.add("11分");
        minute.add("12分");minute.add("13分");minute.add("14分");minute.add("15分");
        minute.add("16分");minute.add("17分");minute.add("18分");minute.add("19分");
        minute.add("20分");minute.add("21分");minute.add("22分");minute.add("23分");
        minute.add("24分");minute.add("25分");minute.add("26分");minute.add("27分");
        minute.add("28分");minute.add("29分");minute.add("30分");minute.add("31分");
        minute.add("32分");minute.add("33分");minute.add("34分");minute.add("35分");
        minute.add("36分");minute.add("37分");minute.add("38分");minute.add("39分");
        minute.add("40分");minute.add("41分");minute.add("42分");minute.add("43分");
        minute.add("44分");minute.add("45分");minute.add("46分");minute.add("47分");
        minute.add("48分");minute.add("49分");minute.add("50分");minute.add("51分");
        minute.add("52分");minute.add("53分");minute.add("54分");minute.add("55分");
        minute.add("56分");minute.add("57分");minute.add("58分");minute.add("59分");

    }

    private void initViewPager() {
        List<LinearLayout> views = new ArrayList<>();
        List<LinearLayout> lls = new ArrayList<>();
        List<ImageView> imageViews = new ArrayList<>();
        List<TextView> roomnames = new ArrayList<>();
        List<TextView> intros = new ArrayList<>();
        List<TextView> fans = new ArrayList<>();
        List<TextView> moneys = new ArrayList<>();
        Url_returnPlayingForfive();
        if (returnFiveGetList.size()>0){
            for(int i=0;i<returnFiveGetList.size();i++){
                ReturnFiveGet get = returnFiveGetList.get(i);
                LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate
                        (R.layout.item_banner, banner, false);
                lls.add(ll);
                views.add(lls.get(i));

                ImageView imageView = (ImageView)ll.findViewById(R.id.head);
                Glide.with(this).load(get.getPicurl()).into(imageView);
                imageViews.add(imageView);

                TextView roomname = (TextView) ll.findViewById(R.id.roomname);
                roomname.setText(get.getPlayingname());
                roomnames.add(roomname);

                TextView intro = (TextView) ll.findViewById(R.id.intro);
                intro.setText(get.getIntroduce());
                intros.add(intro);

                TextView fan = (TextView) ll.findViewById(R.id.fans);
                fan.setText(String.valueOf(get.getCou()));
                fans.add(fan);

                TextView money = (TextView) ll.findViewById(R.id.money);
                money.setText(get.getPay());
                moneys.add(money);

                bundle = new Bundle();
                bundle.putString("rongid",get.getRongid());
                bundle.putString("playUrl",get.getTokenurl());
                bundle.putInt("id",id);
                bundle.putInt("roomId",get.getId());
                goActivity(LiveRoom_PlaceActivity.class,bundle);

            }
            adapter = new ViewPagerAdapter(views, new OnPageClickListener() {
                @Override
                public void onPageClick(View view, int position) {
                    Log.d("view", position + "");
                }
            });
            banner.setAdapter(adapter);
        }else {
            viewpager.setVisibility(View.GONE);
        }







    }
    ///viewpager
    private void Url_returnPlayingForfive(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.returnPlayingForfive(baseUrl, url_viewpager, new CustomCallBack<RemoteDataResult<List<ReturnFiveGet>>>() {
            @Override
            public void onSuccess(Response<RemoteDataResult<List<ReturnFiveGet>>> response) {
                returnFiveGetList = response.body().getData();

            }

            @Override
            public void onFail(String message) {
            }
        });
    }

    private void initDrawer() {
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

    }

    private void initToolBar(HomeToolBar homeToolBar) {
        homeToolBar.setBack(R.mipmap.bg_homepage_tool);
        homeToolBar.setLeftImg(R.mipmap.icon_homepage_left);
        homeToolBar.setRightImg(R.mipmap.icon_homepage_right);
        homeToolBar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        homeToolBar.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putInt("id",id);
                bundle.putString("Qiniu_token",Qiniu_token);
                goActivity(RoastActivity.class,bundle);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        Log.d("----", w + "");
        homepageMap.onCreate(savedInstanceState);
        initMapView();


    }

    private void initMapView() {
        myLocationStyle = new MyLocationStyle();
        /*myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R
                .mipmap.icon_homapage_location));*/

        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细

        if (aMap == null) {
            aMap = homepageMap.getMap();
            mUiSettings = aMap.getUiSettings();
        }
        aMap.setLocationSource(this);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        mUiSettings.setMyLocationButtonEnabled(false); // 是否显示默认的定位按钮

        mUiSettings.setScaleControlsEnabled(false);// 设置地图默认的比例尺是否显示
        mUiSettings.setZoomControlsEnabled(false);

        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        aMap.setOnCameraChangeListener(this);
    }


    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        LatLng point = cameraPosition.target;

        RegeocodeQuery query = new RegeocodeQuery(convertToLatLonPoint(point)
                , 200, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);//根据地址执行异步地址解析
    }

    /**
     * 逆地理回调编码
     *
     * @param
     * @param i
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {
            if (regeocodeResult != null && regeocodeResult
                    .getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress
                    () != null) {
                addrName = regeocodeResult.getRegeocodeAddress()
                        .getFormatAddress();
                //Toast.makeText(this, addrName, Toast.LENGTH_SHORT).show();
                LatLonPoint latLonPoint = regeocodeResult.getRegeocodeQuery
                        ().getPoint();
                if (flag_location) {
                    txt_location.setText(addrName);
                    goodsaddressword = addrName;
                    goodsaddressN = latLonPoint.getLatitude();
                    goodsaddressE = latLonPoint.getLongitude();
                }
                if (flag_destination) {
                    txt_destination.setText(addrName);
                    addressword = addrName;
                    errandaddressN = latLonPoint.getLatitude();
                    errandaddressE = latLonPoint.getLongitude();
                }
                if (flag_take) {
                    txt_take.setText(addrName);
                    addressN = latLonPoint.getLatitude();
                    addressE = latLonPoint.getLongitude();
                }

                /*LatLonPoint latLonPoint = regeocodeResult.getRegeocodeQuery
                ().getPoint();
                Toast.makeText(this,latLonPoint.toString(),Toast.LENGTH_LONG)
                .show();*/
            } else
                Toast.makeText(this, "对不起，没有搜索到相关信息", Toast.LENGTH_SHORT)
                        .show();
        } else if (i == 27) {
            Toast.makeText(this, "搜索失败，请检查网络连接", Toast.LENGTH_SHORT).show();
        } else if (i == 32) {
            Toast.makeText(this, "key验证无效", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                onLocationChangedListener.onLocationChanged(aMapLocation);
                LatLng latLng = new LatLng(aMapLocation.getLatitude(),
                        aMapLocation.getLongitude());
                cityAddr = aMapLocation.getAddress();

                if (flag_move) {
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom
                            (latLng, 18));
                }
                flag_move = false;


                homeToolBar.setTitle(aMapLocation.getCity());

            } else {
                Log.e("error info:", aMapLocation.getErrorCode() + "-----" +
                        aMapLocation.getErrorInfo());
            }
        }
    }

    @OnClick({R.id.locate, R.id.close_viewpager, R.id.order_out, R.id.take_ll
            , R.id.buy_ll, R.id.order_set, R.id.order_take, R.id.txt_location
            , R.id.txt_destin, R.id.txt_take, R.id.txt_reachtime, R.id.place,
            R.id.coversitionlist})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.coversitionlist:
                RongIM.getInstance().startConversationList(HomePageActivity
                        .this);
                break;
            case R.id.place:
                Log.d("-----check",check()+"");
                if (check()){
                    dialog = new AlertDialog.Builder(this).create();
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setGravity(Gravity.BOTTOM);
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setWindowAnimations(R.style.dialogStyle);
                    View view = View.inflate(this, R.layout.dialog_place, null);
                    window.setContentView(view);
                    final TextView txt_realpay = (TextView)view.findViewById(R.id.realpay);
                    txt_realpay.setText("¥"+money);
                    TextView needmoney = (TextView)view.findViewById(R.id.needmoney);
                    needmoney.setText(moneyAll.getText().toString());
                    TextView yes = (TextView) view.findViewById(R.id.yes);
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Url_place();
                            dialog.dismiss();
                        }
                    });
                    final TextView chit_num = (TextView) view.findViewById(R.id
                            .chit_num);
                    RelativeLayout chit = (RelativeLayout) view.findViewById(R.id
                            .chit);
                    chit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(chitGets.size()>0){
                                builder = new AlertDialog.Builder(view.getContext());
                                View view3 = LayoutInflater.from(view.getContext())
                                        .inflate(R.layout.dialog_chit, null);
                                RecyclerView rv = (RecyclerView) view3.findViewById(R
                                        .id.recycler);
                                chitAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, ViewHolder
                                            holder, int position) {
                                        if (chitGets.get(position).getMark() == 1000) {
                                            chit_num.setText("¥10");
                                            couponpay = 10;

                                        } else if (chitGets.get(position).getMark() == 500) {
                                            chit_num.setText("¥5");
                                            couponpay = 5;
                                        } else {
                                            chit_num.setText("¥2");
                                            couponpay = 2;
                                        }
                                        if (money>=couponpay){
                                            realpay = money-couponpay;
                                            txt_realpay.setText("¥"+realpay);
                                            dialog.dismiss();
                                        }else {

                                            ToastUtil.showShort(view.getContext(),"无法使用!");
                                        }
                                    }

                                    @Override
                                    public boolean onItemLongClick(View view,
                                                                   ViewHolder holder,
                                                                   int position) {
                                        return false;
                                    }
                                });

                                rv.setAdapter(chitAdapter);

                                builder.setView(view3);
                                dialog = builder.create();
                                dialog.show();
                            }else {
                                ToastUtil.showShort(HomePageActivity.this,"没有代金券!");
                            }


                        }
                    });
                }else {
                    ToastUtil.showShort(this,"请填写完整!");
                }

                break;
            case R.id.txt_reachtime:
                Log.d("time", "isshortclick");
                builder = new AlertDialog.Builder(HomePageActivity.this);
                View view2 = LayoutInflater.from(HomePageActivity.this)
                        .inflate(R.layout.dialog_time, null);
                wv01 = (WheelView) view2.findViewById(R.id.wv1);
                wv02 = (WheelView) view2.findViewById(R.id.wv2);
                wv03 = (WheelView) view2.findViewById(R.id.wv3);
                wv04 = (WheelView) view2.findViewById(R.id.wv4);
                wv01.setType(0);
                wv02.setType(0);
                wv03.setType(0);
                wv04.setType(0);
                wv01.setSeletion(5);
                wv02.setSeletion(15);
                wv03.setSeletion(12);
                wv04.setSeletion(30);
                wv06 = (WheelView) view2.findViewById(R.id.wv6);
                wv07 = (WheelView) view2.findViewById(R.id.wv7);
                wv08 = (WheelView) view2.findViewById(R.id.wv8);
                wv09 = (WheelView) view2.findViewById(R.id.wv9);
                wv06.setType(1);
                wv07.setType(1);
                wv08.setType(1);
                wv09.setType(1);
                wv06.setSeletion(5);
                wv07.setSeletion(15);
                wv08.setSeletion(12);
                wv09.setSeletion(30);
                wv01.setOffset(2);
                wv01.setItems(month);
                wv02.setOffset(2);
                wv02.setItems(day);
                wv03.setOffset(2);
                wv03.setItems(hour);
                wv04.setOffset(2);
                wv04.setItems(minute);
                wv06.setOffset(2);
                wv06.setItems(month);
                wv07.setOffset(2);
                wv07.setItems(day);
                wv08.setOffset(2);
                wv08.setItems(hour);
                wv09.setOffset(2);
                wv09.setItems(minute);

                ImageView time_yes = (ImageView) view2.findViewById(R.id
                        .time_yes);
                time_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        starttime = year+"-"+wv01.getSeletedItem().replace("月","") +"-"+
                                wv02.getSeletedItem().replace("日","")+" "
                                + wv03.getSeletedItem().replace("点","")+":"
                                + wv04.getSeletedItem().replace("分","")+":"+"00";
                        endtime = year+"-"+wv06.getSeletedItem().replace("月","") +"-"+
                                wv07.getSeletedItem().replace("日","")+" "
                                + wv08.getSeletedItem().replace("点","")+":"
                                + wv09.getSeletedItem().replace("分","")+":"+"00";
                        txt_reachtime.setText(wv01.getSeletedItem() +
                                wv02.getSeletedItem()
                                + wv03.getSeletedItem() + wv04.getSeletedItem
                                () + "～" + wv06.getSeletedItem()
                                + wv07.getSeletedItem() + wv08.getSeletedItem
                                () + wv09.getSeletedItem());
                        dialog.dismiss();
                    }
                });
                builder.setView(view2);
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.txt_location:
                flag_location = true;
                flag_destination = false;
                flag_take = false;
                break;
            case R.id.txt_destin:
                flag_location = false;
                flag_destination = true;
                flag_take = false;
                break;

            case R.id.txt_take:
                flag_location = false;
                flag_destination = false;
                flag_take = true;
                break;

            case R.id.locate:
                if (aMapLocationClient != null) {
                    aMapLocationClient.startLocation();
                    flag_move = true;
                }
                break;
            case R.id.close_viewpager:
                viewpager.setVisibility(View.GONE);
                break;
            case R.id.order_out:
                if (flag_order) {
                    closeBottom();
                } else {
                    am_in.start();
                    flag_order = true;
                    btn_order.setVisibility(View.GONE);
                    order_out.setImageResource(R.mipmap.icon_order_down);
                    ///关闭手势滑动
                    drawerLayout.setDrawerLockMode(DrawerLayout
                            .LOCK_MODE_LOCKED_CLOSED);
                    //禁止地图滑动
                    homepageMap.setEnabled(false);
                    homeToolBar.LeftClick(false);
                    homeToolBar.RightClick(false);
                }
                break;
            case R.id.take_ll:
                take_img.setImageResource(R.drawable.circle_yello);
                buy_img.setImageResource(R.drawable.circle_empty);
                take_content.setVisibility(View.VISIBLE);
                buy_content.setVisibility(View.GONE);
                type =0;
                break;
            case R.id.buy_ll:
                take_img.setImageResource(R.drawable.circle_empty);
                buy_img.setImageResource(R.drawable.circle_yello);
                take_content.setVisibility(View.GONE);
                buy_content.setVisibility(View.VISIBLE);
                type =1;
                break;
            case R.id.order_set:
                btn_order.setBackgroundResource(R.mipmap.bg_homepage_btn_left);
                order_set.setTextColor(getResources().getColor(R.color.black));
                order_take.setTextColor(getResources().getColor(R.color
                        .gray03));
                ll_bottom.setVisibility(View.VISIBLE);
                ll_bottom_take.setVisibility(View.GONE);
                RelativeLayout.LayoutParams lp_set = new RelativeLayout
                        .LayoutParams(PxUtil.dip2px(this, 120), PxUtil.dip2px
                        (this, 48));
                lp_set.addRule(RelativeLayout.ABOVE, R.id.ll_bottom);
                lp_set.setMargins(PxUtil.dip2px(this, 10), 0, 0, PxUtil
                        .dip2px(this, 2));
                btn_order.setLayoutParams(lp_set);

                flag_location = false;
                flag_destination = false;
                flag_take = false;
                break;
            case R.id.order_take:
                btn_order.setBackgroundResource(R.mipmap.bg_homepage_btn_right);
                order_take.setTextColor(getResources().getColor(R.color.black));
                order_set.setTextColor(getResources().getColor(R.color.gray03));
                ll_bottom.setVisibility(View.GONE);
                ll_bottom_take.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams lp_take = new RelativeLayout
                        .LayoutParams(PxUtil.dip2px(this, 120), PxUtil.dip2px
                        (this, 48));
                lp_take.addRule(RelativeLayout.ABOVE, R.id.ll_bottom_take);
                lp_take.setMargins(PxUtil.dip2px(this, 10), 0, 0, PxUtil
                        .dip2px(this, 2));
                btn_order.setLayoutParams(lp_take);

                flag_location = false;
                flag_destination = false;
                flag_take = false;
                break;

        }

    }


    /**
     * 把LatLng对象转化为LatLonPoint对象
     */
    public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
        return new LatLonPoint(latlon.latitude, latlon.longitude);
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        onLocationChangedListener = null;
        if (aMapLocationClient != null) {
            aMapLocationClient.stopLocation();
            aMapLocationClient.onDestroy();
        }
        aMapLocationClient = null;

    }

    /**
     * 激活定位
     *
     * @param monLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener monLocationChangedListener) {
        onLocationChangedListener = monLocationChangedListener;
        if (aMapLocationClient == null) {
            //初始化AMapLocationClient，并绑定监听
            aMapLocationClient = new AMapLocationClient(getApplicationContext
                    ());

            //初始化定位参数
            mapLocationClientOption = new AMapLocationClientOption();
            //设置定位精度
            mapLocationClientOption.setLocationMode(AMapLocationClientOption
                    .AMapLocationMode.Hight_Accuracy);
            //是否返回地址信息
            mapLocationClientOption.setNeedAddress(true);
            //是否只定位一次
            mapLocationClientOption.setOnceLocation(false);
            //设置是否强制刷新WIFI，默认为强制刷新
            mapLocationClientOption.setWifiActiveScan(true);
            //是否允许模拟位置
            mapLocationClientOption.setMockEnable(false);
            //定位时间间隔
            mapLocationClientOption.setInterval(2000);
            //给定位客户端对象设置定位参数
            aMapLocationClient.setLocationOption(mapLocationClientOption);
            //绑定监听
            aMapLocationClient.setLocationListener(this);
            //开启定位
            aMapLocationClient.startLocation();
        }

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        homepageMap.onDestroy();
        aMapLocationClient.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        homepageMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        homepageMap.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        homepageMap.onSaveInstanceState(bundle);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
    }


    private void  closeBottom(){
        take_img.setImageResource(R.drawable.circle_yello);
        buy_img.setImageResource(R.drawable.circle_empty);
        take_content.setVisibility(View.VISIBLE);
        buy_content.setVisibility(View.GONE);
        am_out.start();
        flag_order = false;
        btn_order.setVisibility(View.VISIBLE);
        order_out.setImageResource(R.mipmap.icon_order_up);
        ///打开手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //允许地图滑动
        homepageMap.setEnabled(true);
        //toolbar按钮
        homeToolBar.LeftClick(true);
        homeToolBar.RightClick(true);
    }
    /**
     * 按两次BACK键退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            } else if (flag_order) {
               closeBottom();
            } else {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    ToastUtil.showShort(this, getString(R.string
                            .text_one_more_click));
                    exitTime = System.currentTimeMillis();
                } else {
                    BaseAppManager.getInstance().clearAll();
                    System.exit(0);
                }
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() < bottom && event.getY() > top) {
                return false;
            } else {
                return true;
            }
        }
        return false;

    }

}

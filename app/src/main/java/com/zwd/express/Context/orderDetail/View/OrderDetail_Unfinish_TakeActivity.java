package com.zwd.express.Context.orderDetail.View;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.bumptech.glide.Glide;
import com.zwd.express.Context.confirmPlace.View.ConfirmPlace_BuyActivity;
import com.zwd.express.Context.confirmPlace.View.ConfirmPlace_TakeActivity;
import com.zwd.express.Context.confirmTake.View.ConfirmTake_BuyActivity;
import com.zwd.express.Context.confirmTake.View.ConfirmTake_TakeActivity;
import com.zwd.express.Context.orderDetail.Module.OrderDetailGet;
import com.zwd.express.Context.orderDetail.Module.OrderDetailPost;
import com.zwd.express.R;
import com.zwd.express.Widget02.overlay.WalkRouteOverlay;
import com.zwd.express.Widget02.overlay.WalkRouteOverlay_take;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.base.BaseAppManager;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;
import com.zwd.express.util.PxUtil;
import com.zwd.express.util.ToastUtil;

import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;
import retrofit2.Response;

public class OrderDetail_Unfinish_TakeActivity extends BaseActivity
        implements AMapLocationListener,
        LocationSource, OnRouteSearchListener {

    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.ll_center)
    LinearLayout ll_center;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @BindView(R.id.ll_top)
    LinearLayout ll_top;
    @BindView(R.id.take_img)
    ImageView take_img;
    @BindView(R.id.take_txt)
    TextView take_txt;
    @BindView(R.id.buy_img)
    ImageView buy_img;
    @BindView(R.id.buy_txt)
    TextView buy_txt;
    @BindView(R.id.ll_money)
    LinearLayout ll_money;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_goodsaddressword)
    TextView txtGoodsaddressword;
    @BindView(R.id.txt_addressword)
    TextView txtAddressword;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_reward)
    TextView txtReward;
    @BindView(R.id.sendMsg)
    LinearLayout sendMsg;
    @BindView(R.id.takephone)
    LinearLayout takephone;
    @BindView(R.id.ll_confirm)
    LinearLayout ll_confirm;

    private UiSettings mUiSettings;
    private AMap aMap;
    private OnLocationChangedListener onLocationChangedListener;
    private AMapLocationClientOption mapLocationClientOption = null;
    private AMapLocationClient aMapLocationClient;
    private MyLocationStyle myLocationStyle;

    private RouteSearch routSearch;
    private WalkRouteQuery query;
    private LatLng destination, thing, start;
    private RouteSearch.FromAndTo fromAndTo;
    private WalkRouteOverlay_take walkRouteOverlayTake_destination;
    private WalkRouteOverlay walkRouteOverlay_thing;

    private boolean flag_up = false;
    private int h, w;
    private ObjectAnimator am_in, am_out;

    private int flag_type = 0;///0买东西  1拿东西
    private Bundle bundle;
    private String Qiniu_token;
    private int orderId;
    private int stu;
    private int type;
    private OrderDetailPost post;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url_cancle = "Accept_OffOrder.aspx";
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    private String url = "Accept_ReturnOrderDetail.aspx";

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail__unfinish__take;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        orderId = bundle.getInt("orderId");
        stu = bundle.getInt("stu");
        type = bundle.getInt("type");
        Qiniu_token = bundle.getString("Qiniu_token");
        post = new OrderDetailPost(orderId, stu, type);
        initActionBar(toolbar);
        url_order();
    }


    private void initView(final OrderDetailGet get) {
        destination = new LatLng(get.getErrandaddressN(), get.getErrandaddressE());
        thing = new LatLng(get.getGoodsaddressN(),get.getGoodsaddressE());
        if (get.getAcceptuserheading() != null && get.getAcceptuserheading()
                .length() > 0) {
            Glide.with(this).load(get.getAcceptuserheading()).into(imgHead);
        } else {
            Glide.with(this).load(R.mipmap.pic_head).into(imgHead);
        }
        txtName.setText(get.getAcceptname());
        txtPhone.setText(get.getAcceptphone());
        txtGoodsaddressword.setText(get.getGoodsaddressword());
        txtAddressword.setText(get.getAddressword());
        txtTime.setText(get.getStarttime() + "～" + get.getEndtime());
        if (get.getType() == 1) {
            take_img.setImageResource(R.drawable.circle_empty02);
            take_txt.setTextColor(getResources().getColor(R.color.gray_05));
            buy_img.setImageResource(R.drawable.circle_yello);
            buy_txt.setTextColor(getResources().getColor(R.color.black));
            ll_money.setVisibility(View.VISIBLE);
            txtPrice.setText("¥" + get.getPrice());
        } else {
            buy_img.setImageResource(R.drawable.circle_empty02);
            buy_txt.setTextColor(getResources().getColor(R.color.gray_05));
            take_img.setImageResource(R.drawable.circle_yello);
            take_txt.setTextColor(getResources().getColor(R.color.black));
            ll_money.setVisibility(View.GONE);
        }
        txtDescription.setText(get.getDescription());
        txtReward.setText("¥" + get.getReward());
        initCenter();
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM.getInstance().startPrivateChat
                        (OrderDetail_Unfinish_TakeActivity.this
                                , String.valueOf(get.getAcceptuserid())
                                , get.getAcceptname());
            }
        });
        takephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+get.getAcceptphone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        ll_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putInt("id",orderId);
                bundle.putInt("stu",stu);
                bundle.putString("Qiniu_token", Qiniu_token);
                if (get.getType() == 1) {
                    goActivityForResult(ConfirmTake_BuyActivity.class,bundle,0);
                } else {
                    goActivityForResult(ConfirmTake_TakeActivity.class,bundle,0);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        switch (requestCode){
            case 0:
                if (resultCode==0){
                    Bundle data = intent.getExtras();
                    boolean flag = data.getBoolean("flag");
                    if (flag){
                        finish();
                    }
                }
                break;
        }

    }

    private void url_order() {
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.orderDetail(post, baseUrl, url, new
                CustomCallBack<RemoteDataResult<OrderDetailGet>>() {
                    @Override
                    public void onSuccess
                            (Response<RemoteDataResult<OrderDetailGet>>
                                     response) {
                        OrderDetailGet get = response.body().getData();
                        initView(get);
                    }

                    @Override
                    public void onFail(String message) {
                        Log.d("---fail", message);
                    }
                });
    }
    private void initCenter() {
        ll_center.measure(w, h);
        ll_top.measure(w, h);
        int h_center = ll_center.getMeasuredHeight();
        int h_top = ll_top.getMeasuredHeight();

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                        .WRAP_CONTENT);
        lp.addRule(RelativeLayout.ABOVE, R.id.ll_bottom);
        lp.setMargins(PxUtil.dip2px(this, 4), 0, PxUtil.dip2px(this, 4),
                -h_center);
        ll_top.setLayoutParams(lp);
        am_in = ObjectAnimator.ofFloat(ll_top, "translationY", -h_center);
        am_in.setDuration(400);
        am_out = ObjectAnimator.ofFloat(ll_top, "translationY", 0);
        am_out.setDuration(400);
    }

    ///取消订单
    private void Url_cancle() {
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.cancleOrder(post, baseUrl, url_cancle, new
                CustomCallBack<RemoteDataResult>() {

                    @Override
                    public void onSuccess(Response<RemoteDataResult> response) {
                        dialog_("取消成功!", true);
                    }

                    @Override
                    public void onFail(String message) {
                        Log.d("---fail", message);
                        dialog_("取消失败!", false);
                    }
                });
    }

    private void dialog_(String str, final boolean flag) {
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
                if (flag) {
                    finish();
                }
            }
        });
        TextView txt = (TextView) view1.findViewById(R.id.txt);
        txt.setText(str);
        builder.setView(view1);
        dialog = builder.create();
        dialog.show();
    }

    @OnClick({R.id.img_up, R.id.sendMsg,R.id.ll_cancle})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.img_up:
                if (flag_up) {
                    am_out.start();
                    flag_up = false;
                } else {
                    am_in.start();
                    flag_up = true;
                }
                break;
            case R.id.sendMsg:
                RongIM.getInstance().startPrivateChat
                        (OrderDetail_Unfinish_TakeActivity.this, "02", "小华");
                break;
            case R.id.ll_cancle:
                Url_cancle();
                break;
        }


    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("订单详情");
        toolbar.hideRight();
        toolbar.setBackground(getResources().getDrawable(R.mipmap
                .bg_nomal_tool));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        map.onCreate(savedInstanceState);
        initMapView();
    }

    private void initMapView() {
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(
                R.mipmap.icon_circle));

        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细

        if (aMap == null) {
            aMap = map.getMap();
            mUiSettings = aMap.getUiSettings();
        }
        aMap.setLocationSource(this);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        mUiSettings.setMyLocationButtonEnabled(false); // 是否显示默认的定位按钮

        mUiSettings.setScaleControlsEnabled(false);// 设置地图默认的比例尺是否显示
        mUiSettings.setZoomControlsEnabled(false);

        routSearch = new RouteSearch(this);
        routSearch.setRouteSearchListener(this);


    }


    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int code) {
        aMap.clear();
        if (code == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {

                    WalkPath walkPath = result.getPaths().get(0);
                    if (walkRouteOverlayTake_destination != null) {
                        walkRouteOverlayTake_destination.removeFromMap();
                    }
                    walkRouteOverlayTake_destination = new
                            WalkRouteOverlay_take(this,
                            aMap, walkPath,
                            result.getStartPos(), result.getTargetPos());
                    walkRouteOverlayTake_destination.addToMap();
                    walkRouteOverlayTake_destination.setNodeIconVisibility
                            (false);

                    Log.d("road", walkPath.getSteps().get(0).getRoad());
                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.showShort(this, "对不起，没有搜索到相关数据!");
                }

            } else {
                ToastUtil.showShort(this, "对不起，没有搜索到相关数据!");
            }
        } else {
            ToastUtil.showShort(this, code + "");
        }
    }

    private void searchRouteResult() {
        fromAndTo = new RouteSearch.FromAndTo(convertToLatLonPoint(start)
                , convertToLatLonPoint(thing));

        query = new WalkRouteQuery(fromAndTo, RouteSearch.WALK_MULTI_PATH);
        routSearch.calculateWalkRouteAsyn(query);

    }


    /**
     * 把LatLng对象转化为LatLonPoint对象
     */
    public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
        return new LatLonPoint(latlon.latitude, latlon.longitude);
    }

    public static LatLng convertToLatLng(LatLonPoint point) {
        return new LatLng(point.getLatitude(), point.getLongitude());
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                onLocationChangedListener.onLocationChanged(aMapLocation);

                start = new LatLng(aMapLocation.getLatitude(), aMapLocation
                        .getLongitude());

                Log.d("start", start.toString());
                if (start == thing) {
                    thing = destination;
                }
                searchRouteResult();

            } else {
                Log.e("error info:", aMapLocation.getErrorCode() + "-----" +
                        aMapLocation.getErrorInfo());
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
        aMapLocationClient.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }


    /* *
      * 激活定位
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

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {


    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

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
            if (flag_up) {
                am_out.start();
                flag_up = false;
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}

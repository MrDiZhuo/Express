package com.zwd.express.Context.Locationservice.Controller;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;
import com.zwd.express.Context.Locationservice.Module.MyServicePost;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;

import retrofit2.Response;

public class MyLocationService extends Service  {
    private AMapLocationClient mLocationClient;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener ;
    private AMapLocationClientOption mLocationOption=null;

    private double addressN=0.0,addressE=0.0;
    private String address;

    private SharedPreferences sharedPreferences;
    private int id;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url="Accept_UpdateAccepterAddress.aspx";

    public MyLocationService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        addressN = aMapLocation.getLatitude();
                        addressE = aMapLocation.getLongitude();
                        address = aMapLocation.getAddress();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                url_address(addressN,addressE,address);
                            }
                        }).start();
                    }else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }

        };
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption
                .AMapLocationMode.Hight_Accuracy);
        mLocationOption.setLocationMode(AMapLocationClientOption
                .AMapLocationMode.Battery_Saving);
        mLocationOption.setLocationMode(AMapLocationClientOption
                .AMapLocationMode.Device_Sensors);
        mLocationOption.setInterval(1000);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setWifiActiveScan(true);
        mLocationClient.setLocationOption(mLocationOption);


        sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        id = sharedPreferences.getInt("id",0);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //开启定位
                mLocationClient.startLocation();
            }
        }).start();

    }

    private void url_address(double addressN,double addressE,String address){
        MyServicePost post = new MyServicePost(id,addressN,addressE,address);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.updateAccepterAddress(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {

            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                //Log.d("---success",response.body().getResultMessage());
            }

            @Override
            public void onFail(String message) {
                Log.d("---fail",message);
            }
        });
    }
}

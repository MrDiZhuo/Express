package com.zwd.express.internet;

import com.zwd.express.BuildConfig;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by XRY on 2016/10/4.
 */

public class HttpHelper {
    private HashMap<String, Object> mServiceMap; //用于存储retrofit服务
    public HttpHelper() {
        mServiceMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <S> S getService(Class<S> serviceClass, String url) {
        /**
         * 做一个判断，如果已经存在该服务，直接取出，不存在再创建
         */
        if (!mServiceMap.containsKey(serviceClass.getName())) {
            Object obj = createService(serviceClass,url);
            mServiceMap.put(serviceClass.getName(), obj);
            return (S) obj;
        } else {
            return (S) mServiceMap.get(serviceClass.getName());
        }
    }

    /**
     * 创建Retrofit服务
     * @param serviceClass

     * @param <S>
     * @return
     */
    private <S> S createService(Class<S> serviceClass, String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别

            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }

       /* HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);*/


        OkHttpClient okHttpClient = builder.build();
            /*    .addInterceptor(logging)
                .build();*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(serviceClass);
    }
}

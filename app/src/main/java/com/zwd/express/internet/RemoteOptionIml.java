package com.zwd.express.internet;

import android.util.Log;

import com.zwd.express.Context.Locationservice.Module.MyServicePost;
import com.zwd.express.Context.bill.Module.BillGet;
import com.zwd.express.Context.confirmPlace.Module.ConfirmPlaceGet;
import com.zwd.express.Context.confirmPlace.Module.ConfirmPlacePost;
import com.zwd.express.Context.confirmTake.Module.ConfirmTakeGet;
import com.zwd.express.Context.confirmTake.Module.ConfirmTakePost;
import com.zwd.express.Context.editSelf.Module.EditSelfPost;
import com.zwd.express.Context.homePage.Module.DialogOrderPost;
import com.zwd.express.Context.homePage.Module.PlacePost;
import com.zwd.express.Context.homePage.Module.ReturnFiveGet;
import com.zwd.express.Context.homePage.Module.DialogGet;
import com.zwd.express.Context.homePage.Module.WavePost;
import com.zwd.express.Context.identity.Module.IndentityGet;
import com.zwd.express.Context.identity.Module.IndentityPost;
import com.zwd.express.Context.integrate.Module.GetCouponPost;
import com.zwd.express.Context.liveList.Module.CreateRoomGet;
import com.zwd.express.Context.liveList.Module.CreateRoomToServerGet;
import com.zwd.express.Context.liveList.Module.CreateRoomToServerPost;
import com.zwd.express.Context.liveList.Module.RoomPublishGet;
import com.zwd.express.Context.liveRoom.Module.JoinLivePost;
import com.zwd.express.Context.liveRoom.Module.LiveOrderPost;
import com.zwd.express.Context.liveRoom.Module.LiveRoomUpdateGet;
import com.zwd.express.Context.liveRoom.Module.LiveRoomUpdatePost;
import com.zwd.express.Context.liveRoom.Module.RoomPicGet;
import com.zwd.express.Context.liveTake.Module.LiveTakeGet;
import com.zwd.express.Context.login.Module.LoginGet;
import com.zwd.express.Context.login.Module.LoginPost;
import com.zwd.express.Context.myOrder.Module.MyOrderGet;
import com.zwd.express.Context.myWallet.Module.ChitGet;
import com.zwd.express.Context.myWallet.Module.MyWalletGet;
import com.zwd.express.Context.orderDetail.Module.GetAddressGet;
import com.zwd.express.Context.orderDetail.Module.OrderDetailGet;
import com.zwd.express.Context.orderDetail.Module.OrderDetailPost;
import com.zwd.express.Context.otherInfo.Module.OtherInfoGet;
import com.zwd.express.Context.register.Module.RegisterGet;
import com.zwd.express.Context.register.Module.RegisterPost;
import com.zwd.express.Context.register.Module.VertifyGet;
import com.zwd.express.Context.roast.Module.IfReturnGet;
import com.zwd.express.Context.roast.Module.ProvePost;
import com.zwd.express.Context.roast.Module.PublishRoastPost;
import com.zwd.express.Context.roast.Module.ReplyGet;
import com.zwd.express.Context.roast.Module.RoastListGet;
import com.zwd.express.Context.roastDetail.Module.CommentGet;
import com.zwd.express.Context.roastDetail.Module.DetailPost;
import com.zwd.express.Context.roastDetail.Module.ReplyPost;
import com.zwd.express.Context.roastDetail.Module.RoastDetailGet;
import com.zwd.express.Context.selfInfo.Module.EditHeadPost;
import com.zwd.express.Context.sign.Module.SignGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by asus-pc on 2017/4/9.
 */

public class RemoteOptionIml {
    private HttpHelper httpHelper=new HttpHelper();

    public void login(LoginPost post, String baseUrl,
                      String url, CustomCallBack<RemoteDataResult<LoginGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("username",post.getUsername());
            jsonObject.put("password",post.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<LoginGet>> remoteDataResultCall = remoteApi.login(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }
    public void register(RegisterPost post, String baseUrl,
                         String url, CustomCallBack<RemoteDataResult<RegisterGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("username",post.getUsername());
            jsonObject.put("password",post.getPassword());
            jsonObject.put("name",post.getName());
            jsonObject.put("sex",post.getSex());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<RegisterGet>> remoteDataResultCall = remoteApi.register(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }
    public void forgetPsw(LoginPost post, String baseUrl,
                         String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("username",post.getUsername());
            jsonObject.put("password",post.getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.forgetPsw(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }
    public void editSelf(EditSelfPost post, String baseUrl,
                         String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("name",post.getName());
            jsonObject.put("username",post.getUsername());
            jsonObject.put("sex",post.getSex());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.editSelf(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    ///修改头像
    public void editHead(EditHeadPost post, String baseUrl,
                         String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("heading",post.getHeading());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.editHead(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }


    public void place(PlacePost post, String baseUrl,
                      String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userid",post.getId());
            jsonObject.put("goodsaddressword",post.getGoodsaddressword());
            jsonObject.put("goodsaddressN",post.getGoodsaddressN());
            jsonObject.put("goodsaddressE",post.getGoodsaddressE());
            jsonObject.put("addressword",post.getAddressword());
            jsonObject.put("errandaddressN",post.getErrandaddressN());
            jsonObject.put("errandaddressE",post.getErrandaddressE());
            jsonObject.put("starttime",post.getStarttime());
            jsonObject.put("endtime",post.getEndtime());
            jsonObject.put("name",post.getName());
            jsonObject.put("phone",post.getPhone());
            jsonObject.put("type",post.getType());
            jsonObject.put("description",post.getDescription());
            jsonObject.put("price",post.getPrice());
            jsonObject.put("reward",post.getReward());
            jsonObject.put("status",post.getStatus());
            jsonObject.put("couponid",post.getCouponid());
            jsonObject.put("couponpay",post.getCouponpay());
            jsonObject.put("realpay",post.getRealpay());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.place(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    public void publishRoast(PublishRoastPost post, String baseUrl,
                             String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userid",post.getUserid());
            jsonObject.put("chatcontent",post.getChatcontent());
            jsonObject.put("approve",post.getApprove());

            JSONArray jsonArray = new JSONArray();
            for(int i=0;i<post.getPlist().size();i++){
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("picing",post.getPlist().get(i));
                jsonArray.put(i,jsonObject1);
            }
            jsonObject.put("plist",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.publishRoast(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    public void roastList(int post, String baseUrl,
                          String url, CustomCallBack<RemoteDataResult<List<RoastListGet>>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<RoastListGet>>> remoteDataResultCall = remoteApi.roastList(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    public void Prove(ProvePost post, String baseUrl,
                         String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userid",post.getUserid());
            jsonObject.put("chatid",post.getChatid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.Prove(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }


    public void reply(ReplyPost post, String baseUrl,
                      String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("chatid",post.getChatid());
            jsonObject.put("userid",post.getUserid());
            jsonObject.put("comment",post.getComment());
            jsonObject.put("dtime",post.getDtime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.reply(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    public void roastDetail(DetailPost post, String baseUrl,
                            String url, CustomCallBack<RemoteDataResult<RoastDetailGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getChatId());
            jsonObject.put("userid",post.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<RoastDetailGet>> remoteDataResultCall = remoteApi.roastDetail(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    public void commentDetail(DetailPost post, String baseUrl,
                            String url, CustomCallBack<RemoteDataResult<CommentGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getChatId());
            jsonObject.put("userid",post.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<CommentGet>> remoteDataResultCall = remoteApi.commentDetail(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    public void commentReply(ReplyPost post, String baseUrl,
                      String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("chatcommentid",post.getChatid());
            jsonObject.put("userid",post.getUserid());
            jsonObject.put("targetid",post.getTargetid());
            jsonObject.put("comment",post.getComment());
            jsonObject.put("dtime",post.getDtime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.commentReply(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    /**
     * 短信验证
     */
    public void returncode(String post, String baseUrl, String url,CustomCallBack<RemoteDataResult<VertifyGet>>  callBack){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<VertifyGet>> remoteDataResultCall = remoteApi.returncode(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 七牛
     */
    public void GetQiniuToken(String baseUrl,String url,Callback<String> callBack){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        Retrofit build = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, java.lang.annotation.Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, String>() {
                            @Override
                            public String convert(ResponseBody value) throws IOException {
                                return value.string();
                            }
                        };
                    }
                })
                .client(okHttpClient)
                .build();
        RemoteApi remoteApi = build.create(RemoteApi.class);
        Call<String> token = remoteApi.GetQiniuToken(url);
        token.enqueue(callBack);
    }

    /**
     * 创建直播空间
     */
    public void createRoom(String baseUrl, String url, Callback<CreateRoomGet> callBack){
        JSONObject jsonObject = new JSONObject();
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<CreateRoomGet> remoteDataResultCall = remoteApi.createRoom(url,jsonObject);
        remoteDataResultCall.enqueue(callBack);
    }

    /**
     * 直播推流地址
     */
    public void roomPublish(String roomId,String baseUrl, String url, Callback<RoomPublishGet> callBack){
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RoomPublishGet> remoteDataResultCall = remoteApi.roomPublish(url,roomId);
        remoteDataResultCall.enqueue(callBack);



    }

    /**
     * 直播拉流地址
     */
    public void roomPlay(String roomId,String baseUrl, String url, Callback<RoomPublishGet> callBack){
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RoomPublishGet> remoteDataResultCall = remoteApi.roomPlay(url,roomId);
        remoteDataResultCall.enqueue(callBack);



    }


    ////直播间
    public void createRoomToServer(CreateRoomToServerPost post, String baseUrl,
                                   String url, CustomCallBack<RemoteDataResult<CreateRoomToServerGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userid",post.getUserid());
            jsonObject.put("pushurl",post.getPushurl());
            jsonObject.put("tokenurl",post.getTokenurl());
            jsonObject.put("rongid",post.getRongid());
            jsonObject.put("picurl","");
            jsonObject.put("playingname",post.getPlayingname());
            jsonObject.put("introduce",post.getIntroduce());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<CreateRoomToServerGet>> remoteDataResultCall = remoteApi.createRoomToServer(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    ///关闭直播
    public void closeLive(int post, String baseUrl,
                             String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.closeLive(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }


    ///直播更新
    public void updateLive(LiveRoomUpdatePost post, String baseUrl,
                           String url, CustomCallBack<RemoteDataResult<LiveRoomUpdateGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("picurl",post.getPicurl());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<LiveRoomUpdateGet>> remoteDataResultCall = remoteApi.updateLive(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    /**
     * 直播封面地址
     */
    public void roomSnap(String roomId,String baseUrl, String url, Callback<RoomPicGet> callBack){
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RoomPicGet> remoteDataResultCall = remoteApi.roomSnap(url,roomId);
        remoteDataResultCall.enqueue(callBack);

    }

    ///homepage5个房间
    public void returnPlayingForfive(String baseUrl,
                           String url, CustomCallBack<RemoteDataResult<List<ReturnFiveGet>>> callback){
        JSONObject jsonObject=new JSONObject();
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<ReturnFiveGet>>> remoteDataResultCall = remoteApi.returnPlayingForfive(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    ///直播列表
    public void returnPlayingList(String baseUrl,
                                     String url, CustomCallBack<RemoteDataResult<List<ReturnFiveGet>>> callback){
        JSONObject jsonObject=new JSONObject();
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<ReturnFiveGet>>> remoteDataResultCall = remoteApi.returnPlayingList(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }


    ////用户进入直播
    public void joinLive(JoinLivePost post, String baseUrl,
                         String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userid",post.getUserid());
            jsonObject.put("playingid",post.getPlayingid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.joinLive(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }


    ///直播间下单
    public void liveOrder(LiveOrderPost post, String baseUrl,
                          String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userid",post.getUserid());
            jsonObject.put("playingid",post.getPlayingid());
            jsonObject.put("address",post.getAddress());
            jsonObject.put("name", post.getName());
            jsonObject.put("phone",post.getPhone());
            jsonObject.put("acceptmoney",post.getAcceptmoney());
            jsonObject.put("reward",post.getReward());
            jsonObject.put("introduce",post.getIntroduce());
            jsonObject.put("couponid",post.getCouponid());
            jsonObject.put("couponpay",post.getCouponpay());
            jsonObject.put("realpayDecimal",post.getRealpayDecimal());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.liveOrder(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }


    ///直播订单列表
    public void liveTakeList(int post,String baseUrl,
                                  String url, CustomCallBack<RemoteDataResult<List<LiveTakeGet>>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<LiveTakeGet>>> remoteDataResultCall = remoteApi.liveTakeList(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }


    ///直播间下单
    public void liveTake(List<Integer> post, String baseUrl,
                          String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for(int i=0;i<post.size();i++){
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id",post.get(i));
                jsonArray.put(i,jsonObject1);
            }
            jsonObject.put("ids",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.liveTake(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    ///钱包
    public void returnWallet(int post, String baseUrl,
                         String url, CustomCallBack<RemoteDataResult<MyWalletGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<MyWalletGet>> remoteDataResultCall = remoteApi.returnWallet(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    ///账单
    public void returnBill(int post, String baseUrl,
                             String url, CustomCallBack<RemoteDataResult<List<BillGet>>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<BillGet>>> remoteDataResultCall = remoteApi.returnBill(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }


    ///对换代金券
    public void getCoupon(GetCouponPost post, String baseUrl,
                          String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userid",post.getUserid());
            jsonObject.put("money",post.getMoney());
            jsonObject.put("mark",post.getMark());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.getCoupon(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    ///代金券
    public void couponList(int post, String baseUrl,
                          String url, CustomCallBack<RemoteDataResult<List<ChitGet>>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<ChitGet>>> remoteDataResultCall = remoteApi.couponList(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }


    ///删除订单
    public void deleteOrder(OrderDetailPost post, String baseUrl,
                           String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("sign",post.getSign());
            jsonObject.put("stu",post.getStu());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.deleteOrder(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    ///取消订单
    public void cancleOrder(OrderDetailPost post, String baseUrl,
                            String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("sign",post.getSign());
            jsonObject.put("stu",post.getStu());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.cancleOrder(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }


    ///是否有人回复
    public void ifReturn(int post, String baseUrl,
                            String url, CustomCallBack<RemoteDataResult<IfReturnGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<IfReturnGet>> remoteDataResultCall = remoteApi.ifReturn(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    //回复列表
    public void myReplyList(int post, String baseUrl,
                         String url, CustomCallBack<RemoteDataResult<List<ReplyGet>>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<ReplyGet>>> remoteDataResultCall = remoteApi.myReplyList(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    //扫描订单
    public void waveList(WavePost post, String baseUrl,
                            String url, CustomCallBack<RemoteDataResult<List<DialogGet>>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("addressN",post.getAddressN());
            jsonObject.put("addressE",post.getAddressE());
            jsonObject.put("dtime",post.getDate());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<DialogGet>>> remoteDataResultCall = remoteApi.waveList(url,jsonObject);
        remoteDataResultCall.enqueue(callback);
    }



    //扫描接单
    public void waveOrder(DialogOrderPost post, String baseUrl,
                          String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("addressN",post.getAddressN());
            jsonObject.put("addressE",post.getAddressE());
            jsonObject.put("addressword",post.getAddressword());
            jsonObject.put("userid",post.getUserid());
            JSONArray jsonArray = new JSONArray();
            for(int i=0;i<post.getIds().size();i++){
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id",post.getIds().get(i));
                jsonArray.put(i,jsonObject1);
            }
            jsonObject.put("ErrandList",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.waveOrder(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    //初始化签到界面
    public void returnSign(int post, String baseUrl,
                          String url, CustomCallBack<RemoteDataResult<SignGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<SignGet>> remoteDataResultCall = remoteApi.returnSign(url,jsonObject);
        remoteDataResultCall.enqueue(callback);

    }

    //签到
    public void sign(int post, String baseUrl,
                           String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.sign(url,jsonObject);
        remoteDataResultCall.enqueue(callback);
    }

    //订单列表
    public void orderList(int post, String baseUrl,
                     String url, CustomCallBack<RemoteDataResult<List<MyOrderGet>>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<List<MyOrderGet>>> remoteDataResultCall = remoteApi.orderList(url,jsonObject);
        remoteDataResultCall.enqueue(callback);
    }


    ///订单详情
    public void orderDetail(OrderDetailPost post, String baseUrl,
                            String url, CustomCallBack<RemoteDataResult<OrderDetailGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("sign",post.getSign());
            jsonObject.put("stu",post.getStu());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<OrderDetailGet>> remoteDataResultCall = remoteApi.orderDetail(url,jsonObject);
        remoteDataResultCall.enqueue(callback);
    }


    //上传地理位置
    public void updateAccepterAddress(MyServicePost post, String baseUrl,
                                      String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("addressN",post.getAddressN());
            jsonObject.put("addressE",post.getAddressE());
            jsonObject.put("addressword",post.getAddressword());

        } catch (JSONException e) {
            e.printStackTrace();
        }
       // Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.updateAccepterAddress(url,jsonObject);
        remoteDataResultCall.enqueue(callback);
    }

    ///获得地理位置
    public void getAccepterAddress(OrderDetailPost post, String baseUrl,
                            String url, CustomCallBack<RemoteDataResult<GetAddressGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("stu",post.getStu());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<GetAddressGet>> remoteDataResultCall = remoteApi.getAccepterAddress(url,jsonObject);
        remoteDataResultCall.enqueue(callback);
    }

    ///返回身份验证
    public void returnIdcard(int post, String baseUrl,
                                   String url, CustomCallBack<RemoteDataResult<IndentityGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<IndentityGet>> remoteDataResultCall = remoteApi.returnIdcard(url,jsonObject);
        remoteDataResultCall.enqueue(callback);
    }

    ///身份验证
    public void addUsersIDcard(IndentityPost post, String baseUrl,
                               String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userid",post.getUserid());
            jsonObject.put("name",post.getName());
            jsonObject.put("number",post.getNumber());
            jsonObject.put("picup",post.getPicup());
            jsonObject.put("picdown",post.getPicdown());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.addUsersIDcard(url,jsonObject);
        remoteDataResultCall.enqueue(callback);
    }

    ///下单订单确认界面
    public void returnDoneErrand(OrderDetailPost post, String baseUrl,
                               String url, CustomCallBack<RemoteDataResult<ConfirmPlaceGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("stu",post.getStu());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<ConfirmPlaceGet>> remoteDataResultCall = remoteApi.returnDoneErrand(url,jsonObject);
        remoteDataResultCall.enqueue(callback);
    }

    ///下单确认
    public void doneErrand(ConfirmPlacePost post, String baseUrl,
                           String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("stu",post.getStu());
            jsonObject.put("credit",post.getCredit());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.doneErrand(url,jsonObject);
        remoteDataResultCall.enqueue(callback);
    }

    ///接单订单确认界面
    public void returnDoneAccept(OrderDetailPost post, String baseUrl,
                                 String url, CustomCallBack<RemoteDataResult<ConfirmTakeGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("stu",post.getStu());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<ConfirmTakeGet>> remoteDataResultCall = remoteApi.returnDoneAccept(url,jsonObject);
        remoteDataResultCall.enqueue(callback);
    }

    ///接单确认
    public void doneAccept(ConfirmTakePost post, String baseUrl,
                           String url, CustomCallBack<RemoteDataResult> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post.getId());
            jsonObject.put("stu",post.getStu());
            jsonObject.put("realprice",post.getRealprice());
            jsonObject.put("picurl",post.getPicurl());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult> remoteDataResultCall = remoteApi.doneAccept(url,jsonObject);
        remoteDataResultCall.enqueue(callback);
    }

    ///返回聊天用户信息
    public void returnOrderinfo(int post, String baseUrl,
                           String url, CustomCallBack<RemoteDataResult<OtherInfoGet>> callback){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---",jsonObject.toString());
        RemoteApi remoteApi = httpHelper.getService(RemoteApi.class,baseUrl);
        Call<RemoteDataResult<OtherInfoGet>> remoteDataResultCall = remoteApi.returnOrderinfo(url,jsonObject);
        remoteDataResultCall.enqueue(callback);
    }
}







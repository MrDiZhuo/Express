package com.zwd.express.internet;

import com.zwd.express.Context.bill.Module.BillGet;
import com.zwd.express.Context.confirmPlace.Module.ConfirmPlaceGet;
import com.zwd.express.Context.confirmTake.Module.ConfirmTakeGet;
import com.zwd.express.Context.homePage.Module.ReturnFiveGet;
import com.zwd.express.Context.homePage.Module.DialogGet;
import com.zwd.express.Context.identity.Module.IndentityGet;
import com.zwd.express.Context.liveList.Module.CreateRoomGet;
import com.zwd.express.Context.liveList.Module.CreateRoomToServerGet;
import com.zwd.express.Context.liveList.Module.RoomPublishGet;
import com.zwd.express.Context.liveRoom.Module.LiveRoomUpdateGet;
import com.zwd.express.Context.liveRoom.Module.RoomPicGet;
import com.zwd.express.Context.liveTake.Module.LiveTakeGet;
import com.zwd.express.Context.login.Module.LoginGet;
import com.zwd.express.Context.myOrder.Module.MyOrderGet;
import com.zwd.express.Context.myWallet.Module.ChitGet;
import com.zwd.express.Context.myWallet.Module.MyWalletGet;
import com.zwd.express.Context.orderDetail.Module.GetAddressGet;
import com.zwd.express.Context.orderDetail.Module.OrderDetailGet;
import com.zwd.express.Context.otherInfo.Module.OtherInfoGet;
import com.zwd.express.Context.register.Module.RegisterGet;
import com.zwd.express.Context.register.Module.VertifyGet;
import com.zwd.express.Context.roast.Module.IfReturnGet;
import com.zwd.express.Context.roast.Module.ReplyGet;
import com.zwd.express.Context.roast.Module.RoastListGet;
import com.zwd.express.Context.roastDetail.Module.CommentGet;
import com.zwd.express.Context.roastDetail.Module.RoastDetailGet;
import com.zwd.express.Context.sign.Module.SignGet;

import org.json.JSONObject;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by asus-pc on 2017/4/9.
 */

public interface RemoteApi {
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<LoginGet>>login(@Url String url, @Field("")
            JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<RegisterGet>>register(@Url String url, @Field("")
            JSONObject jsonObject);
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>forgetPsw(@Url String url, @Field("")
            JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>editSelf(@Url String url, @Field("")
            JSONObject jsonObject);

    //修改头像
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>editHead(@Url String url, @Field("")
            JSONObject jsonObject);


    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>place(@Url String url, @Field("")
                    JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>publishRoast(@Url String url, @Field("")
            JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<RoastListGet>>>roastList(@Url String url, @Field("")
            JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>Prove(@Url String url, @Field("")
            JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<RoastDetailGet>>roastDetail(@Url String url, @Field("")
            JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>reply(@Url String url, @Field("")
            JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<CommentGet>>commentDetail(@Url String url, @Field("")
            JSONObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>commentReply(@Url String url, @Field("")
            JSONObject jsonObject);

    ///验证码
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<VertifyGet>>returncode(@Url String url, @Field("")
            JSONObject jsonObject);

    ///七牛token存图片
    @GET
    Call<String>GetQiniuToken(@Url String url);

    ///创建直播空间
    @FormUrlEncoded
    @POST
    Call<CreateRoomGet>createRoom(@Url String url, @Field("")JSONObject jsonObject);

    ///直播推流地址
    @FormUrlEncoded
    @POST
    Call<RoomPublishGet>roomPublish(@Url String url, @Field("")String roomId);

    ///直播拉流地址
    @FormUrlEncoded
    @POST
    Call<RoomPublishGet>roomPlay(@Url String url, @Field("")String roomId);

    ///创建直播间to server
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<CreateRoomToServerGet>>createRoomToServer(@Url String url, @Field("")
            JSONObject jsonObject);

    ////关闭直播
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>closeLive(@Url String url, @Field("")
            JSONObject jsonObject);

    ////直播更新信息
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<LiveRoomUpdateGet>>updateLive(@Url String url, @Field("")
            JSONObject jsonObject);

    ///直播封面地址
    @FormUrlEncoded
    @POST
    Call<RoomPicGet>roomSnap(@Url String url, @Field("")String roomId);


    ////homepage5个房间
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<ReturnFiveGet>>>returnPlayingForfive(@Url String url, @Field("")
            JSONObject jsonObject);

    ////直播列表
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<ReturnFiveGet>>>returnPlayingList(@Url String url, @Field("")
            JSONObject jsonObject);

    ////用户进入直播
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>joinLive(@Url String url, @Field("")
            JSONObject jsonObject);

    ///直播间下单
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>liveOrder(@Url String url, @Field("")
            JSONObject jsonObject);

    ///直播订单列表
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<LiveTakeGet>>>liveTakeList(@Url String url, @Field("")
            JSONObject jsonObject);

    ///直播间接单
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>liveTake(@Url String url, @Field("")
            JSONObject jsonObject);

    ///钱包
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<MyWalletGet>>returnWallet(@Url String url, @Field("")
            JSONObject jsonObject);

    ///账单
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<BillGet>>>returnBill(@Url String url, @Field("")
            JSONObject jsonObject);


    ///对换代金券
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>getCoupon(@Url String url, @Field("")
            JSONObject jsonObject);

    ///代金券
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<ChitGet>>>couponList(@Url String url, @Field("")
            JSONObject jsonObject);

    ///删除订单
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>deleteOrder(@Url String url, @Field("")
            JSONObject jsonObject);

    ///取消订单
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>cancleOrder(@Url String url, @Field("")
            JSONObject jsonObject);


    ///是否有人回复
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<IfReturnGet>>ifReturn(@Url String url, @Field("")
            JSONObject jsonObject);

    //回复列表
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<ReplyGet>>>myReplyList(@Url String url, @Field("")
            JSONObject jsonObject);

    //扫描订单
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<DialogGet>>>waveList(@Url String url, @Field("")
            JSONObject jsonObject);

    //扫描订单
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>waveOrder(@Url String url, @Field("")
            JSONObject jsonObject);

    //初始化签到界面
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<SignGet>>returnSign(@Url String url, @Field("")
            JSONObject jsonObject);

    //签到
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>sign(@Url String url, @Field("")
            JSONObject jsonObject);

    //订单列表
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<List<MyOrderGet>>>orderList(@Url String url, @Field("")
            JSONObject jsonObject);

    ///订单详情
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<OrderDetailGet>>orderDetail(@Url String url, @Field("")
            JSONObject jsonObject);

    //上传地理位置
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>updateAccepterAddress(@Url String url, @Field("")
            JSONObject jsonObject);

    ///获得地理位置
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<GetAddressGet>>getAccepterAddress(@Url String url, @Field("")
            JSONObject jsonObject);


    ///返回身份验证
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<IndentityGet>>returnIdcard(@Url String url, @Field("")
            JSONObject jsonObject);


    ///身份验证
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>addUsersIDcard(@Url String url, @Field("")
            JSONObject jsonObject);


    ///下单订单确认界面
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<ConfirmPlaceGet>>returnDoneErrand(@Url String url, @Field("")
            JSONObject jsonObject);

    ///下单确认
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>doneErrand(@Url String url, @Field("")
            JSONObject jsonObject);

    ///接单订单确认界面
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<ConfirmTakeGet>>returnDoneAccept(@Url String url, @Field("")
            JSONObject jsonObject);


    ///接单确认
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult>doneAccept(@Url String url, @Field("")
            JSONObject jsonObject);

    ///返回聊天用户信息
    @FormUrlEncoded
    @POST
    Call<RemoteDataResult<OtherInfoGet>>returnOrderinfo(@Url String url, @Field("")
            JSONObject jsonObject);
}

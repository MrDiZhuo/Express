package com.zwd.express.internet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 自定义统一接口回调处理类
 * Created by XRY on 2016/10/22.
*/

public abstract class CustomCallBack<T extends  RemoteDataResult> implements Callback<T> {
    private static final String NET_ERROR = "网络连接异常";
    private static final String NET_PERMISSION_ERROR = "网络权限异常";
    private static final String NULL_PARAMENT = "未收到参数，需要传参但未接受到任何参数";
    private static final String SUCCESS = "成功调取接口";
    private static final String PARAMENT_ERROR = "参数错误，参数值为空";
    private static final String PARAMENT_FORMAT_ERROR = "参数格式错误，不是json数据或者是其他要求数据";
    private static final String PARAMENT_LACK = "缺少特定名称的参数";
    private static final String DATA_NOT_FOUND = "缺少必须参数,缺少特定名称的参数";
    private static final String ADMIN_NOT_FOUND = "该用户不存在";
    private static final String PSW_ERROR = "登录密码错误";
    private static final String OBJECT_FORBIDDEN = "该对象已被禁用";
    private static final String KEYWORD_OCCUPY = "关键字段已被占用";
    private static final String OPERATION_FAIL = "操作失败";
    private static final String NOT_LOGIN = "未登录";
    private static final String SESSION_OVERDUE = "session过期";
    private static final String ADMIN_CROWD = "该用户被挤下线";
    private static final String SERVER_TINE_OUT = "服务器响应超时";
    private static final String SERVER_BUSY = "服务器忙";
    private static final String ILLEGAL_OPERATION = "非法操作";
    private static final String NO_OPERATION_ACCESS = "没有操作权限";
    private static final String RONGYUN_ERROR = "融云服务器异常";
    private static final String JIGUANG_ERROR = "极光服务器异常";
    private static final String DAYU_ERROR = "大于服务器异常";
    private static final String VERIFICATION_CODE_ERROR = "短信验证码不正确";
    private static final String OTHER_CASE = "其他情况";
    private static final String NULL_RETURN = "返回值为空";

    public abstract void onSuccess(Response<T> response);
    public abstract void onFail(String message);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response != null) {
            //如果返回为列表
            if (response.body().getData() instanceof List) {
                switch (response.body().getResultCode()){
                    case 101:
                        onSuccess(response);
                        break;
                    case 102:
                        onSuccess(response);
                        break;
                    case 103:
                        onFail(OTHER_CASE);
                }
            }
            //我为啥不用switch。。
            if (response.body().getResultCode() == 0) {
                onFail(NULL_PARAMENT);
            }else if (response.body().getResultCode() == 1){
                onSuccess(response);
            }else if (response.body().getResultCode() == 2){
                onFail(PARAMENT_ERROR);
            }else if (response.body().getResultCode() == 201){
                onFail(PARAMENT_FORMAT_ERROR);
            }else if (response.body().getResultCode() == 202){
                onFail(PARAMENT_LACK);
            }else if (response.body().getResultCode() == 3){
                onFail(DATA_NOT_FOUND);
            }else if (response.body().getResultCode() == 301){
                onFail(ADMIN_NOT_FOUND);
            }else if (response.body().getResultCode() == 302){
                onFail(PSW_ERROR);
            }else if (response.body().getResultCode() == 6){
                onFail(OBJECT_FORBIDDEN);
            }else if (response.body().getResultCode() == 7){
                onFail(KEYWORD_OCCUPY);
            }else if (response.body().getResultCode() == 9){
                onFail(OPERATION_FAIL);
            }else if (response.body().getResultCode() == 10){
                onFail(NOT_LOGIN);
            }else if (response.body().getResultCode() == 11){
                onFail(SESSION_OVERDUE);
            }else if (response.body().getResultCode() == 12){
                onFail(ADMIN_CROWD);
            }else if (response.body().getResultCode() == 13){
                onFail(SERVER_TINE_OUT);
            }else if (response.body().getResultCode() == 14){
                onFail(SERVER_BUSY);
            }else if (response.body().getResultCode() == 18){
                onFail(ILLEGAL_OPERATION);
            }else if (response.body().getResultCode() == 19){
                onFail(NO_OPERATION_ACCESS);
            }else if (response.body().getResultCode() == 21){
                onFail(RONGYUN_ERROR);
            }else if (response.body().getResultCode() == 22){
                onFail(JIGUANG_ERROR);
            }else if (response.body().getResultCode() == 23){
                onFail(DAYU_ERROR);
            }else if (response.body().getResultCode() == 20){
                onFail(VERIFICATION_CODE_ERROR);
            }
        }else {
            onFail(NULL_RETURN);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFail(t.getMessage());
    }
}

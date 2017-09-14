package com.zwd.express.Context.login.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.zwd.express.Context.forgetPsw.View.ForgetPswActivity;
import com.zwd.express.Context.homePage.View.HomePageActivity;
import com.zwd.express.Context.login.Module.LoginGet;
import com.zwd.express.Context.login.Module.LoginPost;
import com.zwd.express.Context.register.View.RegisterActivity;
import com.zwd.express.R;
import com.zwd.express.Widget02.live.LiveKit;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.util.HttpUtil;
import com.zwd.express.util.ToastUtil;
import com.zwd.express.util.isPhoneUtil;

import org.json.JSONException;
import org.json.JSONObject;


import javax.crypto.Mac;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

public class LoginActivity extends BaseActivity implements RongIM.UserInfoProvider{
    private static final String TAG = "LoginActivity";
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.checkbox)
    AppCompatCheckBox checkbox;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "User_CheckLogin.aspx";

    private SharedPreferences sharedPreferences,sharedPreferences02;
    private String oldUsername;

    ////网页生成的


   // private String Qiniu_token="qTd9ln0QzLbE_GsY0iwQMESzhMiYLYttOwFJ9Ekr:pMgggVk7IkOfsTt3JDJzcV5JN6s=:eyJzY29wZSI6InN1aXNob3VleHByZXNzIiwiZGVhZGxpbmUiOjE2MDA3OTQyNDl9";
    private UserInfo userInfo;


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        LiveKit.init(this);
        RongIM.setUserInfoProvider(this,true);
        //默认记住用户名
        sharedPreferences02 = this.getSharedPreferences("test",Context.MODE_PRIVATE);
        sharedPreferences = this.getSharedPreferences("userInfo",Context.MODE_WORLD_READABLE);
        username.setText(sharedPreferences.getString("username",""));
        if(sharedPreferences.getBoolean("ISCHECKED",false)){
            password.setText(sharedPreferences.getString("password",""));
            checkbox.setChecked(true);
        }
        oldUsername = username.getText().toString();
        username.addTextChangedListener(new EditChangeListener());
    }


    @OnClick({R.id.login_login, R.id.forgetPsw, R.id.register})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.login_login:
                //btn_qiniu();
                url_login();
                break;
            case R.id.forgetPsw:
                goActivity(ForgetPswActivity.class);
                break;
            case R.id.register:
                goActivity(RegisterActivity.class);
                break;
        }
    }

    private void url_login() {
        if (check()) {
            progressBar.setVisibility(View.VISIBLE);
            LoginPost post = new LoginPost(username.getText().toString(),
                    password.getText().toString());
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.login(post, baseUrl, url, new
                    CustomCallBack<RemoteDataResult<LoginGet>>() {

                @Override
                public void onSuccess(Response<RemoteDataResult<LoginGet>>
                                              response) {
                    LoginGet get = response.body().getData();

                    Bundle bundle = new Bundle();
                    bundle.putInt("id", get.getId());
                    bundle.putInt("userstatus",get.getUserstatus());
                    bundle.putString("sex",get.getSex());
                    bundle.putString("Qiniu_token",get.getQiniutoken());
                    bundle.putString("heading",get.getHeading());
                    bundle.putString("username",get.getUsername());
                    bundle.putString("name",get.getName());
                    bundle.putInt("credit",get.getCredit());
                    bundle.putInt("mark",get.getMark());

                    ///融云连接
                    Uri uri;
                    if(get.getHeading()!=null&&get.getHeading().length()>0){
                        uri = Uri.parse(get.getHeading());
                    }else {
                        uri = null;
                    }
                    Log.d("-----head",get.getHeading());
                    userInfo = new UserInfo(String.valueOf(get.getId()),get.getName(),uri);

                    connect(get.getToken());
                   // connect("+fixT9Wzid/iObGHadL4KLmpegXq3PPhDElKgQwe6IuxpEWm1akxVgPazxWlw2OqWKbtTPfGDsZaMebJ9N0lhw==");
                    //保存用户名
                    SharedPreferences.Editor  editor = sharedPreferences.edit();
                    editor.putString("username",username.getText().toString());
                    //记住密码
                    if (checkbox.isChecked()){
                        editor.putString("password",password.getText().toString());
                        editor.putBoolean("ISCHECKED",true);
                        editor.putInt("id",get.getId());
                    }
                    editor.commit();
                    SharedPreferences.Editor  editor02 = sharedPreferences02.edit();
                    editor02.putString("DEMO_TOKEN",get.getToken());
                    editor02.commit();
                    goActivity(HomePageActivity.class, bundle);
                }

                @Override
                public void onFail(String message) {
                    progressBar.setVisibility(View.GONE);
                    ToastUtil.showShort(LoginActivity.this, message);
                    Log.d("fail", message);
                }
            });
        }
    }

    /////链接融云
    private void connect(String token){
        LiveKit.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.d(TAG, "connect onTokenIncorrect");
                // 检查appKey 与token是否匹配.
            }
            @Override
            public void onSuccess(String userId) {
                LiveKit.setCurrentUser(userInfo);
                Log.d("-----userId",userId);
            }
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d(TAG, "connect onError = " + errorCode);
                // 根据errorCode 检查原因.
            }
        });
    }
    class EditChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int
                i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1,
                                  int i2) {
            if (charSequence.toString().length() > 0&&charSequence.toString().equals(oldUsername)) {
                checkbox.setChecked(true);
            }else {
                checkbox.setChecked(false);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {}
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

    private boolean check() {
        if (username.getText().toString().isEmpty()) {
            ToastUtil.showShort(this, "用户名不能为空！");
            return false;
        } else {
            if (password.getText().toString().isEmpty()) {
                ToastUtil.showShort(this, "密码不能为空！");
                return false;
            } else
                return true;
        }
    }


    @Override
    public UserInfo getUserInfo(String s) {
        return userInfo;
    }
}

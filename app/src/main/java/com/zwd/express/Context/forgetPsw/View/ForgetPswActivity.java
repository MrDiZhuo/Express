package com.zwd.express.Context.forgetPsw.View;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwd.express.Context.login.Module.LoginPost;
import com.zwd.express.Context.register.Module.VertifyGet;
import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;
import com.zwd.express.util.CountDownUtil;
import com.zwd.express.util.isPhoneUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPswActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.img_verify)
    ImageView img_verify;
    @BindView(R.id.ll_verify)
    LinearLayout ll_verify;
    @BindView(R.id.verify)
    EditText verify;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.newPSW)
    EditText newPSW;
    @BindView(R.id.btn)
    TextView btn;
    @BindView(R.id.inform)
    TextView inform;
    @BindView(R.id.txt_false)
    TextView txt_false;

    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "User_ReturnPassword.aspx";
    private String url_vertify="User_Aliyun.aspx";

    private boolean flag_verify = false;
    private boolean flag_password = false;

    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    private String code ;

    @Override
    protected int getContentView() {
        return R.layout.activity_forget_psw;
    }

    @Override
    protected void initViews() {
        initActionBar(toolbar);
        phone.addTextChangedListener(new PhoneEditChangeListener());
        verify.addTextChangedListener(new VerifyEditChangeListener());
        password.addTextChangedListener(new OldPswEditChangeListener());
        newPSW.addTextChangedListener(new NewPswEditChangeListener());

    }

    @OnClick({R.id.btn,R.id.img_verify})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.btn:
                Url_forgetPsw();
                break;
            case R.id.img_verify:
                time.setVisibility(View.VISIBLE);
                ll_verify.setVisibility(View.VISIBLE);
                Log.d("-----","btn");
                url_code();
                break;
        }

    }
    public void url_code(){

        CountDownUtil countDown = new CountDownUtil(60000,
                1000, time, img_verify);
        countDown.start();
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.returncode(phone.getText().toString(), baseUrl, url_vertify, new CustomCallBack<RemoteDataResult<VertifyGet>>() {


            @Override
            public void onSuccess(Response<RemoteDataResult<VertifyGet>> response) {
                VertifyGet get = response.body().getData();
                code = get.getRandkey();
            }

            @Override
            public void onFail(String message) {
                Log.d("---fail",message);
            }
        });
    }

    private void Url_forgetPsw() {
        LoginPost post = new LoginPost(phone.getText().toString(), newPSW
                .getText().toString());
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.forgetPsw(post, baseUrl, url, new
                CustomCallBack<RemoteDataResult>() {

            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                builder = new AlertDialog.Builder(ForgetPswActivity.this);
                View view1 = LayoutInflater.from(ForgetPswActivity.this)
                        .inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        finish();
                    }
                });
                TextView txt = (TextView) view1.findViewById(R.id.txt);
                txt.setText("重置成功!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onFail(String message) {
                builder = new AlertDialog.Builder(ForgetPswActivity.this);
                View view1 = LayoutInflater.from(ForgetPswActivity.this)
                        .inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                TextView txt = (TextView) view1.findViewById(R.id.txt);
                txt.setText("重置失败!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
            }
        });
    }


    class PhoneEditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int
                i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1,
                                  int i2) {
            if (charSequence.toString().length() == 0) {
                img_verify.setImageResource(R.mipmap.icon_unverify);
                img_verify.setClickable(false);
                inform.setVisibility(View.GONE);
            } else {
                boolean flag = isPhoneUtil.isPhone(charSequence.toString().trim());
                if(flag){
                    inform.setVisibility(View.GONE);
                    img_verify.setImageResource(R.mipmap.icon_verify);
                    img_verify.setClickable(true);

                }else {
                    img_verify.setImageResource(R.mipmap.icon_unverify);
                    img_verify.setClickable(false);
                    inform.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }


    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("忘记密码");
        toolbar.hideRight();
        toolbar.setBack(R.mipmap.bg_nomal_tool);
    }

    class VerifyEditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int
                i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1,
                                  int i2) {
            if (charSequence.toString().length() > 0) {
                if(charSequence.toString().equals(code)){
                    txt_false.setVisibility(View.GONE);
                    flag_verify = true;
                }else {
                    txt_false.setVisibility(View.VISIBLE);
                    flag_verify = false;
                }
            } else {
                txt_false.setVisibility(View.GONE);
                flag_verify = false;
            }
            check(flag_verify, flag_password);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    class OldPswEditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int
                i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1,
                                  int i2) {
            if (charSequence.toString().length() > 0
                    && charSequence.toString().equals(newPSW.getText()
                    .toString())) {
                flag_password = true;
            } else flag_password = false;

            check(flag_verify, flag_password);

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    class NewPswEditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int
                i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1,
                                  int i2) {
            if (charSequence.toString().length() > 0
                    && charSequence.toString().equals(password.getText()
                    .toString())) {
                flag_password = true;
            } else flag_password = false;

            check(flag_verify, flag_password);

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private void check(boolean flag_verify, boolean flag_password) {
        if (flag_verify && flag_password) {
            btn.setBackgroundResource(R.mipmap.bg_btn_yello);
            btn.setTextColor(getResources().getColor(R.color.white));
            btn.setClickable(true);
        } else {
            btn.setBackgroundResource(R.mipmap.btn_gray);
            btn.setTextColor(getResources().getColor(R.color.gray_12));
            btn.setClickable(false);
        }

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

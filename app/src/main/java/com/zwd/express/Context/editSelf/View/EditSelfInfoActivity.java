package com.zwd.express.Context.editSelf.View;

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

import com.zwd.express.Context.editSelf.Module.EditSelfPost;
import com.zwd.express.Context.forgetPsw.View.ForgetPswActivity;
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

public class EditSelfInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.img_verify)
    ImageView img_verify;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.ll_verify)
    LinearLayout ll_verify;
    @BindView(R.id.btn)
    TextView btn;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.verify)
    EditText verify;
    @BindView(R.id.sex)
    EditText sex;
    @BindView(R.id.inform)
    TextView inform;
    @BindView(R.id.inform_sex)
    TextView inform_sex;
    @BindView(R.id.txt_false)
    TextView txt_false;

    private String oldName;
    private String oldPhone;
    private String oldSex;
    private boolean flag_name = false;
    private boolean flag_verify = false;
    private boolean flag_sex = false;
    private Bundle bundle;
    private int id;
    private String str_name;
    private String str_username;
    private String str_sex;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "User_EditUserInfo.aspx";
    private String url_vertify="User_Aliyun.aspx";
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    private String code ;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_self_info;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        str_name = bundle.getString("name");
        str_username = bundle.getString("username");
        str_sex = bundle.getString("sex");
        initActionBar(toolbar);
        initView();
        initVerify();
    }

    private void initView() {
        name.setText(str_name);
        phone.setText(str_username);
        sex.setText(str_sex);
    }

    private void initVerify() {
        oldName = name.getText().toString();
        oldSex = sex.getText().toString();
        oldPhone = phone.getText().toString();
        phone.addTextChangedListener(new EditChangeListener());
        name.addTextChangedListener(new NameEditChangeListener());
        sex.addTextChangedListener(new SexEditChangeListener());
    }
    @OnClick({R.id.btn,R.id.img_verify})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.btn:
                Url_edit();
                break;
            case R.id.img_verify:
                time.setVisibility(View.VISIBLE);
                ll_verify.setVisibility(View.VISIBLE);
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
    private void Url_edit(){
        EditSelfPost post = new EditSelfPost(id,name.getText().toString()
                ,phone.getText().toString(),sex.getText().toString());
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.editSelf(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {

            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                builder = new AlertDialog.Builder(EditSelfInfoActivity.this);
                View view1 = LayoutInflater.from(EditSelfInfoActivity.this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        finish();
                    }
                });
                TextView txt = (TextView)view1.findViewById(R.id.txt) ;
                txt.setText("修改成功!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onFail(String message) {
                builder = new AlertDialog.Builder(EditSelfInfoActivity.this);
                View view1 = LayoutInflater.from(EditSelfInfoActivity.this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                TextView txt = (TextView)view1.findViewById(R.id.txt) ;
                txt.setText("修改失败!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
                Log.d("---",message);
            }
        });
    }
    class NameEditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int
                i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1,
                                  int i2) {
            if (charSequence.toString().length() <0||charSequence.toString().equals(oldName)) {
                flag_name = false;
            } else {
                flag_name = true;
            }
            check( flag_name,  flag_verify, flag_sex);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
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
            check( flag_name,  flag_verify, flag_sex);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
    class SexEditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int
                i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1,
                                  int i2) {
            if (charSequence.toString().length() > 0) {
                if(charSequence.toString().equals(oldSex)){
                    flag_sex=false;
                }else {
                    boolean flag = isPhoneUtil.isSex(charSequence.toString().trim());
                    if(flag){
                        inform_sex.setVisibility(View.GONE);
                        flag_sex = true;
                    }else {
                        inform_sex.setVisibility(View.VISIBLE);
                        flag_sex=false;
                    }
                }
            } else {
                inform_sex.setVisibility(View.GONE);
                flag_sex=false;
            }
            check( flag_name,  flag_verify, flag_sex);

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
    class EditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int
                i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1,
                                  int i2) {
            if(charSequence.toString().length()>0){
                if (charSequence.toString().equals(oldPhone)) {
                    img_verify.setImageResource(R.mipmap.icon_unverify);
                    img_verify.setClickable(false);
                    inform.setVisibility(View.GONE);
                } else {
                    boolean flag = isPhoneUtil.isPhone(charSequence.toString().trim());
                    if(flag){
                        inform.setVisibility(View.GONE);
                        img_verify.setImageResource(R.mipmap.icon_verify);
                        img_verify.setClickable(true);
                        verify.addTextChangedListener(new VerifyEditChangeListener());
                    }else {
                        inform.setVisibility(View.VISIBLE);
                    }
                }
            }else {
                inform.setVisibility(View.GONE);
                img_verify.setImageResource(R.mipmap.icon_unverify);
                img_verify.setClickable(false);
                flag_verify=false;flag_sex=false;flag_name =false;
            }
            check( flag_name,  flag_verify, flag_sex);

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private void check(boolean flag_name, boolean flag_verify, boolean
            flag_sex) {
        if (flag_verify || flag_name || flag_sex) {
            btn.setBackgroundResource(R.mipmap.bg_btn_yello);
            btn.setTextColor(getResources().getColor(R.color.white));
            btn.setClickable(true);
        } else {
            btn.setBackgroundResource(R.mipmap.btn_gray);
            btn.setTextColor(getResources().getColor(R.color.gray_12));
            btn.setClickable(false);
        }

    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("修改个人资料");
        toolbar.hideRight();
        toolbar.setBack(R.mipmap.bg_nomal_tool);
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

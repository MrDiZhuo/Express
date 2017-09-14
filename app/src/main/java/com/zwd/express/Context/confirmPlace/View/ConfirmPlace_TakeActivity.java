package com.zwd.express.Context.confirmPlace.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.express.Context.confirmPlace.Module.ConfirmPlaceGet;
import com.zwd.express.Context.confirmPlace.Module.ConfirmPlacePost;
import com.zwd.express.Context.orderDetail.Module.OrderDetailPost;
import com.zwd.express.Context.orderDetail.View.OrderDetail_UnfinishActivity;
import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.base.BaseAppManager;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

public class ConfirmPlace_TakeActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.evaluate_01)
    ImageView evaluate01;
    @BindView(R.id.evaluate_02)
    ImageView evaluate02;
    @BindView(R.id.evaluate_03)
    ImageView evaluate03;
    @BindView(R.id.evaluate_04)
    ImageView evaluate04;
    @BindView(R.id.evaluate_05)
    ImageView evaluate05;
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_reward)
    TextView txtReward;
    @BindView(R.id.btn)
    TextView btn;

    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Accept_ReturnDoneErrand.aspx";
    private String url_confirm = "Accept_DoneErrand.aspx";
    private int id;
    private int stu;
    private Bundle bundle;
    private Intent intent;
    private boolean flag_evaluate = false;
    private int credit;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    List<ImageView> img_evaluate = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_confirm_place_take;
    }

    @Override
    protected void initViews() {
        intent = getIntent();
        bundle = intent.getExtras();
        id = bundle.getInt("id");
        stu = bundle.getInt("stu");
        initActionBar(toolbar);
        initEvaluate();
        Url_confirmdetail();
    }

    private void Url_confirmdetail() {
        OrderDetailPost post = new OrderDetailPost(stu, id);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.returnDoneErrand(post, baseUrl, url, new
                CustomCallBack<RemoteDataResult<ConfirmPlaceGet>>() {

                    @Override
                    public void onSuccess
                            (Response<RemoteDataResult<ConfirmPlaceGet>>
                                     response) {
                        ConfirmPlaceGet get = response.body().getData();
                        initView(get);
                    }

                    @Override
                    public void onFail(String message) {
                        Log.d("---fail", message);
                    }
                });
    }

    private void initView(ConfirmPlaceGet get) {
        if (get.getHeading() != null && get.getHeading().length() > 0) {
            Glide.with(this).load(get.getHeading()).into(head);
        } else {
            Glide.with(this).load(R.mipmap.pic_head).into(head);
        }
        txtName.setText(get.getName());
        txtPhone.setText(get.getUsernane());
        txtReward.setText("打赏金额:¥" + get.getReward());

    }

    private void initEvaluate() {
        img_evaluate.add(evaluate01);
        img_evaluate.add(evaluate02);
        img_evaluate.add(evaluate03);
        img_evaluate.add(evaluate04);
        img_evaluate.add(evaluate05);
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setTitle("订单确认");
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setBack(R.mipmap.bg_nomal_tool);
        toolbar.hideRight();
    }

    @OnClick({R.id.evaluate_01, R.id.evaluate_02, R.id.evaluate_03, R.id
            .evaluate_04, R.id.evaluate_05,R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.evaluate_01:
                evaluate01.setImageResource(R.mipmap.icon_good);
                for (int i = 1; i < 5; i++) {
                    credit-=20;
                    img_evaluate.get(i).setImageResource(R.mipmap.icon_bad);
                }
                flag_evaluate = true;
                check(flag_evaluate);
                break;
            case R.id.evaluate_02:
                for (int i = 0; i < 2; i++) {
                    credit+=20;
                    img_evaluate.get(i).setImageResource(R.mipmap.icon_good);
                }
                for (int i = 2; i < 5; i++) {
                    credit-=20;
                    img_evaluate.get(i).setImageResource(R.mipmap.icon_bad);
                }
                flag_evaluate = true;
                check(flag_evaluate);
                break;
            case R.id.evaluate_03:
                for (int i = 0; i < 3; i++) {
                    credit+=20;
                    img_evaluate.get(i).setImageResource(R.mipmap.icon_good);
                }
                for (int i = 3; i < 5; i++) {
                    credit-=20;
                    img_evaluate.get(i).setImageResource(R.mipmap.icon_bad);
                }
                flag_evaluate = true;
                check(flag_evaluate);
                break;
            case R.id.evaluate_04:
                for (int i = 0; i < 4; i++) {
                    credit+=20;
                    img_evaluate.get(i).setImageResource(R.mipmap.icon_good);
                }
                for (int i = 4; i < 5; i++) {
                    credit-=20;
                    img_evaluate.get(i).setImageResource(R.mipmap.icon_bad);
                }
                flag_evaluate = true;
                check(flag_evaluate);
                break;
            case R.id.evaluate_05:
                for (int i = 0; i < 5; i++) {
                    credit+=20;
                    img_evaluate.get(i).setImageResource(R.mipmap.icon_good);
                }
                flag_evaluate = true;
                check(flag_evaluate);
                break;
            case R.id.btn:
                Url_confrim();
                break;
        }
    }

    private void Url_confrim(){
        ConfirmPlacePost post = new ConfirmPlacePost(stu,id,credit);
        RemoteOptionIml remoteOptionIml= new RemoteOptionIml();
        remoteOptionIml.doneErrand(post, baseUrl, url_confirm, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                builder = new AlertDialog.Builder(ConfirmPlace_TakeActivity.this);
                View view1 = LayoutInflater.from(ConfirmPlace_TakeActivity.this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("flag",true);
                        intent.putExtras(bundle);
                        ConfirmPlace_TakeActivity.this.setResult(0,intent);
                        ConfirmPlace_TakeActivity.this.finish();


                    }
                });
                TextView txt = (TextView)view1.findViewById(R.id.txt) ;
                txt.setText("确认成功!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onFail(String message) {
                builder = new AlertDialog.Builder(ConfirmPlace_TakeActivity.this);
                View view1 = LayoutInflater.from(ConfirmPlace_TakeActivity.this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                TextView txt = (TextView)view1.findViewById(R.id.txt) ;
                txt.setText("确认失败!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
                Log.d("---",message);
            }
        });
    }
    private void check(boolean flag_evaluate) {
        if (flag_evaluate) {
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
     *键盘返回
     */
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("flag",false);
            intent.putExtras(bundle);
            ConfirmPlace_TakeActivity.this.setResult(0,intent);
            ConfirmPlace_TakeActivity.this.finish();
        }
        return true;
    }
}

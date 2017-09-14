package com.zwd.express.Context.confirmPlace.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loveplusplus.demo.image.ImagePagerActivity;
import com.zwd.express.Context.confirmPlace.Module.ConfirmPlaceGet;
import com.zwd.express.Context.confirmPlace.Module.ConfirmPlacePost;
import com.zwd.express.Context.editSelf.View.EditSelfInfoActivity;
import com.zwd.express.Context.orderDetail.Module.OrderDetailPost;
import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
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

public class ConfirmPlace_BuyActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.pic)
    ImageView pic;
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
    @BindView(R.id.btn)
    TextView btn;
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_reward)
    TextView txtReward;
    @BindView(R.id.txt_realprice)
    TextView txtRealprice;
    @BindView(R.id.ll_no)
    LinearLayout llNo;

    private int id;
    private int stu;
    private Bundle bundle;
    private Intent intent;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Accept_ReturnDoneErrand.aspx";
    private String url_confirm = "Accept_DoneErrand.aspx";

    private String[] urls = {"http://img2.xiukee" +
            ".com/upload/2016/9/21/5937907645.jpg@100q.jpg"};


    List<ImageView> img_evaluate = new ArrayList<>();

    private boolean flag_realprice = false;
    private boolean flag_pic = false;
    private boolean flag_evaluate = false;
    private int credit;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    @Override
    protected int getContentView() {
        return R.layout.activity_confirm_place_buy;
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
        // Glide.with(this).load(urls[0]).into(pic);
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
        txtPrice.setText("物品价格:¥" + get.getPrice());
        txtReward.setText("打赏金额:¥" + get.getReward());
        if(get.getRealprice()!=0.0){
            txtRealprice.setText("实际消费:¥" + get.getRealprice());
            flag_realprice = true;
        }else {
            flag_realprice=false;
        }
        if(get.getPicurl()!=null&&get.getPicurl().length()>0){
            llNo.setVisibility(View.GONE);
            Glide.with(this).load(get.getPicurl()).into(pic);
            flag_pic = true;
        }else {
            pic.setVisibility(View.GONE);
            flag_pic = false;
        }
        check(flag_realprice,flag_pic,flag_evaluate);
    }

    private void check(boolean flag_realprice, boolean flag_pic, boolean
            flag_evaluate) {
        if (flag_realprice&&flag_pic&&flag_evaluate){
            btn.setBackgroundResource(R.mipmap.bg_btn_yello);
            btn.setTextColor(getResources().getColor(R.color.white));
            btn.setClickable(true);
        }else {
            btn.setBackgroundResource(R.mipmap.btn_gray);
            btn.setTextColor(getResources().getColor(R.color.gray_12));
            btn.setClickable(false);
        }
    }

    private void initEvaluate() {
        img_evaluate.add(evaluate01);
        img_evaluate.add(evaluate02);
        img_evaluate.add(evaluate03);
        img_evaluate.add(evaluate04);
        img_evaluate.add(evaluate05);
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setBack(R.mipmap.bg_nomal_tool);
        toolbar.setTitle("订单确认");
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.hideRight();
    }

    @OnClick(R.id.pic)
    public void onViewClicked() {
        imageBrower(0, urls);
    }

    private void imageBrower(int position, String[] urls) {
        Intent intent = new Intent(this, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        startActivity(intent);
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
                check(flag_realprice,flag_pic,flag_evaluate);
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
                check(flag_realprice,flag_pic,flag_evaluate);
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
                check(flag_realprice,flag_pic,flag_evaluate);
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
                check(flag_realprice,flag_pic,flag_evaluate);
                break;
            case R.id.evaluate_05:
                for (int i = 0; i < 5; i++) {
                    credit+=20;
                    img_evaluate.get(i).setImageResource(R.mipmap.icon_good);
                }
                flag_evaluate = true;
                check(flag_realprice,flag_pic,flag_evaluate);
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
                builder = new AlertDialog.Builder(ConfirmPlace_BuyActivity.this);
                View view1 = LayoutInflater.from(ConfirmPlace_BuyActivity.this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("flag",true);
                        intent.putExtras(bundle);
                        ConfirmPlace_BuyActivity.this.setResult(0,intent);
                        ConfirmPlace_BuyActivity.this.finish();
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
                builder = new AlertDialog.Builder(ConfirmPlace_BuyActivity.this);
                View view1 = LayoutInflater.from(ConfirmPlace_BuyActivity.this).inflate(R.layout
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

    /**
     *键盘返回
     */
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("flag",false);
            intent.putExtras(bundle);
            ConfirmPlace_BuyActivity.this.setResult(0,intent);
            ConfirmPlace_BuyActivity.this.finish();
        }
        return true;
    }


}

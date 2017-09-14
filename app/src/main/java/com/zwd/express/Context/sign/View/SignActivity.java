package com.zwd.express.Context.sign.View;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwd.express.Context.sign.Module.SignGet;
import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.base.BaseAppManager;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;
import com.zwd.express.util.PxUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;

public class SignActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.sign)
    ImageView img_sign;
    @BindView(R.id.line_01)
    View line01;
    @BindView(R.id.circle_01)
    ImageView circle01;
    @BindView(R.id.line_02)
    View line02;
    @BindView(R.id.circle_02)
    ImageView circle02;
    @BindView(R.id.line_03)
    View line03;
    @BindView(R.id.circle_03)
    ImageView circle03;
    @BindView(R.id.line_04)
    View line04;
    @BindView(R.id.circle_04)
    ImageView circle04;
    @BindView(R.id.line_05)
    View line05;
    @BindView(R.id.circle_05)
    ImageView circle05;
    @BindView(R.id.line_06)
    View line06;
    @BindView(R.id.circle_06)
    ImageView circle06;
    @BindView(R.id.line_07)
    View line07;
    @BindView(R.id.circle_07)
    ImageView circle07;
    @BindView(R.id.ll_move)
    LinearLayout ll_move;
    @BindView(R.id.thing)
    LinearLayout thing;
    @BindView(R.id.down_thing)
    ImageView downThing;
    @BindView(R.id.box)
    ImageView box;
    @BindView(R.id.centerImg)
    ImageView centerImg;
    @BindView(R.id.title)
    TextView title;


    private int h, w;
    private int flag = 0;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private Bundle bundle;
    private int id;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "User_ReturnSign.aspx";
    private String url_sign = "User_Signing.aspx";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_sign;
    }


    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        initActionBar(toolbar);
        initMove();
        Url_initSign();

    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.hideRight();
        toolbar.setTitle("");
    }

    private void initMove() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams
                (ViewGroup
                .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                .WRAP_CONTENT);
        lp.setMargins(0, PxUtil.dip2px(this, 20), -PxUtil.dip2px(this, (648 -
                w)), 0);
        ll_move.setLayoutParams(lp);


    }
    ///初始化签到
    private void Url_initSign(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.returnSign(id, baseUrl, url, new CustomCallBack<RemoteDataResult<SignGet>>() {

            @Override
            public void onSuccess(Response<RemoteDataResult<SignGet>>
                                          response) {
                SignGet get = response.body().getData();
                initView(get.getStatus(),get.getSign());

            }

            @Override
            public void onFail(String message) {
                Log.d("---fail",message);
            }
        });
    }

    private void initView(int status, int sign) {
        if (status==0){
            img_sign.setImageResource(R.mipmap.btn_sign);
            img_sign.setClickable(true);
        }else {
            img_sign.setImageResource(R.mipmap.btn_unsign);
            img_sign.setClickable(false);
        }
        init(sign);
    }

    ///签到动图
    private void init(int sign){
        List<View> line = new ArrayList<>();
        line.add(line01);line.add(line02);line.add(line03);line.add(line04);
        line.add(line05);line.add(line06);line.add(line07);
        List<ImageView> circle = new ArrayList<>();
        circle.add(circle01);circle.add(circle02);circle.add(circle03);
        circle.add(circle04);circle.add(circle05);circle.add(circle06);
        circle.add(circle07);
        if(sign>0){
            for(int i=0;i<sign;i++){
                line.get(i).setBackgroundColor(getResources().getColor(R.color.yello));
                circle.get(i).setImageResource(R.drawable.circle_yello);
            }
        }

        flag = sign;
    }

    ////签到
    private void Url_sign(){
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.sign(id, baseUrl, url_sign, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                if (flag == 0) {
                    line01.setBackgroundColor(getResources().getColor(R.color.yello));
                    circle01.setImageResource(R.drawable.circle_yello);
                    dialog_("签到成功!");
                    img_sign.setImageResource(R.mipmap.btn_unsign);
                    img_sign.setClickable(false);
                    flag++;
                } else if (flag == 1) {
                    line02.setBackgroundColor(getResources().getColor(R.color.yello));
                    circle02.setImageResource(R.drawable.circle_yello);
                    dialog_("签到成功!");
                    img_sign.setImageResource(R.mipmap.btn_unsign);
                    img_sign.setClickable(false);
                    flag++;
                } else if (flag == 2) {
                    line03.setBackgroundColor(getResources().getColor(R.color.yello));
                    circle03.setImageResource(R.drawable.circle_yello);
                    ObjectAnimator am_move = ObjectAnimator.ofFloat(ll_move,
                            "translationX", -PxUtil.dip2px(SignActivity.this, 81));
                    am_move.setDuration(100);
                    am_move.start();
                    dialog_("签到成功!");
                    img_sign.setImageResource(R.mipmap.btn_unsign);
                    img_sign.setClickable(false);
                    flag++;
                } else if (flag == 3) {
                    line04.setBackgroundColor(getResources().getColor(R.color.yello));
                    circle04.setImageResource(R.drawable.circle_yello);
                    ObjectAnimator am_move = ObjectAnimator.ofFloat(ll_move,
                            "translationX", -PxUtil.dip2px(SignActivity.this, 81 * 2));
                    am_move.setDuration(100);
                    am_move.start();
                    dialog_("签到成功!");
                    img_sign.setImageResource(R.mipmap.btn_unsign);
                    img_sign.setClickable(false);
                    flag++;
                } else if (flag == 4) {
                    line05.setBackgroundColor(getResources().getColor(R.color.yello));
                    circle05.setImageResource(R.drawable.circle_yello);
                    ObjectAnimator am_move = ObjectAnimator.ofFloat(ll_move,
                            "translationX", -PxUtil.dip2px(SignActivity.this, 81 * 3));
                    am_move.setDuration(100);
                    am_move.start();
                    dialog_("签到成功!");
                    img_sign.setImageResource(R.mipmap.btn_unsign);
                    img_sign.setClickable(false);
                    flag++;
                } else if (flag == 5) {
                    line06.setBackgroundColor(getResources().getColor(R.color.yello));
                    circle06.setImageResource(R.drawable.circle_yello);
                    ObjectAnimator am_move = ObjectAnimator.ofFloat(ll_move,
                            "translationX", -PxUtil.dip2px(SignActivity.this, 81 * 4));
                    am_move.setDuration(100);
                    am_move.start();
                    dialog_("签到成功!");
                    img_sign.setImageResource(R.mipmap.btn_unsign);
                    img_sign.setClickable(false);
                    flag++;
                } else if (flag == 6) {
                    line07.setBackgroundColor(getResources().getColor(R.color.yello));
                    circle07.setImageResource(R.drawable.circle_yello);
                    ObjectAnimator am_move = ObjectAnimator.ofFloat(ll_move,
                            "translationX", -PxUtil.dip2px(SignActivity.this, 81 * 5));
                    am_move.setDuration(100);
                    am_move.start();
                    img_sign.setImageResource(R.mipmap.btn_unsign);
                    img_sign.setClickable(false);
                    flag++;
                    // downLine.setVisibility(View.INVISIBLE);

                    final ObjectAnimator am_down = ObjectAnimator.ofFloat(downThing,
                            "translationY", PxUtil.dip2px(SignActivity.this, 50));
                    am_down.setDuration(180);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            thing.setVisibility(View.GONE);
                            downThing.setVisibility(View.VISIBLE);
                            am_down.start();
                        }
                    }, 300);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            box.setImageResource(R.mipmap.icon_sign_take_after);
                            centerImg.setImageResource(R.mipmap.pic_sign_open);
                            title.setText("已经连续签到7天啦");
                            dialog_("恭喜您获得20积分奖励!");
                        }
                    }, 480);

                }
            }

            @Override
            public void onFail(String message) {
                Log.d("---fail",message);
            }
        });
    }

    @OnClick(R.id.sign)
    public void onViewClicked() {
       Url_sign();
    }
    private void dialog_(String str){
        builder = new AlertDialog.Builder(BaseAppManager.getInstance().getForwardActivity());
        View view1 = LayoutInflater.from(BaseAppManager.getInstance().getForwardActivity()).inflate(R.layout
                .dialog_back, null);
        TextView no = (TextView) view1.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView txt = (TextView)view1.findViewById(R.id.txt) ;
        txt.setText(str);
        builder.setView(view1);
        dialog = builder.create();
        dialog.show();
    }
}

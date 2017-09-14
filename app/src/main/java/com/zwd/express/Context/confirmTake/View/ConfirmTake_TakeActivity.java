package com.zwd.express.Context.confirmTake.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.express.Context.confirmPlace.View.ConfirmPlace_TakeActivity;
import com.zwd.express.Context.confirmTake.Module.ConfirmTakeGet;
import com.zwd.express.Context.confirmTake.Module.ConfirmTakePost;
import com.zwd.express.Context.orderDetail.Module.OrderDetailPost;
import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

public class ConfirmTake_TakeActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_reward)
    TextView txtReward;

    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Accept_ReturnDoneAccept.aspx";
    private String url_confirm = "Accept_DoneAccept.aspx";
    private int id;
    private int stu;
    private Bundle bundle;
    private Intent intent;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    @Override
    protected int getContentView() {
        return R.layout.activity_confirm_take_take;
    }

    @Override
    protected void initViews() {
        intent = getIntent();
        bundle = intent.getExtras();
        id = bundle.getInt("id");
        stu = bundle.getInt("stu");
        initActionBar(toolbar);
        Url_confirmdetail();
    }

    private void Url_confirmdetail() {
        OrderDetailPost post = new OrderDetailPost(stu, id);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.returnDoneAccept(post, baseUrl, url, new
                CustomCallBack<RemoteDataResult<ConfirmTakeGet>>() {
                    @Override
                    public void onSuccess
                            (Response<RemoteDataResult<ConfirmTakeGet>>
                                     response) {
                        ConfirmTakeGet get = response.body().getData();
                        initView(get);
                    }
                    @Override
                    public void onFail(String message) {
                        Log.d("---fail", message);
                    }
                });
    }

    private void initView(ConfirmTakeGet get) {
        if (get.getHeading() != null && get.getHeading().length() > 0) {
            Glide.with(this).load(get.getHeading()).into(head);
        } else {
            Glide.with(this).load(R.mipmap.pic_head).into(head);
        }
        txtName.setText(get.getName());
        txtPhone.setText(get.getUsernane());
        txtReward.setText("打赏金额:" + get.getReward());
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.hideRight();
        toolbar.setTitle("订单确认");
        toolbar.setBack(R.mipmap.bg_nomal_tool);
    }

    private void Url_confrim() {
        ConfirmTakePost post = new ConfirmTakePost(stu, id, 0.0, "");
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.doneAccept(post, baseUrl, url_confirm, new
                CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                builder = new AlertDialog.Builder(ConfirmTake_TakeActivity
                        .this);
                View view1 = LayoutInflater.from(ConfirmTake_TakeActivity
                        .this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("flag", true);
                        intent.putExtras(bundle);
                        ConfirmTake_TakeActivity.this.setResult(0, intent);
                        ConfirmTake_TakeActivity.this.finish();
                    }
                });
                TextView txt = (TextView) view1.findViewById(R.id.txt);
                txt.setText("确认成功!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onFail(String message) {
                builder = new AlertDialog.Builder(ConfirmTake_TakeActivity
                        .this);
                View view1 = LayoutInflater.from(ConfirmTake_TakeActivity
                        .this).inflate(R.layout
                        .dialog_back, null);
                TextView no = (TextView) view1.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                TextView txt = (TextView) view1.findViewById(R.id.txt);
                txt.setText("确认失败!");
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();
                Log.d("---", message);
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
            ConfirmTake_TakeActivity.this.setResult(0,intent);
            ConfirmTake_TakeActivity.this.finish();
        }
        return true;
    }
    @OnClick(R.id.btn)
    public void onViewClicked() {
        Url_confrim();
    }
}

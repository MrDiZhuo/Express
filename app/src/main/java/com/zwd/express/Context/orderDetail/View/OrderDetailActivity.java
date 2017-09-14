package com.zwd.express.Context.orderDetail.View;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwd.express.Context.orderDetail.Module.OrderDetailGet;
import com.zwd.express.Context.orderDetail.Module.OrderDetailPost;
import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.base.BaseAppManager;
import com.zwd.express.internet.CustomCallBack;
import com.zwd.express.internet.RemoteDataResult;
import com.zwd.express.internet.RemoteOptionIml;
import com.zwd.express.layout.NormalToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class OrderDetailActivity extends BaseActivity {
    private static final int UN_FINISH = 0;
    private static final int FINISH = 2;
    private static final int CANCLE = 3;
    private static final int UN_TAKE = 0;
    private static final int PLACE = 0;
    private static final int TAKE = 1;
    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.ll_finish)
    LinearLayout ll_finish;
    @BindView(R.id.txt_cancle)
    TextView txt_cancle;
    @BindView(R.id.txt_goodsaddressword)
    TextView txt_goodsaddressword;
    @BindView(R.id.txt_addressword)
    TextView txt_addressword;
    @BindView(R.id.ll_money)
    LinearLayout ll_money;
    @BindView(R.id.img_take)
    ImageView imgTake;
    @BindView(R.id.txt_take)
    TextView txtTake;
    @BindView(R.id.img_buy)
    ImageView imgBuy;
    @BindView(R.id.txt_buy)
    TextView txtBuy;
    @BindView(R.id.txt_money)
    TextView txtMoney;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    @BindView(R.id.txt_reward)
    TextView txtReward;
    @BindView(R.id.txt_Acceptname)
    TextView txtAcceptname;
    @BindView(R.id.txt_Acceptphone)
    TextView txtAcceptphone;
    @BindView(R.id.ll_goodsaddressword)
    LinearLayout llGoodsaddressword;

    private int type;
    private int state;
    private int id;
    private int stu;
    private int orderId;
    private Bundle bundle;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private String baseUrl = "http://139.224.164.183:8008/";
    private String url = "Accept_ReturnOrderDetail.aspx";
    private String url_delete = "Accept_DeleteOrder.aspx";
    private OrderDetailPost post;
    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initViews() {
        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        type = bundle.getInt("type");
        state = bundle.getInt("state");
        stu = bundle.getInt("stu");
        orderId = bundle.getInt("orderId");
        initActionBar(toolbar);
        post = new OrderDetailPost(orderId, stu, type);
        url_order();

        Log.d("---state",state+"");
    }

    private void initView(OrderDetailGet get) {
        if (stu == 0) {
            llGoodsaddressword.setVisibility(View.VISIBLE);
            txt_goodsaddressword.setText(get.getGoodsaddressword());
        }else {
            llGoodsaddressword.setVisibility(View.GONE);
        }
        txt_addressword.setText(get.getAddressword());
        if (get.getType() == 0) {
            ll_money.setVisibility(View.GONE);
            imgTake.setImageResource(R.drawable.circle_yello);
            txtTake.setTextColor(getResources().getColor(R.color.black));
            imgBuy.setImageResource(R.drawable.circle_empty02);
            txtBuy.setTextColor(getResources().getColor(R.color.gray_05));
        } else {
            ll_money.setVisibility(View.VISIBLE);
            txtMoney.setText("¥" + get.getPrice());
            imgBuy.setImageResource(R.drawable.circle_yello);
            txtBuy.setTextColor(getResources().getColor(R.color.black));
            imgTake.setImageResource(R.drawable.circle_empty02);
            txtTake.setTextColor(getResources().getColor(R.color.gray_05));
        }
        txtDescription.setText(get.getDescription());
        txtReward.setText("¥" + get.getReward());
        if (state == FINISH) {
            ll_finish.setVisibility(View.VISIBLE);
            txt_cancle.setVisibility(View.GONE);
            txtAcceptname.setText(get.getAcceptname());
            txtAcceptphone.setText(get.getAcceptphone());
        } else if (state == CANCLE) {
            ll_finish.setVisibility(View.GONE);
            txt_cancle.setVisibility(View.VISIBLE);
            txt_cancle.setText("已取消订单");
        } else if (state == UN_TAKE) {
            ll_finish.setVisibility(View.GONE);
            txt_cancle.setVisibility(View.VISIBLE);
            txt_cancle.setText("还未接单");
        }
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setTitle("订单详情");
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.hideRight();
        toolbar.setBackground(getResources().getDrawable(R.mipmap
                .bg_nomal_tool));
    }

    private void url_order() {

        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.orderDetail(post, baseUrl, url, new
                CustomCallBack<RemoteDataResult<OrderDetailGet>>() {


                    @Override
                    public void onSuccess
                            (Response<RemoteDataResult<OrderDetailGet>>
                                     response) {
                        OrderDetailGet get = response.body().getData();
                        initView(get);
                    }

                    @Override
                    public void onFail(String message) {
                        Log.d("---fail", message);
                    }
                });
    }

    ///删除订单
    private void Url_delete() {
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.deleteOrder(post, baseUrl, url_delete, new
                CustomCallBack<RemoteDataResult>() {

                    @Override
                    public void onSuccess(Response<RemoteDataResult> response) {
                        dialog_("删除成功!",true);
                    }

                    @Override
                    public void onFail(String message) {
                        Log.d("---fail", message);
                        dialog_("删除失败!",false);
                    }
                });
    }

    private void dialog_(String str, final boolean flag) {
        builder = new AlertDialog.Builder(BaseAppManager.getInstance()
                .getForwardActivity());
        View view1 = LayoutInflater.from(BaseAppManager.getInstance()
                .getForwardActivity()).inflate(R.layout
                .dialog_back, null);
        TextView no = (TextView) view1.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if(flag){
                    finish();
                }
            }
        });
        TextView txt = (TextView) view1.findViewById(R.id.txt);
        txt.setText(str);
        builder.setView(view1);
        dialog = builder.create();
        dialog.show();
    }


    @OnClick(R.id.btn)
    public void onViewClicked() {
        Url_delete();
    }

}

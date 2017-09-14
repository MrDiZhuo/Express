package com.zwd.express.Context.homePage.View;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.express.Context.aboutUs.View.AboutUsActivity;
import com.zwd.express.Context.identity.View.IdentityActivity;
import com.zwd.express.Context.invite.View.InviteActivity;
import com.zwd.express.Context.login.View.LoginActivity;
import com.zwd.express.Context.myOrder.View.MyOrderActivity;
import com.zwd.express.Context.myWallet.View.MyWalletActivity;
import com.zwd.express.Context.selfInfo.View.SelfInfoActivity;
import com.zwd.express.Context.sign.View.SignActivity;
import com.zwd.express.R;
import com.zwd.express.base.BaseAppManager;
import com.zwd.express.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.CSCustomServiceInfo;

public class LeftFragment extends BaseFragment {
    @BindView(R.id.selfinfo)
    CircleImageView selfinfo;

    Unbinder unbinder;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_credit)
    TextView txtCredit;
    @BindView(R.id.txt_mark)
    TextView txtMark;
    private int id;
    private String Qiniu_token;
    private Bundle bundle;
    private String heading;
    private String username;
    private String name;
    private String sex;
    private int mark;
    private int credit;
    private int userstatus;


    @Override
    protected void initViews(View rootView) {
        id = getArguments().getInt("id");
        Qiniu_token = getArguments().getString("Qiniu_token");
        heading = getArguments().getString("heading");
        username = getArguments().getString("username");
        name = getArguments().getString("name");
        sex = getArguments().getString("sex");
        mark = getArguments().getInt("mark");
        credit = getArguments().getInt("credit");
        userstatus = getArguments().getInt("userstatus");
        if (heading != null && heading.length() > 0) {
            Glide.with(getContext()).load(heading).into(selfinfo);
        } else {
            Glide.with(getContext()).load(R.mipmap.pic_head).into(selfinfo);
        }
        txtName.setText(name);txtCredit.setText("信用度:"+credit);
        txtMark.setText("我的积分:"+mark);
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_left;
    }


    @OnClick({R.id.order, R.id.wallet, R.id.sign, R.id.selfinfo, R.id.online
            , R.id.outLogin, R.id.identify, R.id.aboutUs, R.id.invite})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.invite:
                DrawerLayout drawerLayout08 = (DrawerLayout) BaseAppManager
                        .getInstance().getForwardActivity()
                        .findViewById(R.id.drawer_layout);
                drawerLayout08.closeDrawer(Gravity.LEFT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goActivity(InviteActivity.class);
                    }
                }, 10);
                break;
            case R.id.aboutUs:
                DrawerLayout drawerLayout07 = (DrawerLayout) BaseAppManager
                        .getInstance().getForwardActivity()
                        .findViewById(R.id.drawer_layout);
                drawerLayout07.closeDrawer(Gravity.LEFT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goActivity(AboutUsActivity.class);
                    }
                }, 10);

                break;
            case R.id.identify:
                DrawerLayout drawerLayout06 = (DrawerLayout) BaseAppManager
                        .getInstance().getForwardActivity()
                        .findViewById(R.id.drawer_layout);
                drawerLayout06.closeDrawer(Gravity.LEFT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bundle = new Bundle();
                        bundle.putInt("id",id);
                        bundle.putString("Qiniu_token", Qiniu_token);
                        goActivity(IdentityActivity.class,bundle);
                    }
                }, 10);

                break;
            case R.id.outLogin:
                DrawerLayout drawerLayout05 = (DrawerLayout) BaseAppManager
                        .getInstance().getForwardActivity()
                        .findViewById(R.id.drawer_layout);
                drawerLayout05.closeDrawer(Gravity.LEFT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BaseAppManager.getInstance().clearAll();
                        goActivity(LoginActivity.class);
                    }
                }, 10);

                break;
            case R.id.online:
                DrawerLayout drawerLayout04 = (DrawerLayout) BaseAppManager
                        .getInstance().getForwardActivity()
                        .findViewById(R.id.drawer_layout);
                drawerLayout04.closeDrawer(Gravity.LEFT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CSCustomServiceInfo.Builder csBuilder = new
                                CSCustomServiceInfo.Builder();
                        CSCustomServiceInfo csInfo = csBuilder.nickName("小明")
                                .build();
                        RongIM.getInstance().startCustomerServiceChat
                                (getActivity(), "KEFU149967205746591",
                                        "在线客服", csInfo);
                    }
                }, 10);

                break;
            case R.id.selfinfo:
                DrawerLayout drawerLayout03 = (DrawerLayout) BaseAppManager
                        .getInstance().getForwardActivity()
                        .findViewById(R.id.drawer_layout);
                drawerLayout03.closeDrawer(Gravity.LEFT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bundle = new Bundle();
                        bundle.putInt("id", id);
                        bundle.putInt("userstatus",userstatus);
                        bundle.putString("sex", sex);
                        bundle.putString("heading", heading);
                        bundle.putString("Qiniu_token", Qiniu_token);
                        bundle.putString("name", name);
                        bundle.putString("username", username);
                        bundle.putInt("credit",credit);

                        goActivity(SelfInfoActivity.class, bundle);
                    }
                }, 10);

                break;
            case R.id.order:
                DrawerLayout drawerLayout00 = (DrawerLayout) BaseAppManager
                        .getInstance().getForwardActivity()
                        .findViewById(R.id.drawer_layout);
                drawerLayout00.closeDrawer(Gravity.LEFT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bundle = new Bundle();
                        bundle.putString("Qiniu_token", Qiniu_token);
                        bundle.putInt("id", id);
                        goActivity(MyOrderActivity.class, bundle);
                    }
                }, 10);
                break;
            case R.id.wallet:
                DrawerLayout drawerLayout01 = (DrawerLayout) BaseAppManager
                        .getInstance().getForwardActivity()
                        .findViewById(R.id.drawer_layout);
                drawerLayout01.closeDrawer(Gravity.LEFT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bundle = new Bundle();
                        bundle.putInt("id", id);
                        goActivity(MyWalletActivity.class, bundle);
                    }
                }, 10);
                break;
            case R.id.sign:
                DrawerLayout drawerLayout02 = (DrawerLayout) BaseAppManager
                        .getInstance().getForwardActivity()
                        .findViewById(R.id.drawer_layout);
                drawerLayout02.closeDrawer(Gravity.LEFT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bundle = new Bundle();
                        bundle.putInt("id", id);
                        goActivity(SignActivity.class, bundle);
                    }
                }, 10);
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

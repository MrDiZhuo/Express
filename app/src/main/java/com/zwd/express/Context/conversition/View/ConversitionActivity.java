package com.zwd.express.Context.conversition.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zwd.express.Context.liveTake.View.LiveTakeActivity;
import com.zwd.express.Context.otherInfo.View.OtherInfoActivity;
import com.zwd.express.R;
import com.zwd.express.Widget02.live.LiveKit;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.layout.NormalToolBar;
import com.zwd.express.util.ToastUtil;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

public class ConversitionActivity extends BaseActivity implements RongIM.ConversationBehaviorListener{


    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    private String mTargetId;
    private String title;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    @Override
    protected int getContentView() {
        return R.layout.activity_conversition;
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        getIntentDate(intent);
        isReconnect(intent);
        initActionBar(toolbar);
    }


    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setTitle(title);
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setBack(R.mipmap.bg_nomal_tool);
        if (mConversationType == Conversation.ConversationType.DISCUSSION){
            toolbar.setRightImg(R.mipmap.icon_homepage_out);
            toolbar.setRightClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder = new AlertDialog.Builder(v.getContext());
                    View view = LayoutInflater.from(v.getContext())
                            .inflate(R.layout.dialog_takepic, null);
                    TextView txt = (TextView) view.findViewById(R.id.txt);
                    txt.setText("确认退出讨论组?");
                    TextView yes = (TextView) view.findViewById(R.id.yes);
                    TextView no = (TextView) view.findViewById(R.id.no);
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            RongIM.getInstance().quitDiscussion(mTargetId, new RongIMClient.OperationCallback() {
                                @Override
                                public void onSuccess() {
                                    ToastUtil.showShort(ConversitionActivity.this,"成功退出讨论组!");
                                    dialog.dismiss();
                                    finish();
                                }
                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                    ToastUtil.showShort(ConversitionActivity.this,"退出讨论组失败!");
                                }
                            });
                        }
                    });
                    builder.setView(view);
                    dialog = builder.create();
                    dialog.show();


                }
            });
        }else if(mConversationType== Conversation.ConversationType.CUSTOMER_SERVICE){
            toolbar.hideRight();
        } else {
            toolbar.setRightImg(R.mipmap.icon_conversition);
            toolbar.setRightClick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("mTargetId",Integer.valueOf(mTargetId));
                    goActivity(OtherInfoActivity.class,bundle);
                }
            });
        }


    }

    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    private void getIntentDate(Intent intent) {

        title = intent.getData().getQueryParameter("title");
        mTargetId = intent.getData().getQueryParameter("targetId");
        //intent.getData().getLastPathSegment();//获得当前会话类型
        mConversationType = Conversation.ConversationType.valueOf(intent
                .getData().getLastPathSegment().toUpperCase(Locale.getDefault
                        ()));
        enterFragment(mConversationType, mTargetId);
    }

    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType
     * @param mTargetId
     */
    private void enterFragment(Conversation.ConversationType
                                       mConversationType, String mTargetId) {

        ConversationFragment fragment = (ConversationFragment)
                getSupportFragmentManager().findFragmentById(R.id.conversation);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName)
                .buildUpon()
                .appendPath("conversation").appendPath(mConversationType
                        .getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);

    }


    /**
     * 判断消息是否是 push 消息
     */
    private void isReconnect(Intent intent) {


        String token = null;
        SharedPreferences pref = getSharedPreferences("test", MODE_PRIVATE);
        token = pref.getString("DEMO_TOKEN", "");

        //push或通知过来
        if (intent != null && intent.getData() != null && intent.getData()
                .getScheme().equals("rong")) {

            //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息
            if (intent.getData().getQueryParameter("push") != null
                    && intent.getData().getQueryParameter("push").equals
                    ("true")) {

                reconnect(token);
            } else {
                //程序切到后台，收到消息后点击进入,会执行这里
                if (RongIM.getInstance() == null || RongIM.getInstance()
                        .getRongIMClient() == null) {

                    reconnect(token);
                } else {
                    enterFragment(mConversationType, mTargetId);
                }
            }
        }
    }


    /**
     * 重连
     *
     * @param token
     */
    private void reconnect(String token) {

        if (getApplicationInfo().packageName.equals(getCurProcessName
                (getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String s) {

                    enterFragment(mConversationType, mTargetId);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }

    ////点击头像进入私人聊天
    @Override
    public boolean onUserPortraitClick(Context context, Conversation
            .ConversationType conversationType, UserInfo userInfo) {
        if (mConversationType == Conversation.ConversationType.DISCUSSION){
            RongIM.getInstance().startPrivateChat(context,userInfo.getUserId(),userInfo.getName());
        }
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation
            .ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }

}

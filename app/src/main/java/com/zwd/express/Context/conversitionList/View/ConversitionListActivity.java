package com.zwd.express.Context.conversitionList.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.layout.NormalToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

public class ConversitionListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_conversition_list;
    }

    @Override
    protected void initViews() {
        initActionBar(toolbar);
        isReconnect();
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("聊天列表");
        toolbar.hideRight();
        toolbar.setBack(R.mipmap.bg_nomal_tool);
    }

    /**
     * 加载 会话列表 ConversationListFragment
     */
    private void enterFragment() {

        ConversationListFragment fragment = (ConversationListFragment)
                getSupportFragmentManager().findFragmentById(R.id
                        .conversationlist);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName)
                .buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE
                        .getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP
                        .getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType
                        .DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM
                        .getName(), "false")//设置系统会话非聚合显示
                .build();

        fragment.setUri(uri);
    }

    /**
     * 判断消息是否是 push 消息
     */
    private void isReconnect() {
        Intent intent = getIntent();
        String token = null;
        SharedPreferences pref = getSharedPreferences("test", MODE_PRIVATE);
        token = pref.getString("DEMO_TOKEN", "");
        //push，通知或新消息过来
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
                    enterFragment();
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

                    enterFragment();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }


}

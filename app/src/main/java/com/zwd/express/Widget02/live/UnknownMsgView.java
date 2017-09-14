package com.zwd.express.Widget02.live;

import android.content.Context;
import android.view.LayoutInflater;

import com.zwd.express.R;
import com.zwd.express.base.BaseMsgView;

import io.rong.imlib.model.MessageContent;

/**
 * Created by asus-pc on 2017/7/20.
 */

public class UnknownMsgView  extends BaseMsgView {

    public UnknownMsgView(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.msg_unknown_view, this);
    }

    @Override
    public void setContent(MessageContent msgContent) {
    }
}
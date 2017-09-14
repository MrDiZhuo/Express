package com.zwd.express.Widget02.live;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zwd.express.R;
import com.zwd.express.base.BaseMsgView;

import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;


public class TextMsgView extends BaseMsgView {

    private TextView username;
    private TextView msgText;

    public TextMsgView(Context context) {
        super(context);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.msg_text_view, this);
        username = (TextView) view.findViewById(R.id.username);
        msgText = (TextView) view.findViewById(R.id.msg_text);
    }

    @Override
    public void setContent(MessageContent msgContent) {
        TextMessage msg = (TextMessage) msgContent;
        username.setText(msg.getUserInfo().getName() + ": ");
        msgText.setText( msg.getContent());
    }
}

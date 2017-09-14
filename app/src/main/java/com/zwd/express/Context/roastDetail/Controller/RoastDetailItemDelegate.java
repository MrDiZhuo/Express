package com.zwd.express.Context.roastDetail.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.express.Context.roastDetail.Module.ChatList;
import com.zwd.express.Context.roastDetail.Module.RoastDetailGet;
import com.zwd.express.Context.roastDetail.View.CommentActivity;
import com.zwd.express.Context.roastDetail.View.RoastDetailActivity;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.ItemViewDelegate;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.base.BaseAppManager;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus-pc on 2017/7/2.
 */

public class RoastDetailItemDelegate implements ItemViewDelegate<ChatList> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_roast_detail;
    }

    @Override
    public boolean isForViewType(ChatList item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, final ChatList chatList, final int position) {
        final RoastDetailActivity roastDetailActivity = (RoastDetailActivity)BaseAppManager.getInstance().getForwardActivity();

        CircleImageView head = (CircleImageView) holder.getView(R.id.head);
        Glide.with(BaseAppManager.getInstance().getForwardActivity()).load(chatList.getPicing()).into(head);
        holder.setText(R.id.name,chatList.getName());
        holder.setText(R.id.context,chatList.getComment());
        holder.setText(R.id.time,chatList.getDtime());
        LinearLayout ll =(LinearLayout)holder.getView(R.id.more);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaseAppManager.getInstance().getForwardActivity(),CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("context","");
                bundle.putString("name","");
                bundle.putInt("chatid",chatList.getId());
                bundle.putInt("userid",roastDetailActivity.userid);
                intent.putExtras(bundle);
                BaseAppManager.getInstance().getForwardActivity().startActivity(intent);
            }
        });
        LinearLayout ll_you =(LinearLayout)holder.getView(R.id.ll_you);
        TextView username = (TextView)holder.getView(R.id.username);
        TextView targetname = (TextView)holder.getView(R.id.targetname);
        TextView txt_mei = (TextView)holder.getView(R.id.txt_mei);
        if (chatList.getChatCR()!=null){
            ll_you.setVisibility(View.VISIBLE);
            username.setText(chatList.getChatCR().getUsername());
            targetname.setText(chatList.getChatCR().getTargetname());
            holder.setText(R.id.comment,chatList.getChatCR().getComment());
            txt_mei.setText("查看全部回复>>>");
        }else {
            ll_you.setVisibility(View.GONE);
            txt_mei.setText("快来抢沙发>>>");
        }
    }


}

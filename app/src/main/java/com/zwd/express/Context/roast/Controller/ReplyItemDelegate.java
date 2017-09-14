package com.zwd.express.Context.roast.Controller;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.express.Context.roast.Module.ReplyGet;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.ItemViewDelegate;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.base.BaseAppManager;

/**
 * Created by asus-pc on 2017/7/2.
 */

public class ReplyItemDelegate implements ItemViewDelegate<ReplyGet> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_reply;
    }

    @Override
    public boolean isForViewType(ReplyGet item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, ReplyGet replyGet, final int position) {
        ImageView circle = (ImageView) holder.getView(R.id.circle);
        if(replyGet.getFlag_look()==0){
            circle.setVisibility(View.VISIBLE);
        }else if (replyGet.getFlag_look()==1){
            circle.setVisibility(View.GONE);
        }
        holder.setText(R.id.name,replyGet.getName());
        TextView type = (TextView)holder.getView(R.id.type);
        if(replyGet.getType()==0){
            type.setText("评论了");
            type.setTextColor(BaseAppManager.getInstance().getForwardActivity().getResources()
                    .getColor(R.color.pink));
        }else {
            type.setText("回复了");
            type.setTextColor(BaseAppManager.getInstance().getForwardActivity().getResources()
                    .getColor(R.color.green));
        }
        holder.setText(R.id.object,replyGet.getObject());
        holder.setText(R.id.context,replyGet.getContext());

    }


}

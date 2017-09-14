package com.zwd.express.Context.roastDetail.Controller;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.express.Context.roastDetail.Module.CommentGet;
import com.zwd.express.Context.roastDetail.Module.Newclist;
import com.zwd.express.Context.roastDetail.Module.RoastDetailGet;
import com.zwd.express.Context.roastDetail.View.CommentActivity;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.ItemViewDelegate;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.base.BaseAppManager;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus-pc on 2017/7/2.
 */

public class CommentItemDelegate implements ItemViewDelegate<Newclist> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_comment;
    }

    @Override
    public boolean isForViewType(Newclist item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Newclist newclist, final int position) {
        CircleImageView head = (CircleImageView) holder.getView(R.id.head);
        Glide.with(BaseAppManager.getInstance().getForwardActivity()).load(newclist.getUserpicing()).into(head);
        holder.setText(R.id.name,newclist.getUsername());
        holder.setText(R.id.targetname,newclist.getTargetname());
        holder.setText(R.id.context,newclist.getComment());
        holder.setText(R.id.time,newclist.getDtime());
    }


}

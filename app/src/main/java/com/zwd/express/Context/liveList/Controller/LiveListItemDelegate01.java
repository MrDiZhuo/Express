package com.zwd.express.Context.liveList.Controller;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zwd.express.Context.homePage.Module.ReturnFiveGet;
import com.zwd.express.Context.liveList.Module.LiveListGet;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.ItemViewDelegate;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.base.BaseAppManager;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by asus-pc on 2017/7/9.
 */

public class LiveListItemDelegate01 implements ItemViewDelegate<ReturnFiveGet> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_livelist_01;
    }

    @Override
    public boolean isForViewType(ReturnFiveGet item, int position) {
        return item.getTYPE()==1;
    }

    @Override
    public void convert(ViewHolder holder, ReturnFiveGet get, int position) {

        ////添加背景图
        final RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.back);
        Glide.with(BaseAppManager.getInstance().getForwardActivity())
                .load(get.getPicurl()).asBitmap().into(new SimpleTarget<Bitmap>(){

            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable bitmapDrawable = new BitmapDrawable(resource);
                relativeLayout.setBackground(bitmapDrawable);
            }
        });

        holder.setText(R.id.id,get.getPlayingname());
        holder.setText(R.id.num,String.valueOf(get.getUserid()));
        holder.setText(R.id.name,get.getPlayingname());
        holder.setText(R.id.context,get.getIntroduce());
    }
}

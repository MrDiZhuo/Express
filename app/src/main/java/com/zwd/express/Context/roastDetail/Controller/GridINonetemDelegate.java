package com.zwd.express.Context.roastDetail.Controller;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.ItemViewDelegate;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.base.BaseAppManager;

/**
 * Created by asus-pc on 2017/7/2.
 */

public class GridINonetemDelegate implements ItemViewDelegate<Integer> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.choose_item;
    }

    @Override
    public boolean isForViewType(Integer item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Integer urls, final int position) {
        ImageView head = (ImageView) holder.getView(R.id.album_image);
        Glide.with(BaseAppManager.getInstance().getForwardActivity()).load(urls).into(head);

    }


}

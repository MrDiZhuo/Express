package com.zwd.express.Context.roast.Controller;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.ItemViewDelegate;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.base.BaseAppManager;

/**
 * Created by asus-pc on 2017/7/2.
 */

public class ChooseItemDelegate implements ItemViewDelegate<String> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.choose_item;
    }

    @Override
    public boolean isForViewType(String item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, String urls, final int position) {
        ImageView head = (ImageView) holder.getView(R.id.album_image);
        Glide.with(BaseAppManager.getInstance().getForwardActivity()).load(urls).into(head);
        if(urls.equals("000000")){
            Glide.with(BaseAppManager.getInstance().getForwardActivity()).load(R.mipmap.pic_choose).into(head);
        }

    }


}

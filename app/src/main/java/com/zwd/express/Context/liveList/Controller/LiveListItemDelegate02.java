package com.zwd.express.Context.liveList.Controller;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zwd.express.Context.homePage.Module.ReturnFiveGet;
import com.zwd.express.Context.liveList.Module.LiveListGet;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.ItemViewDelegate;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.base.BaseAppManager;

/**
 * Created by asus-pc on 2017/7/9.
 */

public class LiveListItemDelegate02 implements ItemViewDelegate<ReturnFiveGet> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_livelist_02;
    }

    @Override
    public boolean isForViewType(ReturnFiveGet item, int position) {
        return item.getTYPE()==2;
    }

    @Override
    public void convert(ViewHolder holder, ReturnFiveGet liveListGet, int position) {
        ImageView imageView = (ImageView)holder.getView(R.id.img);
        Glide.with(BaseAppManager.getInstance().getForwardActivity()).load(R.mipmap.dong)
                .into(imageView);

    }
}

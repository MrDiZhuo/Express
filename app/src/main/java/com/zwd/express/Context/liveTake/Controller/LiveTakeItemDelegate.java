package com.zwd.express.Context.liveTake.Controller;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.express.Context.liveList.Module.LiveListGet;
import com.zwd.express.Context.liveTake.Module.LiveTakeGet;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.ItemViewDelegate;
import com.zwd.express.Widget02.recycler.ViewHolder;
import com.zwd.express.base.BaseAppManager;

/**
 * Created by asus-pc on 2017/7/9.
 */

public class LiveTakeItemDelegate implements ItemViewDelegate<LiveTakeGet> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_livetake;
    }

    @Override
    public boolean isForViewType(LiveTakeGet item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, LiveTakeGet liveListGet, int position) {
        holder.setText(R.id.address,liveListGet.getAddr());
        holder.setText(R.id.reward,"¥"+liveListGet.getReward());
        holder.setText(R.id.money,"¥"+liveListGet.getMoney());
    }
}

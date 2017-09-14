package com.zwd.express.Context.homePage.Controller;

import com.zwd.express.Context.homePage.Module.DialogGet;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.ItemViewDelegate;
import com.zwd.express.Widget02.recycler.ViewHolder;

/**
 * Created by asus-pc on 2017/7/2.
 */

public class HomepageItemDelegateTop implements ItemViewDelegate<DialogGet> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_order_top;
    }

    @Override
    public boolean isForViewType(DialogGet item, int position) {

        return true;
    }

    @Override
    public void convert(ViewHolder holder, DialogGet dialogGet, final int position) {
        holder.setText(R.id.goods,dialogGet.getGoodsaddressword());
        holder.setText(R.id.delivery,dialogGet.getAddressword());
        holder.setText(R.id.time,dialogGet.getStarttime()+"～"+dialogGet.getEndtime());
        if(dialogGet.getType()==1){
            holder.setImageResource(R.id.circle,R.drawable.circle_buy);
            holder.setText(R.id.txt,"买东西");
        }else {
            holder.setImageResource(R.id.circle,R.drawable.circle_take);
            holder.setText(R.id.txt,"拿东西");
        }
        holder.setText(R.id.money,"¥"+dialogGet.getReward());


    }
}

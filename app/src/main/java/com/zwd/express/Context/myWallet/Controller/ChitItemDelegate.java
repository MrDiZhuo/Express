package com.zwd.express.Context.myWallet.Controller;

import com.zwd.express.Context.myWallet.Module.ChitGet;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.ItemViewDelegate;
import com.zwd.express.Widget02.recycler.ViewHolder;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class ChitItemDelegate implements ItemViewDelegate<ChitGet> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_chit;
    }

    @Override
    public boolean isForViewType(ChitGet item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, ChitGet chitGet, int position) {
        holder.setText(R.id.time,chitGet.getStarttime().replace("T"," ")
                +"～"+chitGet.getEndtime().replace("T"," "));
        if(chitGet.getMark()==200){
            holder.setBackgroundRes(R.id.ll,R.mipmap.bg_chit_green);
            holder.setText(R.id.integral,"200积分");
            holder.setText(R.id.money,"¥2.00");
        } else if (chitGet.getMark()==500) {
            holder.setBackgroundRes(R.id.ll,R.mipmap.bg_chit_yello);
            holder.setText(R.id.integral,"500积分");
            holder.setText(R.id.money,"¥5.00");
        }else if(chitGet.getMark()==1000){
            holder.setBackgroundRes(R.id.ll,R.mipmap.bg_chit_pink);
            holder.setText(R.id.integral,"1000积分");
            holder.setText(R.id.money,"¥10.00");
        }
    }
}

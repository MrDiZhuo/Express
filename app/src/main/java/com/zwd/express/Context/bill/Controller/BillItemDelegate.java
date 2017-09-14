package com.zwd.express.Context.bill.Controller;

import android.widget.TextView;

import com.zwd.express.Context.bill.Module.BillGet;
import com.zwd.express.Context.myOrder.Module.MyOrderGet;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.ItemViewDelegate;
import com.zwd.express.Widget02.recycler.ViewHolder;

/**
 * Created by asus-pc on 2017/7/2.
 */

public class BillItemDelegate implements ItemViewDelegate<BillGet> {


    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_bill;
    }

    @Override
    public boolean isForViewType(BillGet item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, BillGet billGet, int position) {
        holder.setText(R.id.day,billGet.getDtime().replace("T"," "));
        holder.setText(R.id.goodseddress,billGet.getGoodseddress());
        holder.setText(R.id.address,billGet.getSeteddress());
        holder.setText(R.id.money,billGet.getPay());
    }
}

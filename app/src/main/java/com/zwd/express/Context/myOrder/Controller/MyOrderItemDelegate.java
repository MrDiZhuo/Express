package com.zwd.express.Context.myOrder.Controller;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwd.express.Context.myOrder.Module.MyOrderGet;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.ItemViewDelegate;
import com.zwd.express.Widget02.recycler.ViewHolder;

/**
 * Created by asus-pc on 2017/7/2.
 */

public class MyOrderItemDelegate implements ItemViewDelegate<MyOrderGet> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_myorder;
    }

    @Override
    public boolean isForViewType(MyOrderGet item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, MyOrderGet myOrderGet, int position) {
        LinearLayout stu = (LinearLayout)holder.getView(R.id.stu);
        if(myOrderGet.getStu()==0){
            stu.setVisibility(View.VISIBLE);
            holder.setText(R.id.from,myOrderGet.getFrom());
        }else {
            stu.setVisibility(View.GONE);
        }
        holder.setText(R.id.to,myOrderGet.getTo());
        holder.setText(R.id.money,myOrderGet.getMoney());
        holder.setText(R.id.time,myOrderGet.getTime());
        TextView state = (TextView)holder.getView(R.id.state);
        if(myOrderGet.getState()==1||myOrderGet.getState()==0){
            state.setText("未完成");
            state.setTextColor(holder.getConvertView().getResources().getColor(R.color.pink));
        }else if(myOrderGet.getState()==2){
            state.setText("已完成");
            state.setTextColor(holder.getConvertView().getResources().getColor(R.color.green));
        }else if(myOrderGet.getState()==3){
            state.setText("已取消");
            state.setTextColor(holder.getConvertView().getResources().getColor(R.color.black));
        }

    }
}

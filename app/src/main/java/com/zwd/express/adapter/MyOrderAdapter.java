package com.zwd.express.adapter;

import android.content.Context;

import com.zwd.express.Context.myOrder.Controller.MyOrderItemDelegate;
import com.zwd.express.Context.myOrder.Module.MyOrderGet;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/2.
 */

public class MyOrderAdapter extends MultiItemTypeAdapter<MyOrderGet> {

    public MyOrderAdapter(Context context, List<MyOrderGet> datas) {
        super(context, datas);
        addItemViewDelegate(new MyOrderItemDelegate());
    }
}

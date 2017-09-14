package com.zwd.express.adapter;

import android.content.Context;

import com.zwd.express.Context.roast.Controller.RoastItemDelegate;
import com.zwd.express.Context.roast.Module.RoastListGet;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class RoastAdapter extends MultiItemTypeAdapter<RoastListGet> {
    public RoastAdapter(Context context, List<RoastListGet> datas) {
        super(context, datas);
        addItemViewDelegate(new RoastItemDelegate());
    }
}

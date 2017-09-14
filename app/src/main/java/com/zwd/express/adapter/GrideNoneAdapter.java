package com.zwd.express.adapter;

import android.content.Context;

import com.zwd.express.Context.roastDetail.Controller.GridINonetemDelegate;
import com.zwd.express.Context.roastDetail.Controller.GridItemDelegate;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class GrideNoneAdapter extends MultiItemTypeAdapter<Integer> {
    public GrideNoneAdapter(Context context, List<Integer> datas) {
        super(context, datas);
        addItemViewDelegate(new GridINonetemDelegate());
    }
}

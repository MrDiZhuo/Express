package com.zwd.express.adapter;

import android.content.Context;

import com.zwd.express.Context.roastDetail.Controller.CommentItemDelegate;
import com.zwd.express.Context.roastDetail.Controller.GridItemDelegate;
import com.zwd.express.Context.roastDetail.Module.CommentGet;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class GrideAdapter extends MultiItemTypeAdapter<String> {
    public GrideAdapter(Context context, List<String> datas) {
        super(context, datas);
        addItemViewDelegate(new GridItemDelegate());
    }
}

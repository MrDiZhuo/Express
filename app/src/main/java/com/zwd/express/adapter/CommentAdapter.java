package com.zwd.express.adapter;

import android.content.Context;

import com.zwd.express.Context.roastDetail.Controller.CommentItemDelegate;
import com.zwd.express.Context.roastDetail.Controller.RoastDetailItemDelegate;
import com.zwd.express.Context.roastDetail.Module.CommentGet;
import com.zwd.express.Context.roastDetail.Module.Newclist;
import com.zwd.express.Context.roastDetail.Module.RoastDetailGet;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class CommentAdapter extends MultiItemTypeAdapter<Newclist> {
    public CommentAdapter(Context context, List<Newclist> datas) {
        super(context, datas);
        addItemViewDelegate(new CommentItemDelegate());
    }
}

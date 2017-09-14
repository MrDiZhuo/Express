package com.zwd.express.adapter;

import android.content.Context;

import com.zwd.express.Context.roastDetail.Controller.RoastDetailItemDelegate;
import com.zwd.express.Context.roastDetail.Module.ChatList;
import com.zwd.express.Context.roastDetail.Module.RoastDetailGet;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class RoastDetailAdapter extends MultiItemTypeAdapter<ChatList> {
    public RoastDetailAdapter(Context context, List<ChatList> datas) {
        super(context, datas);
        addItemViewDelegate(new RoastDetailItemDelegate());
    }
}

package com.zwd.express.adapter;

import android.content.Context;

import com.zwd.express.Context.myWallet.Controller.ChitItemDelegate;
import com.zwd.express.Context.myWallet.Module.ChitGet;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class ChitAdapter extends MultiItemTypeAdapter<ChitGet> {
    public ChitAdapter(Context context, List<ChitGet> datas) {
        super(context, datas);
        addItemViewDelegate(new ChitItemDelegate());
    }
}

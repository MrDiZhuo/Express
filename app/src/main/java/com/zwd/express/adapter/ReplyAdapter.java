package com.zwd.express.adapter;

import android.content.Context;

import com.zwd.express.Context.myWallet.Controller.ChitItemDelegate;
import com.zwd.express.Context.myWallet.Module.ChitGet;
import com.zwd.express.Context.roast.Controller.ReplyItemDelegate;
import com.zwd.express.Context.roast.Module.ReplyGet;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class ReplyAdapter extends MultiItemTypeAdapter<ReplyGet> {
    public ReplyAdapter(Context context, List<ReplyGet> datas) {
        super(context, datas);
        addItemViewDelegate(new ReplyItemDelegate());
    }
}

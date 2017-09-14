package com.zwd.express.adapter;

import android.content.Context;

import com.zwd.express.Context.bill.Controller.BillItemDelegate;
import com.zwd.express.Context.bill.Module.BillGet;
import com.zwd.express.Context.liveTake.Controller.LiveTakeItemDelegate;
import com.zwd.express.Context.liveTake.Module.LiveTakeGet;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class LiveTakeAdapter extends MultiItemTypeAdapter<LiveTakeGet> {
    public LiveTakeAdapter(Context context, List<LiveTakeGet> datas) {
        super(context, datas);
        addItemViewDelegate(new LiveTakeItemDelegate());
    }
}

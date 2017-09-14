package com.zwd.express.adapter;

import android.content.Context;

import com.zwd.express.Context.bill.Controller.BillItemDelegate;
import com.zwd.express.Context.bill.Module.BillGet;
import com.zwd.express.Context.myWallet.Controller.ChitItemDelegate;
import com.zwd.express.Context.myWallet.Module.ChitGet;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class BillAdapter extends MultiItemTypeAdapter<BillGet> {
    public BillAdapter(Context context, List<BillGet> datas) {
        super(context, datas);
        addItemViewDelegate(new BillItemDelegate());
    }
}

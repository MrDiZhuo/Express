package com.zwd.express.adapter;

import android.content.Context;

import com.zwd.express.Context.roast.Controller.ChooseItemDelegate;
import com.zwd.express.Context.roastDetail.Controller.GridItemDelegate;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class ChooseAdapter extends MultiItemTypeAdapter<String> {
    public ChooseAdapter(Context context, List<String> datas) {
        super(context, datas);if(datas.size()==10){
            datas.remove(datas.size()-1);
        }
        addItemViewDelegate(new ChooseItemDelegate());
    }
}

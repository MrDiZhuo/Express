package com.zwd.express.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zwd.express.Context.homePage.Module.ReturnFiveGet;
import com.zwd.express.Context.liveList.Controller.LiveListItemDelegate01;
import com.zwd.express.Context.liveList.Controller.LiveListItemDelegate02;
import com.zwd.express.Context.liveList.Module.LiveListGet;
import com.zwd.express.R;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;
import com.zwd.express.Widget02.recycler.ViewHolder;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class LiveListAdapter extends MultiItemTypeAdapter<ReturnFiveGet> {

    public LiveListAdapter(Context context, List<ReturnFiveGet> datas) {
        super(context, datas);
        addItemViewDelegate(new LiveListItemDelegate01());

        addItemViewDelegate(new LiveListItemDelegate02());
    }


}
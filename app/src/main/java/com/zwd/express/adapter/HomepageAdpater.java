package com.zwd.express.adapter;

import android.content.Context;

import com.zwd.express.Context.homePage.Controller.HomepageItemDelegateTop;
import com.zwd.express.Context.homePage.Module.DialogGet;
import com.zwd.express.Widget02.recycler.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/2.
 */

public class HomepageAdpater extends MultiItemTypeAdapter<DialogGet>{

    public HomepageAdpater(Context context, List<DialogGet> datas) {
        super(context, datas);
        addItemViewDelegate(new HomepageItemDelegateTop());
    }


}

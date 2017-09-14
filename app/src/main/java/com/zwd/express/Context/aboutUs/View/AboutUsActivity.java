package com.zwd.express.Context.aboutUs.View;

import android.os.Bundle;
import android.view.View;

import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.layout.NormalToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    NormalToolBar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initViews() {
        initActionBar(toolbar);
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setTitle("关于我们");
        toolbar.hideRight();
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}

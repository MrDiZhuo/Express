package com.zwd.express.Context.withdraw.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zwd.express.R;
import com.zwd.express.base.BaseActivity;
import com.zwd.express.layout.NormalToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    NormalToolBar toolbar;
    @BindView(R.id.img01)
    ImageView img01;
    @BindView(R.id.img02)
    ImageView img02;

    @Override
    protected int getContentView() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initViews() {
        initActionBar(toolbar);
    }

    private void initActionBar(NormalToolBar toolbar) {
        toolbar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.hideRight();
        toolbar.setTitle("钱包提现");
        toolbar.setBack(R.mipmap.bg_nomal_tool);
    }



    @OnClick({R.id.pay01, R.id.pay02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay01:
                img01.setImageResource(R.drawable.circle_yello);
                img02.setImageResource(R.drawable.circle_empty02);
                break;
            case R.id.pay02:
                img01.setImageResource(R.drawable.circle_empty02);
                img02.setImageResource(R.drawable.circle_yello);
                break;
        }
    }
}

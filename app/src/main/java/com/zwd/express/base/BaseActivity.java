package com.zwd.express.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    protected static String TAG_LOG = null;

    protected Context mContext = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        TAG_LOG = this.getClass().getSimpleName();
        //将activity添加到自定义堆栈
        BaseAppManager.getInstance().addActivity(this);
        //绑定视图
        if (getContentView() != 0) {
            LayoutInflater inflater = getLayoutInflater();
            View inflate = inflater.inflate(getContentView(), null);
            setContentView(inflate);

            //设置view注解
            ButterKnife.bind(this, inflate);
        }

        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
    }

    protected abstract int getContentView();

    protected abstract void initViews();

    public void goActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void goActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void goActivityForResult(Class<?> cls, int resultCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, resultCode);
    }

    public void goActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }
}

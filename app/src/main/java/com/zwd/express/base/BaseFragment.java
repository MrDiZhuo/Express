package com.zwd.express.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {
    protected static String TAG_LOG = null;

    protected Context mContext;

    private View contentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set tag
        TAG_LOG = this.getClass().getSimpleName();

        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewID() != 0) {
            int resId = getContentViewID();
            contentView = inflater.inflate(resId, container, false);
            ButterKnife.bind(this, contentView);
            initViews(contentView);
            return contentView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * override this method to do operation in the fragment
     */
    protected abstract void initViews(View rootView);

    /**
     * override this method to return content view id of the fragment
     */
    protected abstract int getContentViewID();


    /**
     * -----------------------implements methods in BaseView------------
     **/
//    @Override
//    public void setPresenter(BasePresenter presenter) {
//
//    }
//
//    @Override
//    public void close() {
//
//    }
//
//    @Override
//    public void showMessage(String msg) {
//        ToastUtils.showShort(mContext,msg);
//    }
//
//    @Override
//    public void showProgress(String msg) {
//        DialogManager.showProgressDialog(mContext,msg);
//    }
//
//    @Override
//    public void showProgress(String msg, int progress) {
//        DialogManager.showProgressDialog(mContext,msg, progress);
//    }
//
//    @Override
//    public void hideProgress() {
//        DialogManager.hideProgressDialog();
//    }
//
//    @Override
//    public void showErrorMessage(String msg, String content) {
//        DialogManager.showErrorDialog(mContext,msg, content, new SweetAlertDialog.OnSweetClickListener() {
//            @Override
//            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                sweetAlertDialog.dismissWithAnimation();
//            }
//        });
//    }
    public void goActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    public void goActivity(Class<?> cls, Bundle... bundles) {
        Intent intent = new Intent(getActivity(), cls);
        for (Bundle bundle : bundles) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void goActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        startActivityForResult(intent, requestCode);
    }

    public void goActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

}

package com.zwd.express.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.express.R;

/**
 * Created by asus-pc on 2017/7/2.
 */

public class NormalToolBar extends RelativeLayout {
    View contentView;
    ImageView img_left;
    TextView txt_title;
    ImageView img_right;
    RelativeLayout rl;
    public NormalToolBar(Context context) {
        super(context);
        initView(context);
    }

    public NormalToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public NormalToolBar(Context context, AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_nomaltoolbar,this)
                .findViewById(R.id.toolbar);
        img_left = (ImageView)contentView.findViewById(R.id.left);
        txt_title = (TextView)contentView.findViewById(R.id.title);
        img_right = (ImageView)contentView.findViewById(R.id.right);
        rl = (RelativeLayout)contentView.findViewById(R.id.rl);
    }
    /**
     * 设置回调监听
     */
    public void setLeftClick(OnClickListener listener){
        img_left.setOnClickListener(listener);
    }
    public void setRightClick(OnClickListener listener){
        img_right.setOnClickListener(listener);
    }
    /**
     * 设置标题
     */
    public void setTitle(String title){
        txt_title.setText(title);
    }
    public String getTitle(){
        return txt_title.getText().toString();
    }

    public void setRightImg(int resourceId){
        Glide.with(getContext()).load(resourceId).into(img_right);
    }

    public void setBack(int resourceId){
        rl.setBackgroundResource(resourceId);
    }
    /***
     * 隐藏
     */
    public void hideRight(){
        img_right.setVisibility(View.INVISIBLE);
    }
    /**
     * 点击功能
     */
    public void LeftClick(boolean flag){
        img_left.setClickable(flag);
    }
    /***
     * 隐藏
     */
    public void hideTitle(){
        txt_title.setVisibility(View.INVISIBLE);
    }
}

package com.zwd.express.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.express.R;

/**
 * Created by asus-pc on 2017/6/28.
 */

public class HomeToolBar extends RelativeLayout {
    View contentView;
    ImageView img_left;
    ImageView img_right;
    TextView txt_title;
    public HomeToolBar(Context context) {
        super(context);
        initView(context);
    }
    public HomeToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HomeToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_hometoolbar,this)
                .findViewById(R.id.toolbar);
        img_left = (ImageView)contentView.findViewById(R.id.left);
        txt_title = (TextView)contentView.findViewById(R.id.title);
        img_right = (ImageView)contentView.findViewById(R.id.right);

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
    /**
     * 设置图片
     */
    public void setLeftImg(int resourceId){
        Glide.with(getContext()).load(resourceId).into(img_left);
    }
    public Drawable getImage(){
        return img_left.getBackground();
    }

    public void setRightImg(int resourceId){
        Glide.with(getContext()).load(resourceId).into(img_right);
    }
    /**
     * 设置背景
     */
    public void setBack(int resourceId){
        contentView.setBackgroundResource(resourceId);
    }
    /**
     * 隐藏
     */
    public void hideTitle(){
        txt_title.setVisibility(View.GONE);
    }
    public void hideLeft(){
        img_left.setVisibility(View.GONE);
    }
    public void hideRight(){
        img_right.setVisibility(View.GONE);
    }
    /**
     * 点击功能
     */
    public void LeftClick(boolean flag){
        img_left.setClickable(flag);
    }
    public void RightClick(boolean flag){
        img_right.setClickable(flag);
    }

}

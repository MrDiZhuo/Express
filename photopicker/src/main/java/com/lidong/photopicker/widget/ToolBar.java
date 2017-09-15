package com.lidong.photopicker.widget;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidong.photopicker.R;

public class ToolBar extends RelativeLayout {


    View contentView;
    ImageView back;
    TextView title;
    ImageView add;
    TextView subtitle;

    public ToolBar(Context context){
        super(context);
        initViews(context);
    }

    public ToolBar(Context context, AttributeSet attrs){
        super(context,attrs);
        initViews(context);
    }

    public ToolBar(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        contentView = LayoutInflater.from(context).inflate(R.layout.activity_tool_bar,this)
                .findViewById(R.id.action_bar_back);
        back = (ImageView) contentView.findViewById(R.id.action_bar_back_back);
        title = (TextView) contentView.findViewById(R.id.action_bar_back_title);
        add = (ImageView) contentView.findViewById(R.id.action_bar_back_add);
        subtitle = (TextView) contentView.findViewById(R.id.action_bar_back_subtitle);
    }

    /**
     * 设置返回动作监听
     * @param listener
     */
    public void setBackClick(OnClickListener listener){
        back.setOnClickListener(listener);
    }

    /**
     * 设置标题
     * @param stitle
     */
    public void setTitle(String stitle){
        title.setText(stitle);
    }

    public String getTitle(){
        return title.getText().toString();
    }

    /**
     * 设置subtitle
     */

    public void setsbTitle(String sbtitle){
        subtitle.setText(sbtitle);
    }
    public String getsbTitle(){
        return subtitle.getText().toString();
    }

    /**
     * 隐藏add按钮
     */
    public void hideCustomButton(){
        add.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置add按钮监听
     * @param listener
     */
    public void setCustomButtonClickListener(OnClickListener listener){
        add.setOnClickListener(listener);
    }


    /**
     * 隐藏subtitle
     */
    public void hidesubTitle(){
        subtitle.setVisibility(View.INVISIBLE);
    }
    /**
     * 设置subtitle监听
     */
    public void setSubtitleClickListener(OnClickListener listener){
        subtitle.setOnClickListener(listener);
    }




    /**
     * 隐藏返回键
     */
    public void hideBack(){
        back.setVisibility(View.INVISIBLE);
    }


    /**
     * 隐藏ActionBar
     */
    public void hide(){
        this.setVisibility(View.GONE);
    }

    /**
     * 显示ActionBar
     */
    public void show(){
        this.setVisibility(View.VISIBLE);
    }



}

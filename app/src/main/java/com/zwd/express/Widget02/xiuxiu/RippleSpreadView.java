package com.zwd.express.Widget02.xiuxiu;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.zwd.express.R;
import com.zwd.express.util.PxUtil;

/**
 * 作者：Alex
 * <br/>
 * 时间：2016/8/22 18:14
 * <br/>
 * 博客地址：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 * <br/>
 * <br/>
 */
public class RippleSpreadView extends View {
    private Paint innerPaint;
    private Paint outPaint;
    private float innerSize;
    private int innerAnimDuration;
    private int outColor;
    private float outSize;
    private float outSizeX;
    private float outSizeXX;
    private float outSizeXXX;
    private float outSizeXXXX;
    private float outSizeOld;
    private int outAnimDuration;
    private int width;
    private int height;
    private ObjectAnimator innerSizeAnimator;
    private float outSizeStart;
    private float outSizeEnd;
    private float outSpreadValue;
    private ObjectAnimator outSizeAnimator;
    private ObjectAnimator outSizeXAnimator;
    private ObjectAnimator outSizeXXAnimator;
    private ObjectAnimator outSizeXXXAnimator;
    private ObjectAnimator outSizeXXXXAnimator;
    private ObjectAnimator outSizeXXXXXAnimator;
    private Interpolator linearInterpolator;

    public RippleSpreadView(Context context) {
        super(context);
    }

    public RippleSpreadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        linearInterpolator = new LinearInterpolator();
        /**outSpreadValue = 1.2F  比较好*/
        outSpreadValue = 1.0F;
        Context context = getContext();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleSpreadView);
        innerSize = typedArray.getDimension(R.styleable.RippleSpreadView_rsv_innerSize, 20F);
        innerAnimDuration = typedArray.getInt(R.styleable.RippleSpreadView_rsv_innerAnimDuration, 500);
        outColor = typedArray.getColor(R.styleable.RippleSpreadView_rsv_outCircleColor, Color.parseColor("#8BC34A"));
        outSize = typedArray.getDimension(R.styleable.RippleSpreadView_rsv_outSize, 24F);
        outAnimDuration = typedArray.getInt(R.styleable.RippleSpreadView_rsv_outAnimDuration, 700);
        typedArray.recycle();

        innerPaint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_homepage_circle);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        innerPaint.setShader(bitmapShader);

        outPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outPaint.setStyle(Paint.Style.FILL);
        outPaint.setColor(outColor);
        if (innerSize < PxUtil.dip2px(context,8)) {
            innerSize = PxUtil.dip2px(context,20);
        }
        if ((innerAnimDuration < 100) || (innerAnimDuration > 10000)) {
            innerAnimDuration = 2000;
        }
        if (outSize < PxUtil.dip2px(context,4)) {
            outSize = PxUtil.dip2px(context,4);
        }
        if (outSize - innerSize < PxUtil.dip2px(context,2)) {
            outSize = innerSize + PxUtil.dip2px(context,2);
        }
        outSizeOld = outSize;

        if ((outAnimDuration < 100) || (outAnimDuration > 10000)) {
            outAnimDuration = 2000;
        }
        innerSizeAnimator = ObjectAnimator.ofFloat(this, "innerSize", innerSize * 1F, innerSize * 1.1F, innerSize * 1F, innerSize * 1.1F).setDuration(innerAnimDuration);
        innerSizeAnimator.setInterpolator(linearInterpolator);
        innerSizeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        innerSizeAnimator.setRepeatCount(Integer.MAX_VALUE);

        outSizeStart = innerSize * 0.9F;
        outSizeEnd = outSize * outSpreadValue;

        outSizeAnimator = ObjectAnimator.ofFloat(this, "outSize", outSizeStart, outSizeEnd).setDuration((long) (outAnimDuration));
        outSizeXAnimator = ObjectAnimator.ofFloat(this, "outSizeX", outSizeStart, outSizeEnd).setDuration((long) (outAnimDuration));
        outSizeXXAnimator = ObjectAnimator.ofFloat(this, "outSizeXX", outSizeStart, outSizeEnd).setDuration((long) (outAnimDuration));
        outSizeXXXAnimator = ObjectAnimator.ofFloat(this, "outSizeXXX", outSizeStart, outSizeEnd).setDuration((long) (outAnimDuration));
        outSizeXXXXAnimator = ObjectAnimator.ofFloat(this, "outSizeXXXX", outSizeStart, outSizeEnd).setDuration((long) (outAnimDuration));

        initObjectAnimator(outSizeAnimator);
        initObjectAnimator(outSizeXAnimator);
        initObjectAnimator(outSizeXXAnimator);
        initObjectAnimator(outSizeXXXAnimator);
        initObjectAnimator(outSizeXXXXAnimator);
        outSizeXAnimator.setStartDelay((long) (outAnimDuration / 6));
        outSizeXXAnimator.setStartDelay((long) (outAnimDuration * 2 / 4));
        outSizeXXXAnimator.setStartDelay((long) (outAnimDuration * 3 / 4));
        outSizeXXXXAnimator.setStartDelay((long) (outAnimDuration * 3 / 4));

    }

    public void startAnim(){
        try {
            innerSizeAnimator.start();
            outSizeAnimator.start();
            outSizeXAnimator.start();
            outSizeXXAnimator.start();
            outSizeXXXAnimator.start();
            outSizeXXXXAnimator.start();
        } catch (Exception e) {

        }

    }
    public void stopInAnim(){
        innerSizeAnimator.end();
    }
    public void stopOutAnim(){
        try {
            outSizeAnimator.end();
            outSizeXAnimator.end();
            outSizeXXAnimator.end();
            outSizeXXXAnimator.end();
            outSizeXXXXAnimator.end();
        } catch (Exception e) {

        }
    }

    private void initObjectAnimator(ObjectAnimator objectAnimator) {
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setRepeatCount(Integer.MAX_VALUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas, outSize, outPaint, outSizeEnd, outSizeStart, outSizeOld);
        drawCircle(canvas, outSizeX, outPaint, outSizeEnd, outSizeStart, outSizeOld);
        drawCircle(canvas, outSizeXX, outPaint, outSizeEnd, outSizeStart, outSizeOld);
        drawCircle(canvas, outSizeXXX, outPaint, outSizeEnd, outSizeStart, outSizeOld);
        drawCircle(canvas, outSizeXXXX, outPaint, outSizeEnd, outSizeStart, outSizeOld);


        canvas.drawCircle((width - 0) * 0.5F, (height - 0) * 0.5F, innerSize * 0.5F, innerPaint);
    }

    private final void drawCircle(Canvas canvas, float outSize, Paint outPaint, float outSizeEnd, float outSizeStart, float outSizeOld) {
        float k = 255 / (outSizeEnd - outSizeStart);
        int alpha = (int) (k * outSizeOld * outSpreadValue - k * outSize);
        outPaint.setAlpha(alpha);
        canvas.drawCircle((width - 0) * 0.5F, (height - 0) * 0.5F, outSize * 0.5F, outPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            if (innerSizeAnimator != null) {
                innerSizeAnimator.cancel();
            }
            if (outSizeAnimator != null) {
                outSizeAnimator.cancel();
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            if (innerSizeAnimator != null) {
                innerSizeAnimator.start();
            }
            if (outSizeAnimator != null) {
               // outSizeAnimator.start();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 供 innerSizeAnimator 调用
     */
    public void setInnerSize(float innerSize) {
        this.innerSize = innerSize;
        invalidate();
    }

    /**
     * 供 outSizeAnimator 调用
     */
    public void setOutSize(float outSize) {
        this.outSize = outSize;
        invalidate();
    }

    /**
     * 供 outSizeAnimator 调用
     */
    public void setOutSizeX(float outSize) {
        this.outSizeX = outSize;
        invalidate();
    }

    /**
     * 供 outSizeAnimator 调用
     */
    public void setOutSizeXX(float outSize) {
        this.outSizeXX = outSize;
        invalidate();
    }

    /**
     * 供 outSizeAnimator 调用
     */
    public void setOutSizeXXX(float outSize) {
        this.outSizeXXX = outSize;
        invalidate();
    }
    /**
     * 供 outSizeAnimator 调用
     */
    public void setOutSizeXXXX(float outSize) {
        this.outSizeXXXX = outSize;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        width = (int) outSizeEnd;
        height = (int) outSizeEnd;
        setMeasuredDimension(width, height);
    }




}

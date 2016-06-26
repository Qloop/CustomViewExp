package com.ltz.customview.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.ltz.customview.utils.MeasureUtil;

/**
 * Created by Qloop on 2016/6/25.
 */
public class CustomView extends View implements Runnable {


    private Paint mPaint;
    private Context mContext;
    private int radius;

    public CustomView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }


    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setStyle(Paint.Style.STROKE);//设置为描边
        mPaint.setColor(Color.LTGRAY);
        /**
         * 设置描边的宽度  px
         * setStrokeWidth(0)指的是宽度为 1 像素
         */
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //圆心坐标 x,y,半径,画笔
        canvas.drawCircle(MeasureUtil.getScreenSize((Activity) mContext)[0] / 2, MeasureUtil.getScreenSize((Activity) mContext)[1] / 2,
                radius, mPaint);

    }

    /**
     * 设置圆环半径
     *
     * @param radius
     */
    public synchronized void setRadius(int radius) {
        this.radius = radius;

        invalidate();//重新绘制
    }

    @Override
    public void run() {

        try {
            while (true) {
                if (radius <= 200) {
                    radius += 10;

                    postInvalidate();//开子线程刷新
                } else {
                    radius = 0;
                }
                Thread.sleep(80);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

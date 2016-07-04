package com.ltz.customview.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.ltz.customview.R;
import com.ltz.customview.utils.MeasureUtil;

/**
 * Created by Qloop on 2016/6/26.
 */
public class DisInView extends View {


    private Paint mPaint;
    //位图
    private Bitmap bitmapDis;
    //屏幕尺寸
    private int screenW;
    private int screenH;
    //位图左上角坐标
    private int y;
    private int x;
    private PorterDuffXfermode porterDuffXfermode;
    private Bitmap bitmapSrc;

    public DisInView(Context context) {
        super(context);
    }

    public DisInView(Context context, AttributeSet attrs) {
        super(context, attrs);

        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

        initPaint();
        initRes(context);
    }

    public DisInView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


//        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        initPaint();
        initRes(context);
    }


    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    /**
     * 初始化资源
     */
    private void initRes(Context context) {
        bitmapDis = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a3);
        bitmapSrc = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a3_mask);


        int[] screenSize = MeasureUtil.getScreenSize((Activity) context);

        //屏幕宽高
        screenW = screenSize[0];
        screenH = screenSize[1];

        //绘制位图的左上角坐标  位于屏幕中心
        x = screenW / 2 - bitmapDis.getWidth() / 2;
        y = screenW / 2 - bitmapDis.getWidth() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        //将绘制操作保存到新的图层（离屏缓存）
        int sr = canvas.saveLayer(0, 0, screenW, screenH, null, Canvas.ALL_SAVE_FLAG);

//        canvas.drawBitmap(bitmapDis, x, y, mPaint);
        canvas.drawColor(0xFF8f66DA);

        mPaint.setXfermode(porterDuffXfermode);

        canvas.drawBitmap(bitmapSrc, x, y, mPaint);

        mPaint.setXfermode(null);

        //还原画布ddd
        canvas.restoreToCount(sr);

    }
}

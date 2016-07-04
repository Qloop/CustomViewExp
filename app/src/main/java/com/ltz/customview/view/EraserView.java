package com.ltz.customview.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ltz.customview.R;
import com.ltz.customview.utils.MeasureUtil;

/**
 * 橡皮擦效果
 * Created by Qloop on 2016/6/26.
 */
public class EraserView extends View {
    private static final int MIN_MOVE_DIS = 5;// 最小的移动距离：如果我们手指在屏幕上的移动距离小于此值则不会绘制

    private int screenW;
    private int screenH;
    private Path mPath;
    private Paint mPaint;
    private Bitmap fgBitmap;
    private Canvas mCanvas;
    private Bitmap bgBitmap;
    private float preX;
    private float preY;

    public EraserView(Context context) {
        super(context);

        calParams(context);
        init(context);
    }

    public EraserView(Context context, AttributeSet attrs) {
        super(context, attrs);

        calParams(context);
        init(context);
    }

    public EraserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        calParams(context);
        init(context);
    }


    /**
     * 计算参数
     *
     * @param context
     */
    private void calParams(Context context) {
        int[] screenSize = MeasureUtil.getScreenSize((Activity) context);

        screenW = screenSize[0];
        screenH = screenSize[1];
    }


    /**
     * 初始化对象
     *
     * @param context
     */
    private void init(Context context) {
        mPath = new Path();

        //设置画笔 抗锯齿 防抖动
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        //设置画笔透明
        mPaint.setARGB(125, 255, 0, 0);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(50);

        mPaint.setStrokeJoin(Paint.Join.ROUND);//笔触边沿  设置为圆滑
        mPaint.setStrokeCap(Paint.Cap.ROUND);  //笔触类型

        // 生成前景图Bitmap
        fgBitmap = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.ARGB_8888);

        // 将其注入画布
        mCanvas = new Canvas(fgBitmap);

        // 绘制画布背景为中性灰
        mCanvas.drawColor(0xFF808080);

        // 获取背景底图Bitmap
        bgBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a4);

        //缩放背景图至屏幕大小
        bgBitmap = Bitmap.createScaledBitmap(bgBitmap, screenW, screenH, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制背景
        canvas.drawBitmap(bgBitmap, 0, 0, null);

        // 绘制前景
//        canvas.drawBitmap(fgBitmap, 0, 0, null);

        /*
         * 这里要注意canvas和mCanvas是两个不同的画布对象
         * 当我们在屏幕上移动手指绘制路径时会把路径通过mCanvas绘制到fgBitmap上
         * 每当我们手指移动一次均会将路径mPath作为目标图像绘制到mCanvas上，而在上面我们先在mCanvas上绘制了中性灰色
         * 两者会因为DST_IN模式的计算只显示中性灰，但是因为mPath的透明，计算生成的混合图像也会是透明的
         * 所以我们会得到“橡皮擦”的效果
         */
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:// 手指接触屏幕重置路径
                mPath.reset();
                mPath.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:// 手指移动时连接路径
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                if (dx >= MIN_MOVE_DIS || dy >= MIN_MOVE_DIS) {
                    mPath.quadTo(preX, preY, x, y);
                    preX = x;
                    preY = y;
                }
                break;
        }

        invalidate();//重新绘图
        return true;
    }
}

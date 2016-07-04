package com.ltz.customview.view;

import android.content.Context;
import android.content.Loader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 绘制文本
 * Created by Qloop on 2016/6/28.
 */
public class FontView extends View {

    private static final String TEXT = "ap爱哥ξτβбпшㄎㄊěǔぬも┰┠№＠↓";
    private Paint mPaint;
    private Paint.FontMetrics mFontMetrics;

    public FontView(Context context) {
        super(context);

        initPaint();
    }

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initPaint();
    }

    public FontView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setColor(Color.GREEN);
        mPaint.setTextSize(50);
        mFontMetrics = mPaint.getFontMetrics();

        Log.d("font", mFontMetrics.ascent + "--ascent");
        Log.d("font", mFontMetrics.descent + "--descent");
        Log.d("font", mFontMetrics.top + "--top");
        Log.d("font", mFontMetrics.bottom + "--bottom");
        Log.d("font", mFontMetrics.leading + "--leading");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(TEXT, 0, Math.abs(mFontMetrics.top), mPaint);
    }
}

package com.ltz.customview.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ltz.customview.R;
import com.ltz.customview.view.CustomView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Qloop on 2016/6/25.
 */
public class MainActivity extends Activity {


    @BindView(R.id.cv_circle)
    CustomView cvCircle;

    private int radius;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            cvCircle.setRadius(radius);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        cvCircle = (CustomView) findViewById(R.id.cv_circle);
        new Thread(cvCircle).start();
    }
}

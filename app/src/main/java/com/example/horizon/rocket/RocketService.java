package com.example.horizon.rocket;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by horizon on 2/5/2017.
 */

public class RocketService extends Service {

    private int mHeight;
    private WindowManager mWM;
    private int mWidth;
    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private View mRocket_view;
    private AnimationDrawable ad_rocket;
    private ImageView iv_rocket;


    @Override
    public void onCreate() {
        super.onCreate();

        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);

        //获取屏幕的宽高
        mWidth = mWM.getDefaultDisplay().getWidth();
        mHeight = mWM.getDefaultDisplay().getHeight();

        //自定义toast
        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;

        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                //      | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE   默认可以触摸
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;

        params.format = PixelFormat.TRANSLUCENT;
        //    params.windowAnimations = com.android.internal.R.style.Animation_Toast;

        //toast的显示等级,和来电等级一致
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.setTitle("Toast");
        params.gravity = Gravity.TOP + Gravity.LEFT;

        //将自定义布局转化为view对象
     //   android.view.View rocket_view = android.view.View.inflate(getApplicationContext(), R.layout.rocket_view, null);
        mRocket_view = View.inflate(getApplicationContext(), R.layout.rocket_view, null);

        //获取图片控件进行播放动画

        iv_rocket = (ImageView) mRocket_view.findViewById(R.id.iv_rocket);
        ad_rocket = (AnimationDrawable) iv_rocket.getBackground();
        ad_rocket.start();

        mWM.addView(mRocket_view,params);


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

        mWM.removeView(mRocket_view);
        super.onDestroy();
    }
}

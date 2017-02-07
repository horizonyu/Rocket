package com.example.horizon.rocket;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

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
    private int startX;
    private int startY;
    private int i = 0;
    private WindowManager.LayoutParams params;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            params.y = (int) msg.obj;
            mWM.updateViewLayout(mRocket_view,params);
        }
    };
    private int height;


    @Override
    public void onCreate() {
        super.onCreate();

        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);

        //获取屏幕的宽高
        mWidth = mWM.getDefaultDisplay().getWidth();
        mHeight = mWM.getDefaultDisplay().getHeight();

        //自定义toast
        params = mParams;
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

        //设置火箭的触摸事件
        mRocket_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:

                        int endX = (int) event.getRawX();
                        int endY = (int) event.getRawY();
                        int disX = endX - startX;
                        int disY = endY - startY;

                        params.x = params.x + disX;
                        params.y = params.y + disY;

                        //容错处理
                        if(params.x < 0) params.x = 0;
                        if(params.y < 0) params.y = 0;
                        if(params.x > mWidth - mRocket_view.getWidth()) params.x = mWidth - mRocket_view.getWidth();
                        if(params.y > mHeight - mRocket_view.getHeight()) params.y = mHeight - mRocket_view.getHeight();

                        //作为下一次移动的起始位置
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();

                        mWM.updateViewLayout(mRocket_view, params);

                        break;

                    case MotionEvent.ACTION_UP:
                     //   mWM.updateViewLayout(mRocket_view,params);
                        //设置小火箭的释放
                        if(params.x < mWidth/2 && params.y > 350)
                        {
                            //释放火箭
                            releaseRocket();

                            //添加火箭上升时的背景
                            Intent intent = new Intent(getApplicationContext(), RocketBackground.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        break;
                }
                return true;
            }
        });

        //获取图片控件进行播放动画
        iv_rocket = (ImageView) mRocket_view.findViewById(R.id.iv_rocket);
        ad_rocket = (AnimationDrawable) iv_rocket.getBackground();
        ad_rocket.start();

        mWM.addView(mRocket_view, params);


    }

    /**
     * 释放小火箭
     */
    private void releaseRocket() {
        Toast.makeText(getApplicationContext(),"释放火箭",Toast.LENGTH_LONG).show();

        params.x = (mWM.getDefaultDisplay().getWidth() - mRocket_view.getWidth()) / 2;
        new Thread() {

            @Override
            public void run() {
                super.run();
                height = mHeight - mRocket_view.getHeight();
                i = 0;
            while(height >= 0) {
                height = (mHeight - mRocket_view.getHeight()) - 35 * i;
                i++;

                //由于需要在执行过程中等待一段时间，所以需要新建一个子线程
                    SystemClock.sleep(20);
                    Message message = Message.obtain();
                    message.obj = height;
                    mHandler.sendMessage(message);
                }


             }
        }.start()  ;

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

package com.example.horizon.rocket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.view.animation.AlphaAnimation;
/**
 * Created by horizon on 2/6/2017.
 */
public class RocketBackground extends Activity {

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rocket_backgroung);

       //设置图片的淡入淡出的动画
        ImageView iv_top = (ImageView) findViewById(R.id.iv_rocket_background1);
        ImageView iv_bottom = (ImageView) findViewById(R.id.iv_rocket_background2);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(100);

        iv_top.setAnimation(alphaAnimation);
        iv_bottom.setAnimation(alphaAnimation);

        //将此activitiy在指定的时间内关闭
        mHandler.sendEmptyMessageDelayed(0,1500);


    }

}

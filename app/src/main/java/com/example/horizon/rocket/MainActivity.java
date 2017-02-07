package com.example.horizon.rocket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

//TODO 实现小火箭的拖动以及在底部的释放

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_release_rocket = (Button) findViewById(R.id.bt_release_rocket);
        Button bt_stop_rocket = (Button) findViewById(R.id.bt_stop_rocket);

        bt_release_rocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this,RocketService.class));
                finish();
            }
        });

        bt_stop_rocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this,RocketService.class));
                finish();
            }
        });


    }
}

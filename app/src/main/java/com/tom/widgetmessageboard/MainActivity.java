package com.tom.widgetmessageboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private TextView tv;
    // 手勢偵測器
    private GestureDetector detector;
    // pass image id 陣列
    private int[] passIds;
    // pass name 陣列
    private String[] passName;
    // pass 索引，位置（0: vaccine pass, 1: test pass, 2: recovery pass）
    private int passIndex;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        passIds = new int[]{R.drawable.qr_code, R.drawable.quack, R.drawable.main};
        passName = new String[]{"疫把照 - 疫苗pass", "疫把照 - 檢驗pass", "疫把照 - 康復pass"};
        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {
            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // 手勢往左滑動
                if (e2.getX() < e1.getX() - 40) {
                    Log.i("juan", "左移");
                    if (passIndex < passIds.length - 1) {
                        passIndex++;
                    } else {
                        passIndex = 0;
                    }
                }
                // 手勢往右滑動
                if (e2.getX() > e1.getX() + 40) {
                    Log.i("juan", "右移");
                    if (passIndex > 0) {
                        passIndex--;
                    } else {
                        passIndex = passIds.length - 1;
                    }
                }
                img.setImageResource(passIds[passIndex]);
                tv.setText(passName[passIndex]);
                return false;
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppWidget().onUpdateWidget(MainActivity.this);
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    public void initialize() {
        img = findViewById(R.id.img);
        tv = findViewById(R.id.tv);
        btnUpdate = findViewById(R.id.btn_update);
    }
}
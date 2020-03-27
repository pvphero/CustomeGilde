package com.vv.gilde;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView1, imageView2, imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);

        // 使用
        // Glide.with(this).load("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg").into(imageView);
    }

    // 加载此图片：https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg
    public void t1(View view) {
        Glide.with(this)
                .load("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg")
                .into(imageView1); // inio 不能在异步线程运行
    }

    public void t2(View view) {
        Glide.with(this)
                .load("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg")
                .into(imageView2);
    }

    public void t3(View view) {
        Glide.with(this)
                .load("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg")
                .into(imageView3);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 1.侵入代码很强
        // 以前的 框架
        /*XXXXX.unDestroy();
        XXXXX.unDestroy();
        XXXXX.unDestroy();*/

        // Glide 你不用写这句话，内部会监听你什么释放了
        // GLide 对 Activity 生命周期方法监听管理
    }
}

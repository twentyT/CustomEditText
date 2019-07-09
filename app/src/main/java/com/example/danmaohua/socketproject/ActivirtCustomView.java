package com.example.danmaohua.socketproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.danmaohua.socketproject.tool.CustomView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivirtCustomView extends AppCompatActivity {
    @BindView(R.id.customView)
    CustomView customView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        ButterKnife.bind(this);
    }

    public void clickTest(View view) {
        Toast.makeText(this,"停止呀",Toast.LENGTH_SHORT).show();
        customView.stopAnimation();
    }
}

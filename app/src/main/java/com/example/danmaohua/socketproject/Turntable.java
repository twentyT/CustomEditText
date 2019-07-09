package com.example.danmaohua.socketproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;


import com.example.danmaohua.socketproject.tool.LuckyPanView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018/2/9.
 */

public class Turntable extends Activity {


    @BindView(R.id.id_luckypan)
    LuckyPanView idLuckypan;
    @BindView(R.id.id_start_btn)
    ImageView idStartBtn;

    private Random random = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.turntable);
        ButterKnife.bind(this);
        idStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!idLuckypan.isStart()) {
                    idStartBtn.setImageResource(R.drawable.stop);
                    idLuckypan.luckyStart(random.nextInt(6));
                } else {
                    if (!idLuckypan.isShouldEnd())

                    {
                        idStartBtn.setImageResource(R.drawable.start);
                        idLuckypan.luckyEnd();
                    }
                }
            }
        });
    }

}

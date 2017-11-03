package com.example.administrator.baseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.baseproject.bean.PercelableBean;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private final String TAG = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        if (null != intent) {
            PercelableBean bean = intent.getParcelableExtra("data");
            List<PercelableBean> list = intent.getParcelableArrayListExtra("datas");
            if (null != bean) {
                Log.d(TAG, "data: " + bean.toString());
            }
            if (null != list || list.size() != 0) {
                for (PercelableBean b : list) {
                    Log.d(TAG, "datas: " + b.toString());
                }
            }

        }
    }
}

package com.example.administrator.baseproject;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.baseproject.bean.PercelableBean;
import com.example.administrator.baseproject.utils.ScreenUtils;
import com.example.administrator.baseproject.view.ZCToast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        ArrayList<PercelableBean> list = new ArrayList<>();
//        PercelableBean bean = new PercelableBean();
//        bean.setIntVar(0);
//        bean.setStringVar("aaa");
//        list.add(bean);
//        bean = new PercelableBean();
//        bean.setIntVar(1);
//        bean.setStringVar("bbb");
//        list.add(bean);
//        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//        intent.putExtra("data", bean);
//        intent.putParcelableArrayListExtra("datas", list);
//        startActivity(intent);
//    }
}

package com.example.administrator.baseproject.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/9/9.
 */

public class TestFragment extends Fragment implements View.OnClickListener{

    private final String TAG = "tag";

    private OnCalled called;

    public interface OnCalled{
        void doSomething();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            called = (OnCalled) context;
        }catch (ClassCastException e) {
            Log.d(TAG, "class cast failed!");
        }
    }

    @Override
    public void onClick(View view) {
        called.doSomething();
    }
}

package com.example.administrator.baseproject.view;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/10/13.
 */

public class ZCToast{

    private static Toast mToast;

    public static void showToast(Context context, String content) {
        if (null == mToast) {
            mToast = Toast.makeText(context.getApplicationContext(),
                    content, Toast.LENGTH_LONG);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }

    public static void showToast(Context context, CharSequence content) {
        showToast(context, content);
    }
}

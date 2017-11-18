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
//public class CustomToast {
//    private static TextView mTextView;
//    private static ImageView mImageView;
//
//    public static void showToast(Context context, String message) {
//        //加载Toast布局
//        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast, null);
//        //初始化布局控件
//        mTextView = (TextView) toastRoot.findViewById(R.id.message);
//        mImageView = (ImageView) toastRoot.findViewById(R.id.imageView);
//        //为控件设置属性
//        mTextView.setText(message);
//        mImageView.setImageResource(R.mipmap.ic_launcher);
//        //Toast的初始化
//        Toast toastStart = new Toast(context);
//        //获取屏幕高度
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        int height = wm.getDefaultDisplay().getHeight();
//        //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
//        toastStart.setGravity(Gravity.TOP, 0, height / 3);
//        toastStart.setDuration(Toast.LENGTH_LONG);
//        toastStart.setView(toastRoot);
//        toastStart.show();
//    }
//}
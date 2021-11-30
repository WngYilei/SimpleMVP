package com.xl.base.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtils {

    private static Toast sToast;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void toast(final Context context, final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast =  Toast.makeText(context, "", Toast.LENGTH_SHORT);
                }
                sToast.setText(text);
                sToast.show();
            }
        });
    }

    public static void toast(final Context context, final int id) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = new Toast(context);
                }
                sToast.setText("");
                sToast.show();
//                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

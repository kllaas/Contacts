package com.example.alexey.contacts.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Alexey on 01/03/17.
 */

public class ToastUtils {

    public static void showMessage(int message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showMessage(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}

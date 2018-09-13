package com.grupogtd.es20182.monitoriasufcg.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by francisco on 10/09/18.
 */

public class Util {

    public static void showShortToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}

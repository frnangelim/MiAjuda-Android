package com.grupogtd.es20182.monitoriasufcg.utils;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by francisco on 10/09/18.
 */

public class Util {

    public static String jwt = "";

    public static String getJwt() {
        return jwt;
    }

    public static void setJwt(String jwt) {
        Util.jwt = jwt;
    }

    public static void showShortToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    public static void showProgressbar(ProgressBar mProgressBar) {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public static void hideProgressbar(ProgressBar mProgressBar) {
        mProgressBar.setVisibility(View.GONE);
    }
}

package com.grupogtd.es20182.monitoriasufcg.service.serverConnector;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class QueueSingleton {
    private static QueueSingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private QueueSingleton(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized QueueSingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new QueueSingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}

package com.grupogtd.es20182.monitoriasufcg.service.serverConnector;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.Callback.IServerArrayCallback;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.Callback.IServerObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServerConnector {
    private RequestQueue mQueue;

    public ServerConnector(Context context) {
        mQueue = QueueSingleton.getInstance(context).getRequestQueue();
    }

    public void getObject(String url, final IServerObjectCallback callBack) {
        JsonObjectRequest getObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onError(error);
                    }
                }
        );

        mQueue.add(getObjectRequest);
    }

    public void getArray(String url, final IServerArrayCallback callBack) {
        JsonArrayRequest getArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onError(error);
                    }
                }
        );

        mQueue.add(getArrayRequest);
    }

    public void postObject(String url, JSONObject body, final IServerObjectCallback callBack) {
        JsonObjectRequest postObjectRequest = new JsonObjectRequest(Request.Method.POST, url, body,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onError(error);
                    }
                }
        );

        mQueue.add(postObjectRequest);
    }

    public void putObject(String url, JSONObject body, final IServerObjectCallback callBack) {
        JsonObjectRequest putObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, body,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onError(error);
                    }
                }
        );

        mQueue.add(putObjectRequest);
    }

    public void deleteObject(String url, JSONObject body, final IServerObjectCallback callBack) {
        JsonObjectRequest deleteObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, body,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onError(error);
                    }
                }
        );

        mQueue.add(deleteObjectRequest);
    }
}

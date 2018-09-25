package com.grupogtd.es20182.monitoriasufcg.service.serverConnector.Callback;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface IServerObjectCallback {
    void onSuccess(JSONObject result);

    void onError(VolleyError error);
}

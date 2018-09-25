package com.grupogtd.es20182.monitoriasufcg.service.serverConnector.Callback;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface IServerArrayCallback {
    void onSuccess(JSONArray result);

    void onError(VolleyError error);
}

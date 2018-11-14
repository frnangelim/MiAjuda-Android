package com.grupogtd.es20182.monitoriasufcg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grupogtd.es20182.monitoriasufcg.R;
import com.grupogtd.es20182.monitoriasufcg.adapters.MonitorAdapter;
import com.grupogtd.es20182.monitoriasufcg.service.domain.Course;
import com.grupogtd.es20182.monitoriasufcg.service.domain.User;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.Callback.IServerArrayCallback;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.ServerConnector;
import com.grupogtd.es20182.monitoriasufcg.utils.Constant;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private ArrayList<User> monitors = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MonitorAdapter mAdapter;

    private TextView emptyMonitors;

    private Course currentCourse;

    private ServerConnector mServerConnector;
    private Gson mGson;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mServerConnector = new ServerConnector(getContext());
        mGson = new Gson();
    }

    private void getMonitors() {
        mServerConnector.getArray(Constant.BASE_URL + Constant.MONITORS_QUERY + "/" + currentCourse.get_id(), new IServerArrayCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                Type postListType = new TypeToken<List<User>>() {
                }.getType();

                monitors = mGson.fromJson(String.valueOf(result), postListType);
                if(monitors.size() > 0){
                    emptyMonitors.setVisibility(View.INVISIBLE);
                    mAdapter = new MonitorAdapter(getContext(), monitors);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    Log.d("HEHE", "oi");
                    emptyMonitors.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("CALLBACK", error.toString());
                Log.i("CALLBACK", "FALHOU");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        mRecyclerView = v.findViewById(R.id.rv_monitors);
        emptyMonitors = v.findViewById(R.id.empty_monitors);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        currentCourse = getArguments().getParcelable(Constant.CLASS_OBJ_KEY);

        getMonitors();
        return v;
    }
}

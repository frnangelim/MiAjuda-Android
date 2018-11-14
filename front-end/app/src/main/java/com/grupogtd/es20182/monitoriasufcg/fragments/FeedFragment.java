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
import com.grupogtd.es20182.monitoriasufcg.adapters.PostAdapter;
import com.grupogtd.es20182.monitoriasufcg.service.domain.Course;
import com.grupogtd.es20182.monitoriasufcg.service.domain.Post;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.Callback.IServerArrayCallback;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.ServerConnector;
import com.grupogtd.es20182.monitoriasufcg.utils.Constant;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    private ArrayList<Post> posts = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private PostAdapter mAdapter;
    private Course currentCourse;
    private TextView emptyFeed;

    private ServerConnector mServerConnector;
    private Gson mGson;

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance(ArrayList<Post> posts) {
        FeedFragment fragment = new FeedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mServerConnector = new ServerConnector(getContext());
        mGson = new Gson();
    }

    private void getPosts() {
        mServerConnector.getArray(Constant.BASE_URL + Constant.POSTS_QUERY + "/" + currentCourse.get_id(), new IServerArrayCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                Type postListType = new TypeToken<List<Post>>() {
                }.getType();

                posts = mGson.fromJson(String.valueOf(result), postListType);
                if (posts.size() > 0) {
                    emptyFeed.setVisibility(View.INVISIBLE);
                    mAdapter = new PostAdapter(getContext(), posts);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    emptyFeed.setVisibility(View.VISIBLE);
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
        View v = inflater.inflate(R.layout.fragment_feed, container, false);

        mRecyclerView = v.findViewById(R.id.rv_feed);
        emptyFeed = v.findViewById(R.id.empty_posts);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        currentCourse = getArguments().getParcelable(Constant.CLASS_OBJ_KEY);
        getPosts();
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

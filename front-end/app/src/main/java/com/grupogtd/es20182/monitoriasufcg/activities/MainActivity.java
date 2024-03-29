package com.grupogtd.es20182.monitoriasufcg.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grupogtd.es20182.monitoriasufcg.R;
import com.grupogtd.es20182.monitoriasufcg.adapters.CourseAdapter;
import com.grupogtd.es20182.monitoriasufcg.firebase.FirebaseConnection;
import com.grupogtd.es20182.monitoriasufcg.service.domain.Course;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.Callback.IServerArrayCallback;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.Callback.IServerObjectCallback;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.ServerConnector;
import com.grupogtd.es20182.monitoriasufcg.utils.Constant;
import com.grupogtd.es20182.monitoriasufcg.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private CourseAdapter mAdapter;

    private FloatingActionButton fabAdd;
    private Context mContext;

    private ServerConnector mServerConnector;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mServerConnector = new ServerConnector(this);
        mGson = new Gson();

        connectGoogleApi();
        initView();
        getCourses();
    }

    private void initView() {
        initRecyclerView();
        initFab();
    }

    private void getCourses() {
        Util.showProgressbar(mProgressBar);

        mServerConnector.getArray(Constant.BASE_URL + Constant.GET_MY_CLASSES_QUERY, new IServerArrayCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                Type courseListType = new TypeToken<List<Course>>() {
                }.getType();

                ArrayList<Course> courses = mGson.fromJson(String.valueOf(result), courseListType);

                TextView empty = findViewById(R.id.empty_courses);
                if (courses.size() > 0) {
                    mAdapter = new CourseAdapter(mContext, courses);
                    mRecyclerView.setAdapter(mAdapter);
                    empty.setVisibility(View.INVISIBLE);
                } else {
                    empty.setVisibility(View.VISIBLE);
                }

                Util.hideProgressbar(mProgressBar);
            }
            @Override
            public void onError(VolleyError error) {
                Log.d("CALLBACK", error.toString());
                Log.i("CALLBACK", "FALHOU");
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.rv_courses);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mProgressBar = findViewById(R.id.progress);
    }

    private void initFab() {
        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddCourseDialog();
            }
        });
    }

    private void openAddCourseDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_course, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText code = mView.findViewById(R.id.et_code);
        Button add = mView.findViewById(R.id.btn_add);
        Button cancel = mView.findViewById(R.id.btn_cancel);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinClass(code.getText().toString());
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void joinClass(String code) {
        Util.showProgressbar(mProgressBar);
        JSONObject json = new JSONObject();
        try {
            json.put(Constant.CLASS_TOKEN_KEY, code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mServerConnector.postObject(Constant.BASE_URL + Constant.JOIN_CLASS_QUERY, json, new IServerObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Util.showShortToast(mContext, mContext.getString(R.string.course_joined));
                getCourses();
            }

            @Override
            public void onError(VolleyError error) {
                Util.showShortToast(mContext, mContext.getString(R.string.invalid_code));
                Log.d("result2", error.toString());
                Util.hideProgressbar(mProgressBar);
            }
        });
    }

    private void connectGoogleApi() {
        GoogleSignInOptions gsoptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gsoptions)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                openConfirmDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openConfirmDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(mContext.getString(R.string.logout))
                .setMessage(mContext.getString(R.string.confirm_acc_exit))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void logout() {
        FirebaseConnection.getFirebaseAuth().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Util.showShortToast(MainActivity.this, getString(R.string.account_disconnected));
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Util.showShortToast(MainActivity.this, getString(R.string.connection_fail_msg));
    }
}

package com.grupogtd.es20182.monitoriasufcg.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.grupogtd.es20182.monitoriasufcg.R;
import com.grupogtd.es20182.monitoriasufcg.adapters.CourseAdapter;
import com.grupogtd.es20182.monitoriasufcg.firebase.FirebaseConnection;
import com.grupogtd.es20182.monitoriasufcg.service.domain.Course;
import com.grupogtd.es20182.monitoriasufcg.utils.Util;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private CourseAdapter mAdapter;

    private FloatingActionButton fabAdd;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        connectGoogleApi();
        initRecyclerView();
        initFab();
        getCourses();
    }

    private void getCourses() {
        Util.showProgressbar(mProgressBar);
        ArrayList<Course> courses = new ArrayList<Course>();
        courses.add(new Course("Engenharia de Software", "Rohit Gheyi"));
        courses.add(new Course("Banco de Dados I", "Cláudio Campelo"));
        courses.add(new Course("Métodos e Software Númericos", "Antão Moura"));
        courses.add(new Course("Programação I", "Dalton Serey"));
        courses.add(new Course("Introdução a Computação", "Joseana Fechine"));
        courses.add(new Course("Programação Funcinal", "Adalberto Cajueiro"));

        mAdapter = new CourseAdapter(this, courses);
        mRecyclerView.setAdapter(mAdapter);
        Util.hideProgressbar(mProgressBar);
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

        final EditText deckName = mView.findViewById(R.id.et_code);
        Button addDeck = mView.findViewById(R.id.btn_add);

        addDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deckName.getText().toString().trim().length() >= 3) {
                    Util.showShortToast(mContext, "Turma adicionada(NOT WORKING)");
                    dialog.dismiss();
                } else {
                    Util.showShortToast(mContext, "Código inválido");
                }
            }
        });

        dialog.show();
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
        builder.setTitle("Sair")
                .setMessage("Você tem deseja que deseja sair da conta?")
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

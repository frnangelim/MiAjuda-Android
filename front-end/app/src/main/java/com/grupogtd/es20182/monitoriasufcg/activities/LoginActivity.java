package com.grupogtd.es20182.monitoriasufcg.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.VolleyError;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.grupogtd.es20182.monitoriasufcg.R;
import com.grupogtd.es20182.monitoriasufcg.firebase.FirebaseConnection;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.Callback.IServerObjectCallback;
import com.grupogtd.es20182.monitoriasufcg.service.serverConnector.ServerConnector;
import com.grupogtd.es20182.monitoriasufcg.utils.Constant;
import com.grupogtd.es20182.monitoriasufcg.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private SignInButton btnSignIn;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        checkIfUserIsAlreadyLogged();
    }

    private void initView() {
        mFirebaseAuth = FirebaseConnection.getFirebaseAuth();
        connectGoogleApi();

        btnSignIn = findViewById(R.id.signInGoogle);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void checkIfUserIsAlreadyLogged() {
        if (FirebaseConnection.getFirebaseAuth().getCurrentUser() != null) {
            SharedPreferences sharedPreferences = getSharedPreferences(Constant.USER_PREFERENCES, Context.MODE_PRIVATE);
            String jwt = sharedPreferences.getString(Constant.ACCESS_TOKEN, null);
            if (jwt != null) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, 1);
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Util.showShortToast(LoginActivity.this, getString(R.string.connection_fail_msg));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount account = result.getSignInAccount();

            if (account != null) {
                if (result.isSuccess()) {
                    firebaseLogin(account);
                }
            }
        }
    }

    private void firebaseLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            getAccessFromBackend();
                        } else {
                            task.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Util.showShortToast(LoginActivity.this, e.getMessage());
                                }
                            });
                        }
                    }
                });
    }

    private void getAccessFromBackend() {
        ServerConnector mServerConnector = new ServerConnector(this);

        JSONObject json = new JSONObject();
        try {
            json.put(Constant.EMAIL_KEY, FirebaseConnection.getFirebaseUser().getEmail());
            json.put(Constant.NAME_KEY, FirebaseConnection.getFirebaseUser().getDisplayName());
            json.put(Constant.PASSWORD_KEY, Constant.DEFAULT_PASSWORD);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mServerConnector.postObject(Constant.BASE_URL + Constant.LOGIN_QUERY, json, new IServerObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                saveToken(result);
                redirectToMain();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("result2", error.toString());
            }
        });
    }

    private void saveToken(JSONObject result) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            editor.putString(Constant.ACCESS_TOKEN, String.valueOf(result.get("jwt")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.apply();
    }

    private void redirectToMain() {
        Util.showShortToast(LoginActivity.this, getString(R.string.login_success_msg));
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

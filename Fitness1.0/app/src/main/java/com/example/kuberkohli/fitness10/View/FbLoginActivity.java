package com.example.kuberkohli.fitness10.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kuberkohli.fitness10.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class FbLoginActivity extends AppCompatActivity {

    private TextView login_status;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProfilePictureView profilePictureView;
    private ImageView propic;
    private String userId, url, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        login_status = (TextView) findViewById(R.id.login_status);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        callbackManager = CallbackManager.Factory.create();
        propic = (ImageView) findViewById(R.id.propic);

        if (AccessToken.getCurrentAccessToken() != null) {
            userId = AccessToken.getCurrentAccessToken().getUserId();
            url = "https://graph.facebook.com/" + userId + "/picture?type=large";
            username = Profile.getCurrentProfile().getName();
            Bundle userData = new Bundle();
            userData.putString("userName", username);
            userData.putString("url", url);
            userData.putString("userId", userId);
            Intent intent = new Intent(FbLoginActivity.this, TabNavigationActivity.class);
            intent.putExtra("userData", userData);
            startActivity(intent);


        }
        else {
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    userId = loginResult.getAccessToken().getUserId();
                    url = "https://graph.facebook.com/" + userId + "/picture?type=large";

                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    Log.v("FBLoginActivity", response.toString());

                                    // Application code
                                    try {
                                        username = object.getString("name");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender,birthday");
                    request.setParameters(parameters);
                    request.executeAsync();

                    Bundle userData = new Bundle();
                    userData.putString("userName", username);
                    userData.putString("url", url);
                    userData.putString("userId", userId);
                    Intent intent = new Intent(FbLoginActivity.this, TabNavigationActivity.class);
                    intent.putExtra("userData", userData);
                    startActivity(intent);
                }

                @Override
                public void onCancel() {
                    login_status.setText("Login Cancelled");
                }

                @Override
                public void onError(FacebookException error) {
                    login_status.setText("Login Error :" + error.getMessage());
                }
            });


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}

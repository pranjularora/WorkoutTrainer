package com.example.kuberkohli.fitness10.View;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuberkohli.fitness10.R;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {

    private TextView login_status;
    private ImageView propic;
    private Bundle userData;

    private String userId, url, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_profile);

        login_status = (TextView) findViewById(R.id.login_status);
        propic = (ImageView) findViewById(R.id.propic);
        userData = getIntent().getBundleExtra("userData");
        username = Profile.getCurrentProfile().getName();
        url = userData.getString("url");

        Picasso.with(getApplicationContext())
                .load(url)
                .into(propic);

        login_status.setText("Welcome ! "+username);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Demonstrating Coordinator layout", Snackbar.LENGTH_LONG).show();
            }
        });


    }
}

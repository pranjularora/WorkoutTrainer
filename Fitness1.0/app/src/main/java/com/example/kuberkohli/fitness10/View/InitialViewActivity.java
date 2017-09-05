package com.example.kuberkohli.fitness10.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.kuberkohli.fitness10.R;
import com.facebook.AccessToken;

public class InitialViewActivity extends AppCompatActivity {
    private View v;
    SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_view);
        prefs = getSharedPreferences("com.example.kuberkohli.fitness10", MODE_PRIVATE);
        v = (View) findViewById(R.id.activity_initial_view);
        new CountDownTimer(2700, 1000) {
            public void onFinish() {
                if (prefs.getBoolean("firstrun", true)) {
                    // Do first run stuff here then set 'firstrun' as false
                    // using the following line to edit/commit prefs
                    prefs.edit().putBoolean("firstrun", false).commit();
                    animationTransition();
                }else{
                    Intent intent = new Intent(InitialViewActivity.this, FbLoginActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(InitialViewActivity.this, v ,"ActivityTransition" );
                    InitialViewActivity.this.startActivity(intent, options.toBundle());
                }

            }

            public void onTick(long millisUntilFinished) {
               // Do nothing
            }
        }.start();
    }

    void animationTransition(){
        if (AccessToken.getCurrentAccessToken() != null){
            Intent intent = new Intent(getApplicationContext(), TabNavigationActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}

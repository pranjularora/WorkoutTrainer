package com.example.kuberkohli.fitness10.View;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.kuberkohli.fitness10.R;

public class SettingsActivity extends AppCompatActivity {

    AudioManager manager;
    Button mute, unMute;

    // function calls to mute or unmute the volume on button clicks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mute = (Button) findViewById(R.id.Mute);
        unMute = (Button) findViewById(R.id.unMute);

        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Volume Muted", Toast.LENGTH_SHORT).show();
                manager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            }
        });

        unMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Volume UnMuted", Toast.LENGTH_SHORT).show();
                manager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            }
        });
    }

}

package com.example.kuberkohli.fitness10.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.kuberkohli.fitness10.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Youtube1Activity extends YouTubeBaseActivity implements
    YouTubePlayer.OnInitializedListener, View.OnClickListener {

        private static final int RECOVERY_DIALOG_REQUEST = 1;
        private String url;

        // YouTube player view
        private YouTubePlayerView youTubeView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            url = getIntent().getStringExtra("url");

            setContentView(R.layout.fragment_youtube_player);

            youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

            // Initializing video player with developer key
            youTubeView.initialize(getString(R.string.google_key), this);

        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider,
                YouTubeInitializationResult errorReason) {
            if (errorReason.isUserRecoverableError()) {
                errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
            } else {
                String errorMessage = String.format("Error in youtube player.", errorReason.toString());
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                            YouTubePlayer player, boolean wasRestored) {
            if (!wasRestored) {

                // loadVideo() will auto play video
                // Use cueVideo() method, if you don't want to play it automatically
                player.loadVideo(url);

                // Hiding player controls
                player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == RECOVERY_DIALOG_REQUEST) {
                // Retry initialization if user performed a recovery action
                getYouTubePlayerProvider().initialize(getString(R.string.google_key), this);
            }
        }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    @Override
    public void onClick(View v) {

    }
}

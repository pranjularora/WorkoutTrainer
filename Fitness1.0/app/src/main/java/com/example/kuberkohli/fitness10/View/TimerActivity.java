package com.example.kuberkohli.fitness10.View;

import android.animation.ObjectAnimator;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuberkohli.fitness10.R;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    TextToSpeech t1;
    TextView ed1;
    Button b1;
    ImageView img1,img2;
    ProgressBar p1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ed1 = (TextView) findViewById(R.id.textView);
        img1 = (ImageView) findViewById(R.id.youtube_icon);
        img2 = (ImageView) findViewById(R.id.gifTest);
        img1.setVisibility(View.INVISIBLE);
        img2.setVisibility(View.INVISIBLE);
        b1 = (Button) findViewById(R.id.button);
        p1 = (ProgressBar) findViewById(R.id.progressBar2);
        final float f = (float) 1.05;
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                    t1.setSpeechRate(f);
                    t1.setPitch(f);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ed1.getText().toString();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                ObjectAnimator anim = ObjectAnimator.ofInt(p1, "progress", 0, 100);
                anim.setDuration(105000);
                anim.setInterpolator(new DecelerateInterpolator());
                anim.start();
                new CountDownTimer(31000, 1000) {
                    public void onFinish() {
                        b1.setEnabled(true);
                        String speak = "Exercise Complete";
                        t1.speak(speak, TextToSpeech.QUEUE_FLUSH, null);
                    }

                    public void onTick(long millisUntilFinished) {
                        b1.setEnabled(false);
                        String speak = Long.toString(millisUntilFinished/1000);
                        ed1.setText(Long.toString(millisUntilFinished/1000));
                        t1.speak(speak, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }.start();

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        t1.stop();
        t1.shutdown();
    }
}

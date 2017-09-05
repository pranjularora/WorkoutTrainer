package com.example.kuberkohli.fitness10.View;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kuberkohli.fitness10.R;

import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class TimerFragment extends android.support.v4.app.Fragment {

    TextToSpeech t1;
    TextView exerciseNameView;
    TextView ed1;
    Button b1;
    ImageView youtube;
    ImageView gifTest;
    ProgressBar p1;
    ObjectAnimator anim;
    CountDownTimer c1;
    int reps;
    String exerName;
    int lag = 0;

    boolean youtubeFlag = false;
    final float f = (float) 1.05;

    public TimerFragment() {
        // Required empty public constructor
    }

    public static TimerFragment newInstance(int reps, String ExerciseName, String gifUrl) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putSerializable("exerciseName", ExerciseName);
        args.putSerializable("exerciseReps", reps);
        args.putSerializable("gifUrl", gifUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootview = inflater.inflate(R.layout.timer, container, false);

        String gif_url;
        exerciseNameView = (TextView) rootview.findViewById(R.id.exerciseName);
        if (getArguments() != null) {
            reps = (int) getArguments().getSerializable("exerciseReps");
            exerName = (String) getArguments().getSerializable("exerciseName");
            gif_url = (String) getArguments().getSerializable("gifUrl");
        } else {
            return rootview;
        }
        exerciseNameView.setText(exerName);
        ed1 = (TextView) rootview.findViewById(R.id.textView);
        gifTest = (ImageView) rootview.findViewById(R.id.gifTest);
        b1 = (Button) rootview.findViewById(R.id.button);
        youtube = (ImageView) rootview.findViewById(R.id.youtube_icon);
        p1 = (ProgressBar) rootview.findViewById(R.id.progressBar2);

        ed1.setText(String.valueOf(reps));

        Glide
                .with(this) // replace with 'this' if it's in activity
                .load(gif_url)
                .asGif()
                .error(R.drawable.youtube_video) // show error drawable if the image is not a gif
                .into(gifTest);


        t1 = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                    t1.setSpeechRate(f);
                    t1.setPitch(f);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubeFlag = true;
                anim = ObjectAnimator.ofInt(p1, "progress", 0, 100);
                String toSpeak = String.valueOf((reps + 1));
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                anim.setDuration(reps * 33 * 100);
                anim.setInterpolator(new DecelerateInterpolator());
                anim.start();

                c1 = new CountDownTimer(reps * 1000 + 1000, 1000) {
                    public void onFinish() {
                        b1.setEnabled(true);
                        String speak = "Exercise Complete";
                        t1.speak(speak, TextToSpeech.QUEUE_FLUSH, null);
                        youtubeFlag = false;
                    }

                    public void onTick(long millisUntilFinished) {
                        b1.setEnabled(false);
                        String speak = Long.toString(millisUntilFinished / 1000);
                        ed1.setText(Long.toString(millisUntilFinished / 1000));
                        t1.speak(speak, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }.start();

            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (youtubeFlag) {
                    t1.stop();
                    t1.shutdown();
                    anim.cancel();
                    p1.setProgress(1);
                    int reps = (int) getArguments().getSerializable("exerciseReps");
                    ed1.setText(String.valueOf(reps));
                    b1.setEnabled(true);
                    c1.cancel();
                } else {
                    p1.setProgress(1);
                    int reps = (int) getArguments().getSerializable("exerciseReps");
                    ed1.setText(String.valueOf(reps));
                }
                Intent intent = new Intent(getContext(), Youtube1Activity.class);
                intent.putExtra("url", "HoIIMxZlUY8");
                startActivity(intent);

            }
        });

        return rootview;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(!b1.isEnabled())
            b1.setEnabled(true);
        if (youtubeFlag) {
            lag = p1.getProgress();
            t1.stop();
            anim.cancel();
            c1.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        t1 = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                    t1.setSpeechRate(f);
                    t1.setPitch(f);
                }
            }
        });
        if (reps != Integer.parseInt(ed1.getText().toString())) {
            anim = ObjectAnimator.ofInt(p1, "progress", lag, 100);
            anim.setDuration(Integer.parseInt(ed1.getText().toString()) * 33 * 100);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.start();

            c1 = new CountDownTimer(Integer.parseInt(ed1.getText().toString()) * 1000, 1000) {
                public void onFinish() {
                    b1.setEnabled(true);
                    String speak = "Exercise Complete";
                    t1.speak(speak, TextToSpeech.QUEUE_FLUSH, null);
                    youtubeFlag = false;
                }

                public void onTick(long millisUntilFinished) {
                    b1.setEnabled(false);
                    String speak = Long.toString(millisUntilFinished / 1000);
                    ed1.setText(Long.toString(millisUntilFinished / 1000));
                    t1.speak(speak, TextToSpeech.QUEUE_FLUSH, null);
                }
            }.start();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(t1!= null) {
            t1.stop();
            t1.shutdown();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(t1!= null) {
            t1.stop();
            t1.shutdown();
        }
    }
}


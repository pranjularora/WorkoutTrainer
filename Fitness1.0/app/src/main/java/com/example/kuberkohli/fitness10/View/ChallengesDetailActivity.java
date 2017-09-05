package com.example.kuberkohli.fitness10.View;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.kuberkohli.fitness10.R;

import java.util.HashMap;

public class ChallengesDetailActivity extends AppCompatActivity implements ChallengesDetailFragment.ExerciseDataInterface {
    Fragment mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_detail);
        HashMap<String, ?> workoutDetails = (HashMap<String, ?>) getIntent().getSerializableExtra("workOutData");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.RecycleViewFragmentContainer_workout_details
                        ,ChallengesDetailFragment.newInstance(workoutDetails))

                .commit();

    }

    @Override
    public void ExerciseData(int reps, String exerciseName, String gifUrl) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.RecycleViewFragmentContainer_workout_details,
                        TimerFragment.newInstance(reps, exerciseName, gifUrl))
                .addToBackStack(null).commit();
    }
}

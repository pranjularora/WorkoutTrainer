package com.example.kuberkohli.fitness10.View;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kuberkohli.fitness10.Controller.ChallengesDetailViewAdapter;
import com.example.kuberkohli.fitness10.Model.WorkoutData;
import com.example.kuberkohli.fitness10.R;
import com.mikepenz.itemanimators.AlphaCrossFadeAnimator;

import java.util.HashMap;

public class ChallengesDetailFragment extends android.support.v4.app.Fragment {



    private WorkoutData workoutData;
    private HashMap<String, ?> workoutData_map;

    private static RecyclerView recView1;
    private static ChallengesDetailViewAdapter adapter;
    android.support.v4.app.Fragment frag;
    android.support.v4.app.FragmentTransaction trans;

    public ChallengesDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }


    public interface ExerciseDataInterface{
        void ExerciseData(int reps, String exerciseName, String gifUrl);
    }

    ExerciseDataInterface interfaceExerciseData;

    public static ChallengesDetailFragment newInstance(HashMap<String, ?> workoutData) {
        ChallengesDetailFragment fragment = new ChallengesDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("key", workoutData);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_challanges_details, container, false);

        recView1 = (RecyclerView) view.findViewById(R.id.recviewer2);
        recView1.setLayoutManager(new LinearLayoutManager(getContext()));
        interfaceExerciseData = (ExerciseDataInterface) view.getContext();
        if(getArguments() != null)
            workoutData_map =  (HashMap<String, ?>) getArguments().getSerializable("key");
        else
            return view;


        final String exer1 =  (String) workoutData_map.get("exercise1");

        AlphaCrossFadeAnimator animator = new AlphaCrossFadeAnimator();
        animator.setAddDuration(700);
        animator.setRemoveDuration(700);
        animator.setChangeDuration(700);
        recView1.setItemAnimator(animator);

        adapter = new ChallengesDetailViewAdapter(getContext(), workoutData_map);
        recView1.setAdapter(adapter);
        setHasOptionsMenu(true);

        adapter.setItemClickCallBack(new ChallengesDetailViewAdapter.itemClickCallBack() {
            @Override
            public void onItemClick(View v, int position) {
                position++;
                String exerciseClicked = "exercise" + position;
                String repsClicked = "exercise" + position + "_reps";
                String clickedExerciseName = (String) workoutData_map.get(exerciseClicked);
                String clickedReps = (String) workoutData_map.get(repsClicked);
                String exer_url = "exercise" + position + "_url";
                String gif_url = (String) workoutData_map.get(exer_url);
                normalClick(Integer.parseInt(clickedReps), clickedExerciseName, gif_url);
            }
        });


        return view;
    }

    // We need to pass number of Reps(to set timer) and Name of the exercise
    public void normalClick(int reps, String exerciseName, String gifUrl){
        interfaceExerciseData.ExerciseData(reps, exerciseName, gifUrl);

    }


}

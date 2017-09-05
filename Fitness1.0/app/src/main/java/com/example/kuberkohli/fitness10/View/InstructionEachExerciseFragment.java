package com.example.kuberkohli.fitness10.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kuberkohli.fitness10.R;

import java.util.HashMap;

/**
 * Created by pranjularora on 4/23/17.
 */

public class InstructionEachExerciseFragment extends  android.support.v4.app.Fragment {

    private TextView exerciseHeadingText;
    private TextView exerciseDescrText;
    private ImageView GifView;
    private HashMap<String, ?> exercise_map_instruction;

    public InstructionEachExerciseFragment() {
        // Required empty public constructor
    }

    public static InstructionEachExerciseFragment newInstance(String clickedExerciseName
            , String exerciseDescription, String gif_url) {

        InstructionEachExerciseFragment fragment = new InstructionEachExerciseFragment();
        Bundle args = new Bundle();
        args.putSerializable("ExerciseName", clickedExerciseName);
        args.putSerializable("ExerciseDescription", exerciseDescription);
        args.putSerializable("ExerciseGif", gif_url);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruction_each_exercise, container, false);
        exerciseHeadingText = (TextView) view.findViewById(R.id.exerciseHeading);
        exerciseDescrText = (TextView) view.findViewById(R.id.exerciseDescription);
        GifView = (ImageView) view.findViewById(R.id.gifTest_Instruction);

        String url = (String) getArguments().getSerializable("ExerciseGif");

        if(getArguments() != null) {
            String exerName = (String) getArguments().getSerializable("ExerciseName");
            String exerDesc = (String) getArguments().getSerializable("ExerciseDescription");
            exerciseHeadingText.setText(exerName);
            exerciseDescrText.setText(exerDesc);
            exerciseHeadingText.setTransitionName(exerName); // set transition name
        }
        else
            return view;

        Glide
                .with(this) // replace with 'this' if it's in activity
                .load(url)
                .asGif()
                .error(R.drawable.youtube_video) // show error drawable if the image is not a gif
                .into(GifView);

        return view;
    }



}

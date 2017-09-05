package com.example.kuberkohli.fitness10.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuberkohli.fitness10.R;

import java.util.HashMap;

public class InstrcutionTabActivity extends AppCompatActivity implements InstructionDetailFragment.InterfaceInstructionEachExer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrcution_tab);

            HashMap<String, ?> workoutDetailsInstructions = (HashMap<String, ?>) getIntent()
                    .getSerializableExtra("workOutDataInstruction");

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.instruction_detail_frame,
                            InstructionDetailFragment.newInstance(workoutDetailsInstructions))
                    .commit();


    }

    @Override
    public void EachIntructionExerDetail(String clickedExerciseName, String exerciseDescription
            , String gif_url, View view) {

        TextView img = (TextView) view.findViewById(R.id.instruction_detail_textView);
        InstructionEachExerciseFragment fragment = InstructionEachExerciseFragment
                .newInstance(clickedExerciseName, exerciseDescription, gif_url);

        fragment.setSharedElementEnterTransition(new DetailsTransition());

        fragment.setSharedElementReturnTransition(new DetailsTransition());

        getSupportFragmentManager().beginTransaction()
               // .addSharedElement(img, img.getTransitionName())
                .replace(R.id.instruction_detail_frame, fragment)
                .addToBackStack(null)
                .commit();
    }
}

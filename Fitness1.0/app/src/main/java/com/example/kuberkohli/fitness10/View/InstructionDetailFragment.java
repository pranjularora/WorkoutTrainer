package com.example.kuberkohli.fitness10.View;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kuberkohli.fitness10.Controller.InstructionDetailViewAdapter;
import com.example.kuberkohli.fitness10.R;
import com.example.kuberkohli.fitness10.animation.AnimationFactory;
import com.mikepenz.itemanimators.SlideDownAlphaAnimator;

import java.util.HashMap;


public class InstructionDetailFragment extends android.support.v4.app.Fragment {


    private static RecyclerView recView1;
    private static InstructionDetailViewAdapter adapter;

    private HashMap<String, ?> workoutData_map_instruction;
    android.support.v4.app.Fragment frag;
    android.support.v4.app.FragmentTransaction trans;

    public InstructionDetailFragment() {
        // Required empty public constructor
    }


    public interface InterfaceInstructionEachExer{
        void EachIntructionExerDetail(String clickedExerciseName, String exerciseDescription, String gif_url, View view);
    }

    InterfaceInstructionEachExer interfaceInstructionEachExer;

    public static InstructionDetailFragment newInstance(HashMap<String, ?> workoutDataInstructions) {
        InstructionDetailFragment fragment = new InstructionDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("key", workoutDataInstructions);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_instrcution_tab, container, false);

        recView1 = (RecyclerView) view.findViewById(R.id.recviewer_inst_det);
        recView1.setLayoutManager(new LinearLayoutManager(getContext()));

        if(getArguments() != null)
            workoutData_map_instruction =  (HashMap<String, ?>) getArguments().getSerializable("key");
        else
            return view;

        interfaceInstructionEachExer = (InterfaceInstructionEachExer) view.getContext();
        final android.widget.ViewAnimator viewAnimator = (android.widget.ViewAnimator) view.findViewById(R.id.animator1);
        AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);

        SlideDownAlphaAnimator animator = new SlideDownAlphaAnimator();
        animator.setAddDuration(700);
        animator.setRemoveDuration(700);
        animator.setChangeDuration(700);
        recView1.setItemAnimator(animator);

        adapter = new InstructionDetailViewAdapter(getContext(), workoutData_map_instruction);
        recView1.setAdapter(adapter);
        setHasOptionsMenu(true);

        adapter.setItemClickCallBack(new InstructionDetailViewAdapter.itemClickCallBack() {
            @Override
            public void onItemClick(View view, int position){
                position++;
                String exerciseClicked = "exercise" + position;
                String description = "exercise" + position + "_descp";
                String clickedExerciseName = (String) workoutData_map_instruction.get(exerciseClicked);
                String exerciseDescription  = (String) workoutData_map_instruction.get(description);
                String exer_url = "exercise" + position + "_url";
                String gif_url = (String) workoutData_map_instruction.get(exer_url);
                normalClick(clickedExerciseName, exerciseDescription, gif_url, view);
            }
        });
        return view;
    }

    public void normalClick(String clickedExerciseName, String exerciseDescription, String gif_url, View view){
        interfaceInstructionEachExer.EachIntructionExerDetail(clickedExerciseName, exerciseDescription, gif_url, view);

    }


}

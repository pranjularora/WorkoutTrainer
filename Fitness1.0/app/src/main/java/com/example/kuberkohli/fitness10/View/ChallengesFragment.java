package com.example.kuberkohli.fitness10.View;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.kuberkohli.fitness10.Controller.MyFirebaseRecylerAdapter;
import com.example.kuberkohli.fitness10.Model.WorkoutData;
import com.example.kuberkohli.fitness10.Model.Workouts;
import com.example.kuberkohli.fitness10.R;
import com.example.kuberkohli.fitness10.animation.AnimationFactory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;

public class ChallengesFragment extends android.support.v4.app.Fragment {


    private static RecyclerView recyclerView;

    DatabaseReference childRef;
    private static MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    WorkoutData workoutData;

    android.support.v4.app.Fragment frag;
    FragmentTransaction trans;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public ChallengesFragment() {
        // Required empty public constructor
    }

    public static ChallengesFragment newInstance(int i) {
        Bundle args = new Bundle();
        ChallengesFragment fragment = new ChallengesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface InterfaceWorkoutData{
        void DisplayWorkoutData(int position, HashMap<String, ?> movie);
    }

    InterfaceWorkoutData interfaceWorkoutDataData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_challanges, container, false);
        childRef =  FirebaseDatabase.getInstance().getReference().child("Gym").getRef();


        myFirebaseRecylerAdapter = new MyFirebaseRecylerAdapter(Workouts.class, R.layout.challenges_list,
                MyFirebaseRecylerAdapter.WorkoutViewHolder.class, childRef, getContext());

        workoutData = new WorkoutData();
        recyclerView = (RecyclerView) view.findViewById(R.id.recviewer1);
        interfaceWorkoutDataData =  (InterfaceWorkoutData) view.getContext();

        final android.widget.ViewAnimator viewAnimator = (android.widget.ViewAnimator) view.findViewById(R.id.animator);
        AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SlideInOutBottomItemAnimator animator = new SlideInOutBottomItemAnimator(recyclerView);
        recyclerView.setItemAnimator(animator);
        recyclerView.setAdapter(myFirebaseRecylerAdapter);

        if (workoutData.getSize() == 0) {
            workoutData.setAdapter(myFirebaseRecylerAdapter);
            workoutData.setContext(getActivity());
            workoutData.initializeDataFromCloud();
        }
        setHasOptionsMenu(true);

        myFirebaseRecylerAdapter.SetOnItemClickListner(new MyFirebaseRecylerAdapter.RecyclerItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                final HashMap<String, ?> workoutDetails = (HashMap<String, ?>) workoutData.getItem(position);
                normalClick(position, workoutDetails);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }

            @Override
            public void onOverFlowMenuClick(View v, final int position){
                PopupMenu popup = new PopupMenu(getActivity(), v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        HashMap movie;
                        switch (item.getItemId()){
                            default:
                                return false;
                        }
                    }
                });
            }

            public void normalClick(int pos, HashMap<String, ?> mov){
                interfaceWorkoutDataData.DisplayWorkoutData(pos, mov);
            }

            @Override
            public void onCheckBoxClick(View v, int position) {

            }
        });

        return view;
    }

}

package com.example.kuberkohli.fitness10.View;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.kuberkohli.fitness10.Controller.MyFirebaseRecyclerAdapterInstruction;
//import com.example.kuberkohli.fitness10.Controller.MyFirebaseRecylerAdapter;
import com.example.kuberkohli.fitness10.Model.WorkoutDataInstruction;
import com.example.kuberkohli.fitness10.Model.WorkoutsInstruction;
import com.example.kuberkohli.fitness10.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class InstructionTabFragment extends android.support.v4.app.Fragment {


    private static RecyclerView recView1;
    android.support.v4.app.Fragment frag;
    android.support.v4.app.FragmentTransaction trans;

    DatabaseReference childRef;
    private static MyFirebaseRecyclerAdapterInstruction myFirebaseRecyclerAdapterInstruction;
    WorkoutDataInstruction workoutDataInstruction;


    public InstructionTabFragment() {
        // Required empty public constructor
    }

    public static InstructionTabFragment newInstance(int i) {
        Bundle args = new Bundle();
        InstructionTabFragment fragment = new InstructionTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface InterfaceWorkoutDataInstructionPage{
        void DisplayWorkoutDataInstruction(int position, HashMap<String, ?> movie);
    }

    InterfaceWorkoutDataInstructionPage interfaceWorkoutDataInstructionPage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_instruction_tab, container, false);
        recView1 = (RecyclerView) view.findViewById(R.id.recviewer_inst);

        childRef =  FirebaseDatabase.getInstance().getReference().child("Gym").getRef();

        myFirebaseRecyclerAdapterInstruction = new MyFirebaseRecyclerAdapterInstruction(WorkoutsInstruction.class
                , R.layout.instruction_list,
                MyFirebaseRecyclerAdapterInstruction.WorkoutViewHolder.class, childRef, getContext());

        workoutDataInstruction = new WorkoutDataInstruction();

        interfaceWorkoutDataInstructionPage =  (InterfaceWorkoutDataInstructionPage) view.getContext();

        recView1.setLayoutManager(new LinearLayoutManager(getContext()));

        recView1.setAdapter(myFirebaseRecyclerAdapterInstruction);

        if (workoutDataInstruction.getSize() == 0) {
            workoutDataInstruction.setAdapter(myFirebaseRecyclerAdapterInstruction);
            //getApplicationContext()-activity is used movieData.initializeDataFromCloud();
            workoutDataInstruction.setContext(getActivity());
            workoutDataInstruction.initializeDataFromCloud();
        }

        setHasOptionsMenu(true);


        myFirebaseRecyclerAdapterInstruction.SetOnItemClickListner(new MyFirebaseRecyclerAdapterInstruction.RecyclerItemClickListener(){

            @Override
            public void onItemClick(View view, final int position) {
                final HashMap<String, ?> workoutDetails = (HashMap<String, ?>) workoutDataInstruction.getItem(position);
                final String id = (String) workoutDetails.get("id");
                DatabaseReference ref = workoutDataInstruction.getFireBaseRef();
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
                interfaceWorkoutDataInstructionPage.DisplayWorkoutDataInstruction(pos, mov);
            }

            @Override
            public void onCheckBoxClick(View v, int position) {
            }
        });

        return view;
    }

}

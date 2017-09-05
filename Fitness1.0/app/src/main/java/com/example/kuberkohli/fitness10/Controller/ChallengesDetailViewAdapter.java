package com.example.kuberkohli.fitness10.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kuberkohli.fitness10.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChallengesDetailViewAdapter extends RecyclerView.Adapter<ChallengesDetailViewAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    static HashMap<String, ?> workoutList;
    private Context context;
    private int count = 1;
    private itemClickCallBack itemClickCallBack;

    public interface itemClickCallBack {
        void onItemClick(View v, int p);
    }

    public void setItemClickCallBack(final itemClickCallBack itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }

    public ChallengesDetailViewAdapter(Context c, HashMap<String, ?> workoutList) {
        this.inflater = LayoutInflater.from(c);
        context = c;
        this.workoutList = workoutList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.challenges_list_start, parent, false);
                return new MyViewHolder(view);

        }
        view = inflater.inflate(R.layout.challenges_detail_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }


    // onBindHolder is responsible for binding the views inside the card view.
    // A map has been passed to the new activity which pass the map to the fragment
    // In this adapter, exerciseName and exerciseReps are calcuted using keys in DB, which is Exer1, Exer1_reps
    // for on click event, we can get the position and based on that position, again data gets fetched from the map.
    // On click event is defined in the ChallengesDetailFragment.java
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String getExerciseID = "exercise" + count;
        String getRepsID = "exercise" + count + "_reps";
        String exercise = (String) workoutList.get(getExerciseID);
        String exercise_reps = (String) workoutList.get(getRepsID);
        holder.exerciseTextView.setText(exercise);
        holder.exercise_repsTextView.setText(exercise_reps);
        count ++;


    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView exerciseTextView;
        private TextView exercise_repsTextView;
        private TextView label;
        private TextView desc;
        private View container;

        public MyViewHolder(View itemView) {
            super(itemView);
            exerciseTextView = (TextView) itemView.findViewById(R.id.list_item_exercise);
            exercise_repsTextView = (TextView) itemView.findViewById(R.id.list_item_reps);
            label = (TextView) itemView.findViewById(R.id.list_item_name);
            desc = (TextView) itemView.findViewById(R.id.list_item_desc);

            container = itemView.findViewById(R.id.list_item_layout);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickCallBack.onItemClick(v, getAdapterPosition());
        }

    }

}

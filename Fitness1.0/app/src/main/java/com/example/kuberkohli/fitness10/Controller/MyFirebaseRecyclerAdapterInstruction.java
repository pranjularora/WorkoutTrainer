package com.example.kuberkohli.fitness10.Controller;

/**
 * Created by pranjularora on 4/19/17.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuberkohli.fitness10.Model.WorkoutsInstruction;
import com.example.kuberkohli.fitness10.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class MyFirebaseRecyclerAdapterInstruction extends FirebaseRecyclerAdapter<WorkoutsInstruction, MyFirebaseRecyclerAdapterInstruction.WorkoutViewHolder> {
    private Context mContext;
    private List<Map<String, ?>> movieDataSet;
    public boolean check = false;


    static RecyclerItemClickListener movieItemClickListener;

    public MyFirebaseRecyclerAdapterInstruction(Class<WorkoutsInstruction> modelClass, int modelLayout,
                                                Class<MyFirebaseRecyclerAdapterInstruction.WorkoutViewHolder> holder, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, holder, ref);
        this.mContext = context;
        setHasStableIds(true); // kuber
    }

    public interface RecyclerItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);

        void onOverFlowMenuClick(View v, int position);

        public void onCheckBoxClick(View v, int position);
    }

    public void SetOnItemClickListner(final  RecyclerItemClickListener mItemClickListner) {
        this.movieItemClickListener = mItemClickListner;
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected void populateViewHolder(WorkoutViewHolder movieViewHolder, WorkoutsInstruction workouts, int position) {
        String name = (String) workouts.getName();

        movieViewHolder.title.setText((String) name);
        Picasso.with(mContext).load((String) workouts.getUrl()).into(movieViewHolder.movieImage);

    }

    @Override
    public WorkoutsInstruction getItem(int position) {
        return super.getItem(position);
    }



    //TODO: Populate ViewHolder and add listeners.
    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        public ImageView movieImage;

        public TextView title;
        public TextView description;
        private View container;
        public WorkoutViewHolder(View v) {
            super(v);
            movieImage = (ImageView) v.findViewById(R.id.list_item_imageview_instruction);
            title = (TextView) v.findViewById(R.id.title_instruction);
            container = v.findViewById(R.id.list_item_layout_instruction);

            // by this we are just registering the click event on the whole view i.e. view in our case
            container.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    if(movieItemClickListener != null){
                        if(getAdapterPosition() != RecyclerView.NO_POSITION){
                            // to make a click event generic, we are not performing any action here. we are just calling
                            // another function..
                            movieItemClickListener.onItemClick(v, getAdapterPosition());
                        }
                    }
                }
            });



            v.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    if(movieItemClickListener != null){
                        if(getAdapterPosition() != RecyclerView.NO_POSITION){
                            // to make a click event generic, we are not performing any action here. we are just calling
                            // another function..
                            movieItemClickListener.onItemLongClick(v, getAdapterPosition());
                        }
                    }
                    return true;
                }
            });

        }
    }

}



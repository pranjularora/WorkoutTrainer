package com.example.kuberkohli.fitness10.Controller; //change the package name to your project's package name

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuberkohli.fitness10.Model.Workouts;
import com.example.kuberkohli.fitness10.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class MyFirebaseRecylerAdapter extends FirebaseRecyclerAdapter<Workouts, MyFirebaseRecylerAdapter.WorkoutViewHolder> {

    private Context mContext;
    private List<Map<String, ?>> movieDataSet;
    public boolean check = false;


    static RecyclerItemClickListener movieItemClickListener;

    public MyFirebaseRecylerAdapter(Class<Workouts> modelClass, int modelLayout,
                                    Class<WorkoutViewHolder> holder, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, holder, ref);
        this.mContext = context;
        //setHasStableIds(true); // kuber
    }

    public interface RecyclerItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);

        void onOverFlowMenuClick(View v, int position);

        public void onCheckBoxClick(View v, int position);
    }

    public void SetOnItemClickListner(final  MyFirebaseRecylerAdapter.RecyclerItemClickListener mItemClickListner) {
        this.movieItemClickListener = mItemClickListner;
    }

    @Override
    protected void populateViewHolder(WorkoutViewHolder movieViewHolder, Workouts workouts, int positions) {

        //TODO: Populate viewHolder by setting the movie attributes to cardview fields

        String name = (String) workouts.getName();

        movieViewHolder.title.setText((String) name);
        Picasso.with(mContext).load((String) workouts.getUrl()).into(movieViewHolder.movieImage);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Workouts getItem(int position) {
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
            movieImage = (ImageView) v.findViewById(R.id.list_item_imageview);
            title = (TextView) v.findViewById(R.id.list_item_name);
            container = v.findViewById(R.id.list_item_layout);

            // by this we are just registering the click event on the whole view i.e. view in our case
            container.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    if(movieItemClickListener != null){
                        if(getAdapterPosition() != RecyclerView.NO_POSITION){
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


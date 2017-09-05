package com.example.kuberkohli.fitness10.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kuberkohli.fitness10.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstructionDetailViewAdapter extends RecyclerView.Adapter<InstructionDetailViewAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    static HashMap<String, ?> workoutListInstruction;
   private Context mContext;;
    private itemClickCallBack itemClickCallBack;
    private int count = 1;

    public interface itemClickCallBack {
        void onItemClick(View v, int p);
    }

    public void setItemClickCallBack(final itemClickCallBack itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }

    public InstructionDetailViewAdapter(Context c, HashMap<String, ?> workoutList) {
        this.inflater = LayoutInflater.from(c);
        mContext = c;
        this.workoutListInstruction = workoutList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.instruction_detail_list, parent, false);
                return new MyViewHolder(view);

        }
        view = inflater.inflate(R.layout.instruction_detail_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String getExerciseID = "exercise" + count;
        String imageUrl = "exercise" + count + "_url";
        String exercise = (String) workoutListInstruction.get(getExerciseID);
        holder.instruction_card_textView.setTransitionName(exercise); // set transition name
        holder.instruction_card_textView.setText(exercise);

        Glide
                .with(mContext) // replace with 'this' if it's in activity
                .load((String) workoutListInstruction.get(imageUrl))
                .asGif()
                .error(R.drawable.youtube_video) // show error drawable if the image is not a gif
                .into(holder.instruction_card_imageView);
        count ++;

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView instruction_card_textView;
        private ImageView instruction_card_imageView;
        private View container;

        public MyViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.list_item_layout);
            container.setOnClickListener(this);
            instruction_card_textView = (TextView) itemView.findViewById(R.id.instruction_detail_textView);
            instruction_card_imageView = (ImageView) itemView.findViewById(R.id.instruction_detail_imageView);
        }

        @Override
        public void onClick(View v) {
            itemClickCallBack.onItemClick(v, getAdapterPosition());
        }

    }

}

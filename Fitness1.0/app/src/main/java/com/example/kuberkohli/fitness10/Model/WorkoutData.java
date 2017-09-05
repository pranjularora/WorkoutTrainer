package com.example.kuberkohli.fitness10.Model;

/**
 * Created by pranjularora on 4/17/17.
 */
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.kuberkohli.fitness10.Controller.MyFirebaseRecylerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// this is same as movie Data
public class WorkoutData {
    List<Map<String,?>> workoutList;
    DatabaseReference mRef;
    MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    Context mContext;

    public void setAdapter(MyFirebaseRecylerAdapter mAdapter) {
        myFirebaseRecylerAdapter = mAdapter;
    }

    public void removeItemFromServer(Map<String,?> movie){
        if(movie!=null){
            String id = (String)movie.get("id");
            mRef.child(id).removeValue();
        }
    }

    public void addItemToServer(Map<String,?> movie){
        if(movie!=null){
            String id = (String) movie.get("id");
            mRef.child(id).setValue(movie);
        }
    }

    public DatabaseReference getFireBaseRef(){
        return mRef;
    }
    public void setContext(Context context){
        mContext = context;
    }

    public List<Map<String, ?>> getworkoutList() {
        return workoutList;
    }

    public int getSize(){
        return workoutList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < workoutList.size()){
            return (HashMap) workoutList.get(i);
        } else return null;
    }


    public void onItemRemovedFromCloud(HashMap item){
        int position = -1;
        String id=(String)item.get("id");
        for(int i=0;i<workoutList.size();i++){
            HashMap movie = (HashMap)workoutList.get(i);
            String mid = (String)movie.get("id");
            if(mid.equals(id)){
                position= i;
                break;
            }
        }
        if(position != -1){
            workoutList.remove(position);
            Toast.makeText(mContext, "Item Removed:" + id, Toast.LENGTH_SHORT).show();

        }
    }

    public void onItemAddedToCloud(HashMap item){
        int insertPosition = 0;
        String id=(String)item.get("id");
        for(int i=0;i<workoutList.size();i++){
            HashMap movie = (HashMap)workoutList.get(i);
            String mid = (String)movie.get("id");
            if(mid.equals(id)){
                return;
            }
            if(mid.compareTo(id)<0){
                insertPosition=i+1;
            }else{
                break;
            }
        }
        workoutList.add(insertPosition,item);
        //  Toast.makeText(mContext, "Item added:" + id, Toast.LENGTH_SHORT).show();

    }

    public void onItemUpdatedToCloud(HashMap item){
        String id=(String)item.get("id");
        for(int i=0;i<workoutList.size();i++){
            HashMap movie = (HashMap)workoutList.get(i);
            String mid = (String)movie.get("id");
            if(mid.equals(id)){
                workoutList.remove(i);
                workoutList.add(i,item);
                Log.d("My Test: NotifyChanged",id);

                break;
            }
        }

    }
    public void initializeDataFromCloud() {
        workoutList.clear();
        mRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildAdded", dataSnapshot.toString());
                HashMap<String,String> workout = (HashMap<String,String>)dataSnapshot.getValue();
                onItemAddedToCloud(workout);
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildChanged", dataSnapshot.toString());
                HashMap<String,String> workout = (HashMap<String,String>)dataSnapshot.getValue();
                onItemUpdatedToCloud(workout);
            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Log.d("MyTest: OnChildRemoved", dataSnapshot.toString());
                HashMap<String,String> workout = (HashMap<String,String>)dataSnapshot.getValue();
                onItemRemovedFromCloud(workout);
            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public WorkoutData(){

        workoutList = new ArrayList<Map<String,?>>();
        mRef = FirebaseDatabase.getInstance().getReference().child("Gym").getRef();
        myFirebaseRecylerAdapter = null;
        mContext = null;

    }


    public int findFirst(String query){

        for(int i=0;i<workoutList.size();i++){
            HashMap hm = (HashMap)getworkoutList().get(i);
            if(((String)hm.get("name")).toLowerCase().contains(query.toLowerCase())){
                return i;
            }
        }
        return 0;

    }
}



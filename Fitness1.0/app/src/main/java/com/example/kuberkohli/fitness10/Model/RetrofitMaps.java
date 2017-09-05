package com.example.kuberkohli.fitness10.Model;
import com.example.kuberkohli.fitness10.Model.POJO.Example;
/**
 * Created by pranjularora on 4/25/17.
 */



import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;


public interface RetrofitMaps {

    @GET("api/directions/json?key=AIzaSyCqbWwSRSUV1IqPJLwmcoSlAUn8aPl8pHw")
    Call<Example> getDistanceDuration(@Query("units") String units, @Query("origin") String origin
            , @Query("destination") String destination, @Query("mode") String mode);

}

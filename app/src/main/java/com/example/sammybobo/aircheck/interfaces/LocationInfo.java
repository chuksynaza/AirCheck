package com.example.sammybobo.aircheck.interfaces;

import com.example.sammybobo.aircheck.Model.CitySearch;
import com.example.sammybobo.aircheck.Model.Frequency;
import com.example.sammybobo.aircheck.Model.GibsLink;
import com.example.sammybobo.aircheck.Model.LocationModel;
import com.example.sammybobo.aircheck.Model.ReturnedPushCrowdsource;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Agbede on 19/04/2016.
 */
public interface LocationInfo {
    @GET("test2.php")
    Observable<LocationModel> getLocationInfo1 (
            @Query("splashapi")String splash,
           @Query("longitude")double longitude,
           @Query("latitude")double latitude
    );

    @GET("userinput.php")
    Observable<ReturnedPushCrowdsource> push_crowd_source (
            @Query("userInput")String userInput,
            @Query("longitude")double longitude,
            @Query("latitude")double latitude,
            @Query("IMEI")String IMEI,
            @Query("cough_intensity")int cough_intensity,
            @Query("shortness_of_breath_intensity")int sob_intensity,
            @Query("wheezing")int wheezing_intensity,
            @Query("sneezing")int sneezing_intensity,
            @Query("nasal_obstruction")int nasal_obs_intensity,
            @Query("itching_eyes")int itchy_eyes_intensity
    );


    @GET("frequency.php")
    Observable<List<Frequency>> getFrequency();

    @GET("city.php")
    Observable<List<CitySearch>> citySearch(@Query("city") String city);

    @GET("GIBS.php")
    Observable<GibsLink> getGibsLink(@Query("lat")double latitude,
                                     @Query("lon")double longitude,
                                     @Query("_date") String _date);
}

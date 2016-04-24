package com.example.sammybobo.aircheck;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sammybobo.aircheck.Model.Frequency;
import com.example.sammybobo.aircheck.interfaces.LocationInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Agbede on 22/04/2016.
 */
public class MapsPage extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{
    GoogleMap googleMap;
    GoogleApiClient googleApiClient;
    SupportMapFragment supportMapFragment;
    final String url = "https://app.chuksy.me/aircheck/engine/";
    LocationInfo inter;
    ArrayList<String> latitude;
    ArrayList<String> longitude;
    ArrayList<String> frequency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapspage);
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();

        inter = retrofit.create(LocationInfo.class);
        rx.Observable<List<Frequency>> getFrequency = inter.getFrequency();
        getFrequency.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Frequency>>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                               }

                               @Override
                               public void onNext(List<Frequency> frequency) {
                                   Log.d("ello", frequency.toArray().toString());
                                                    /*longitude.add(frequency.get(0).getLongitude());
                                                longitude.add(frequency.get(1).getLongitude());
                                                    Log.d("hello",frequency.get(1).getLongitude());
                                                Log.d("hello one", longitude.get(0));*/
                                   // latitude.add(frequency.get(0).getLatitude());
                                   // frequency1.add(frequency.get(0).getFREQUENCY());

                                                /*age.putStringArrayListExtra("longitude", longitude);
                                                age.putStringArrayListExtra("latitude", latitude);
                                                age.putStringArrayListExtra("frequency", frequency1);*/
                               }
                           });


        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.main_map);
        supportMapFragment.getMapAsync(this);
        Intent a = getIntent();
        latitude = a.getStringArrayListExtra("latitude");
        longitude = a.getStringArrayListExtra("longitude");
        frequency = a.getStringArrayListExtra("frequency");

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();



        //inter.
    }

    @Override
    public void onConnected(Bundle bundle) {
            Log.d("Hello world", "It gets here");

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location)
    {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {

    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    { Log.d("Hello world", "It gets here - the onMapReady");
        this.googleMap = googleMap;
        for (int i = 0; i <latitude.size(); i++){
            googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latitude.get(i)),Double.parseDouble(longitude.get(i)))));
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(latitude.get(i)),Double.parseDouble(longitude.get(i)))),1450, null);
        }

        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setMyLocationEnabled(true);

    }
}

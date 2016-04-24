package com.example.sammybobo.aircheck;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.sammybobo.aircheck.Model.LocationModel;
import com.example.sammybobo.aircheck.interfaces.LocationInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;
import java.util.concurrent.TimeUnit;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Agbede on 19/04/2016.
 */
public class Splash extends Activity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    ContentLoadingProgressBar progressBar;
    Location location;
    LocationRequest locationRequest;
    GoogleApiClient googleApiClient;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    final String url = "https://app.chuksy.me/aircheck/engine/";
    TelephonyManager telephonyManager;
    String imei;
    LocationInfo inter;
    rx.Observable<List<LocationModel>> loModel;

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

       // progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
//        progressBar.show();
//        progressBar.setProgress(15);
        googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
       // googleApiClient.connect();
        Intent base = new Intent(Splash.this, MainActivity.class);
        startActivity(base);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
        inter = retrofit.create(LocationInfo.class);

        sharedPreferences = getSharedPreferences("splash", MODE_PRIVATE);

        if (sharedPreferences.contains("sign_up")){
            Intent a = new Intent(Splash.this, MainActivity.class);
            startActivity(a);}
        else
        {

        }

    }

    @Override
    protected void onResume()
    {
        googleApiClient.connect();
        super.onResume();
    }

    public void splash_change()
    {
        Observable.timer(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(5)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        //progressBar.setProgress(100);
                        //progressBar.show();
                        ImageView imageView = (ImageView) findViewById(R.id.logo);
                      Intent move = new Intent(Splash.this, SignIn.class);
                        startActivity(move);
//                       ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(Splash.this, (View)                              imageView, "logo");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong)
                    {
                    }
                });
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        Log.d("Hello world", "It's in the connected form");
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location == null)
        {
            getLocationUpdates();
        }
        else
        {
            Log.d("Connected", location.getLatitude() + " " + location.getLongitude());
            rx.Observable<LocationModel> push_location = inter.getLocationInfo1("7hysysby8ryvr8wbywrvy", location.getLongitude(), location.getLatitude());

            push_location.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<LocationModel>() {
                        @Override
                        public void onCompleted() {
                            Intent base = new Intent(Splash.this, MainActivity.class);
                           // base.putExtra("hello")
                            startActivity(base);

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("Error is", e.toString());
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(LocationModel locationModels) {
                            editor.putString("city",locationModels.getCity());
                            editor.putString("temperature",locationModels.getTemperature().toString() );
                            editor.putString("humidity", locationModels.getHumidity().toString());
                            editor.commit();
                            Log.d("City", locationModels.getCity());
                            Log.d("Temperature", locationModels.getTemperature().toString());
                            Log.d("Humidity", locationModels.getHumidity().toString());
                        }
                    });
            Intent alpha = new Intent(Splash.this, SignIn.class);
            startActivity(alpha);
            getLocationUpdates();

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onLocationChanged(final Location location)
    {
            Log.d("location has changed", location.getLatitude()+" "+location.getLongitude());
        rx.Observable<LocationModel> push_location = inter.getLocationInfo1("7hysysby8ryvr8wbywrvy", location.getLongitude(), location.getLatitude());

        push_location.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<LocationModel>() {
                    @Override
                    public void onCompleted() {
                        Intent base = new Intent(Splash.this, MainActivity.class);
                        startActivity(base);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error is", e.toString());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(LocationModel locationModels) {
                        Log.d("City", locationModels.getCity());
                        Log.d("Temperature", locationModels.getTemperature().toString());
                        Log.d("Humidity", locationModels.getHumidity().toString());
                        editor.putString("city",locationModels.getCity());
                        editor.putString("temperature",locationModels.getTemperature().toString() );
                        editor.putString("humidity", locationModels.getHumidity().toString());
                        editor.commit();

                    }
                });


            splash_change();
    }

    private void getLocationUpdates() {
        Log.d("Owo bi","It's here");
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(15000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
                }
        }

package com.example.sammybobo.aircheck;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.sammybobo.aircheck.Model.ReturnedPushCrowdsource;
import com.example.sammybobo.aircheck.interfaces.LocationInfo;
import retrofit2.Retrofit;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Agbede on 20/04/2016.
 */
public class CrowdSource extends Activity {
    ProgressDialog progressDialog;
    LocationInfo locationInfo;
    Button submit_button;
    final static String url = "https://app.chuksy.me/aircheck/engine/";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    double longitude;
    double latitude;
    String IMEI;
    int cough_intensity;
    int sob_intensity;
    int wheezing_intensity;
    int sneezing_intensity;
    int nasal_obs_intensity;
    int itchy_eyes_intensity;
    String userInput;
    AppCompatSeekBar cough;
    AppCompatSeekBar shortness_of_breath;
    AppCompatSeekBar wheezing;
    AppCompatSeekBar sneezing;
    AppCompatSeekBar nasOb;
    AppCompatSeekBar itchyEyes;
    TextView cough_value;
    TextView shortness_of_breath_value;
    TextView wheezing_value;
    TextView sneezing_value;
    TextView nasOb_value;
    TextView itchyEyes_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.crowd_source);
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        sharedPreferences = getSharedPreferences("splash", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        longitude = sharedPreferences.getFloat("last_longitude",0);
        latitude = sharedPreferences.getFloat("last_latitude", 0);
        Log.d("Hello world", longitude + " " + latitude);
        cough_value = (TextView)findViewById(R.id.cough_value);
        shortness_of_breath_value = (TextView)findViewById(R.id.sob_value);
        wheezing_value = (TextView)findViewById(R.id.wheezing_value);
        sneezing_value = (TextView)findViewById(R.id.sneezing_value);
        nasOb_value = (TextView)findViewById(R.id.nob_value);
        itchyEyes_value = (TextView)findViewById(R.id.itchy_eyes_value);
        cough = (AppCompatSeekBar)findViewById(R.id.cough_seekbar);
        shortness_of_breath = (AppCompatSeekBar)findViewById(R.id.shortness_of_breath_seekbar);
        wheezing = (AppCompatSeekBar)findViewById(R.id.wheezing_seekbar);
        sneezing = (AppCompatSeekBar)findViewById(R.id.sneezing_seekbar);
        nasOb = (AppCompatSeekBar)findViewById(R.id.nasal_obstruction_seekbar);
        itchyEyes = (AppCompatSeekBar)findViewById(R.id.itchy_eyes_seekbar);
        userInput = "8hbhjgi7ggkvyuyy6";
        submit_button = (Button)findViewById(R.id.submit_crowd_sourced);
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
        locationInfo = retrofit.create(LocationInfo.class);


        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Observable<ReturnedPushCrowdsource> push_crowdy = locationInfo.push_crowd_source(userInput, 5.35, 566.4, IMEI, cough_intensity, sob_intensity, wheezing_intensity, sneezing_intensity, nasal_obs_intensity, itchy_eyes_intensity);

                push_crowdy.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<ReturnedPushCrowdsource>() {
                            @Override
                            public void onCompleted() {
                                progressDialog.hide();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("Error is ", e.toString());
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(ReturnedPushCrowdsource returnedPushCrowdsource) {
                                Log.d("answer is", returnedPushCrowdsource.getAnswer());
                            }
                        });
            }
        });

        cough.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    cough_intensity = progress;
                if (progress >= 0 && progress <= 20)
                    cough_value.setText("Low cough. Maybe nothing to worry about");
                else if (progress >= 20 && progress <=40)
                    cough_value.setText("This is getting higher. Are you okay?");
                else if (progress > 40 && progress < 60)
                    cough_value.setText("For your weather temperature, this may be abnormal");
                else if (progress >= 60 && progress <=80)
                    cough_value.setText("This may be a flu");
                else if (progress > 80 && progress <101)
                    cough_value.setText("Get a doctor. ASAP!");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        wheezing.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                wheezing_intensity = progress;
                if (progress >= 0 && progress <= 20)
                    wheezing_value.setText("Ah wheezing? Not really");
                else if (progress >= 20 && progress <=40)
                    wheezing_value.setText("This is getting higher. Are you okay?");
                else if (progress > 40 && progress < 60)
                    wheezing_value.setText("For your weather temperature, this may be abnormal");
                else if (progress >= 60 && progress <=80)
                    wheezing_value.setText("This may be a flu");
                else if (progress > 80 && progress <101)
                    wheezing_value.setText("Get a doctor. ASAP!");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        shortness_of_breath.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sob_intensity = progress;
                if (progress >= 0 && progress <= 20)
                    shortness_of_breath_value.setText("You breathe fairly well");
                else if (progress >= 20 && progress <=40)
                    shortness_of_breath_value.setText("This is getting higher. Are you okay?");
                else if (progress > 40 && progress < 60)
                    shortness_of_breath_value.setText("For your weather temperature, this may be abnormal");
                else if (progress >= 60 && progress <=80)
                    shortness_of_breath_value.setText("This is not normal. Are you sure you are fine?");
                else if (progress > 80 && progress <101)
                    shortness_of_breath_value.setText("Get a doctor. ASAP!");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sneezing.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sneezing_intensity = progress;
                sneezing_value.setText(""+progress);
                if (progress >= 0 && progress <= 20)
                    cough_value.setText("Normals. for anyone.");
                else if (progress >= 20 && progress <=40)
                    cough_value.setText("This is getting higher. Are you okay?");
                else if (progress > 40 && progress < 60)
                    cough_value.setText("For your weather temperature, this may be abnormal");
                else if (progress >= 60 && progress <=80)
                    cough_value.setText("This is not normal. Someone may be infected because of you");
                else if (progress > 80 && progress <101)
                    cough_value.setText("Get a doctor. ASAP!");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        nasOb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                nasal_obs_intensity = progress;
                if (progress >= 0 && progress <= 20)
                    nasOb_value.setText("Low cough. Maybe nothing to worry about");
                else if (progress >= 20 && progress <=40)
                    nasOb_value.setText("This is getting higher. Are you okay?");
                else if (progress > 40 && progress < 60)
                    nasOb_value.setText("For your weather temperature, this may be abnormal");
                else if (progress >= 60 && progress <=80)
                    nasOb_value.setText("This may be a flu");
                else if (progress > 80 && progress <101)
                    nasOb_value.setText("Get a doctor. ASAP!");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        itchyEyes.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                itchy_eyes_intensity = progress;
                if (progress >= 0 && progress <= 20)
                   itchyEyes_value.setText("Your eyes itch just a little.");
                else if (progress >= 20 && progress <=40)
                    itchyEyes_value.setText("This is getting higher. Are you okay?");
                else if (progress > 40 && progress < 60)
                    itchyEyes_value.setText("For your weather temperature, this may be abnormal");
                else if (progress >= 60 && progress <=80)
                    itchyEyes_value.setText("This is not cool at all");
                else if (progress > 80 && progress <101)
                    itchyEyes_value.setText("Get a doctor. ASAP!");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}

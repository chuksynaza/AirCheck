package com.example.sammybobo.aircheck;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.sammybobo.aircheck.Model.GibsLink;
import com.example.sammybobo.aircheck.interfaces.LocationInfo;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Agbede on 23/04/2016.
 */
public class GIBS extends Activity {
    private String url = "https://app.chuksy.me/aircheck/engine/";
    LocationInfo locationInfo;
    WebView webView;
    WebSettings webSettings = webView.getSettings();

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gibs_main);
        webView = (WebView)findViewById(R.id.webView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
        locationInfo = retrofit.create(LocationInfo.class);

        Observable<GibsLink> observable = locationInfo.getGibsLink(8.12,5.08, "2013-11-23");
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GibsLink>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("error is ", e.toString());
                        e.printStackTrace();
                        progressDialog.hide();
                    }

                    @Override
                    public void onNext(GibsLink gibsLink) {
                        Log.d("Value of gibsLink", gibsLink.getUrl());
                        if (gibsLink.getUrl() != null)
                        webView.loadUrl(gibsLink.getUrl());
                        else
                        {
                            Log.d("GIBS Value is null", "true");
                        }
                    }
                });
    }
}

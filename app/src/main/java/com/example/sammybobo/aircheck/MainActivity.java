package com.example.sammybobo.aircheck;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sammybobo.aircheck.Model.CitySearch;
import com.example.sammybobo.aircheck.Model.Frequency;
import com.example.sammybobo.aircheck.interfaces.LocationInfo;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity{
    CircularProgressBar circularProgressBar;
    FloatingActionButton fab;
    AlertDialog alertDialog;
    AlertDialog.Builder listDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView location_;
    TextView weather_data;
    TextView temperature_;
    ImageButton imageButton;
    ArrayAdapter<String> arrayAdapter;
    LocationInfo locationInfo;
    final String url = "https://app.chuksy.me/aircheck/engine/";
    ProgressDialog progressDialog;
    private double rand = Math.random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(MainActivity.this);
        listDialog = new AlertDialog.Builder(this);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        arrayAdapter.add("Search location");
        arrayAdapter.add("Analysis");
        arrayAdapter.add("Save location");
        arrayAdapter.add("Send SMS");
        arrayAdapter.add("Share location");
        arrayAdapter.add("Settings");
        arrayAdapter.add("Use GIBS Services");
        arrayAdapter.add("Feedback");
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
        final Intent age;
        locationInfo = retrofit.create(LocationInfo.class);
        age = new Intent(MainActivity.this, MapsPage.class);
        imageButton = (ImageButton)findViewById(R.id.imageButton);
        circularProgressBar = (CircularProgressBar)findViewById(R.id.progressBar);
        location_ = (TextView)findViewById(R.id.location);
        weather_data = (TextView)findViewById(R.id.weather_data);
        temperature_ = (TextView)findViewById(R.id.temperature);

        sharedPreferences = getSharedPreferences("splash", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        final Dialog dialog = new Dialog(this);
       // Log.d("Hello here it is", sharedPreferences.getInt("temperature", 0) );
        Log.d("Hello here it is", sharedPreferences.getString("temperature", "helo"));
        sharedPreferences.getString("temperature", "helo");
        final String temperature = sharedPreferences.getString("temperature", "helo");
        temperature_.setText("34"  + (char) 0x00B0);
        final String location = sharedPreferences.getString("city","hello");
        location_.setText("Ilorin, Nigeria");
        double num = Double.parseDouble(temperature);
        circularProgressBar.setProgressWithAnimation(34);
        Log.d("hello world", " "+num);



        //alertDialog = new AlertDialog.Builder(MainActivity.this);

        circularProgressBar = (CircularProgressBar)findViewById(R.id.progressBar);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, CrowdSource.class);
                startActivity(a);
            }
        });
       // circularProgressBar.setProgressWithAnimation(temperature);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                listDialog.setAdapter(arrayAdapter, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String gotten_string = arrayAdapter.getItem(which);
                        switch (which){
                            case 0:
                                final EditText username = new EditText(MainActivity.this);
                                username.setId(R.id.hello);

                                AlertDialog.Builder _dialog = new AlertDialog.Builder(MainActivity.this);
                                _dialog.setView(username);
                                _dialog.setPositiveButton("Find location", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                progressDialog.show();
                                               // Log.d("Value of username", username.getText().toString());
                                            }
                                        });
                                        _dialog.show();
                                final rx.Observable<List<CitySearch>> citySearch = locationInfo.citySearch(username.getText().toString());
                                citySearch.observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(new Observer<List<CitySearch>>() {
                                            @Override
                                            public void onCompleted() {
                                                progressDialog.hide();
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                e.printStackTrace();
                                            }

                                            @Override
                                            public void onNext(List<CitySearch> citySearches) {

                                            }
                                        });
                                break;

                            case 1:
                                Intent play = new Intent(MainActivity.this, MapsPage.class);
                                break;

                            case 2:
                                saveBitmap();
                                break;

                            case 3:Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                                intent.putExtra("message","Hi! I am presently in "+location+(char) 0x00B0+" and the weather is "+temperature +". Courtesy : Zephyr");
                                startActivityForResult(intent, 353);
                                break;
                            case 4:Intent a = new Intent(Intent.ACTION_SEND);
                                a.setType("image/*");
                                File file = new File(Environment.getExternalStorageDirectory(), "location"+rand+".jpg");
                                Uri uri = Uri.fromFile(file);
                                a.putExtra(Intent.EXTRA_STREAM, uri);
                                startActivity(Intent.createChooser(a, "Share location Via"));

                                break;
                            case 5:
                                break;
                            case 6:
                                Intent newIntent = new Intent(MainActivity.this, GIBS.class);
                                startActivity(newIntent);
                                break;
                            case 7:final EditText feedback = new EditText(MainActivity.this);
                                feedback.setId(R.id.feedback);
                                feedback.setHint("What do you feel about Zephyr");

                                AlertDialog.Builder _dialog_ = new AlertDialog.Builder(MainActivity.this);
                                _dialog_.setView(feedback);
                                _dialog_.setPositiveButton("Feedback!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                        progressDialog.show();
                                         Log.d("Value of username", feedback.getText().toString());
                                    }
                                });
                                _dialog_.show();

                        }
                    }
                });
                listDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 353 && resultCode == RESULT_OK){
            final Uri contactUri = data.getData();
            final String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

            String number;

            Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            number = cursor.getString(column);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, data.getStringExtra("message"), null, null);

        }
    }
    private void savebitmap(Bitmap bitmap)
    {
        File imagepath = new File(Environment.getExternalStorageDirectory(), "location"+rand+".jpg");
        Log.d("File path", imagepath.toString());
        FileOutputStream fos;
        try
        {
            fos = new FileOutputStream(imagepath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            Log.d("flush","Successfully flushed");
            fos.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("Not found", "true");
        } catch (IOException e)
        {
            e.printStackTrace();
            Log.d("Not found", "true2");
        }
    }
    public void saveBitmap(){
        View root = findViewById(R.id.rootview).getRootView();
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap = root.getDrawingCache();
        Log.d("Bitmap snapped", "Successful");
        savebitmap(bitmap);
    }

}

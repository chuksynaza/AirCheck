package com.example.sammybobo.aircheck;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * Created by Agbede on 19/04/2016.
 */
public class SignIn extends Activity {
    TextInputLayout username_wrapper;
    Button username_confirm;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText username_editText;
    String username;
    ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(findViewById(R.id.cards));

        //shimmerFrameLayout = (ShimmerFrameLayout)findViewById(R.id.shimmer);
        //.startShimmerAnimation();
        sharedPreferences = getSharedPreferences("user_details",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        username_wrapper = (TextInputLayout)findViewById(R.id.username_wrapper);
        username_editText = (EditText)findViewById(R.id.user_username);
        username = username_editText.getText().toString();
        username_wrapper.setHint("Your name please?");
        username_confirm = (Button)findViewById(R.id.submit_name);
        username_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username != null){
                    editor.putString("username",username);
                    editor.commit();
                    Intent a = new Intent(SignIn.this, MainActivity.class);
                    startActivity(a);
                }
                else {
                    username_wrapper.setError("Oops. Check your data again please");
                }

            }
        });

    }
}

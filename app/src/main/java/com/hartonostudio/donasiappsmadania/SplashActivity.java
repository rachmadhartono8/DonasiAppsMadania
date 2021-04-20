package com.hartonostudio.donasiappsmadania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.hartonostudio.donasiappsmadania.RegLogin.LoginHomeActivity;

public class SplashActivity extends AppCompatActivity {

    private int waktu_loading=2000;
    private FirebaseAuth mAuth, mAuthAdmin;

    //4000=4 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        mAuthAdmin = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //berpindah ke login activity
                Intent home = new Intent(SplashActivity.this, LoginHomeActivity.class);
                startActivity(home);
                finish();

            }
        },waktu_loading);
    }
}

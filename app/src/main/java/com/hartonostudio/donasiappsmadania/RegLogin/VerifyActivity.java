package com.hartonostudio.donasiappsmadania.RegLogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hartonostudio.donasiappsmadania.R;

public class VerifyActivity extends AppCompatActivity {

    private TextView tvLogin, tvPhone;
    private boolean doubleBacktoExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        tvLogin = findViewById(R.id.tvLogin);
        tvPhone = findViewById(R.id.tvPhone);
        Login();
        Phone();
    }

    //On click listener to tv login to move to login activity
    public void Login() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    public void Phone(){
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyActivity.this, PhoneLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (doubleBacktoExit){
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
        this.doubleBacktoExit = true;
        Toast.makeText(this, "Tekan Lagi Untuk Keluar",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBacktoExit = false;
            }
        }, 2000);
    }
}


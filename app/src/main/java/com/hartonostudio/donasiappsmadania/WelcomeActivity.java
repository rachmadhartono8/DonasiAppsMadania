package com.hartonostudio.donasiappsmadania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hartonostudio.donasiappsmadania.RegLogin.EmailLoginActivity;
import com.hartonostudio.donasiappsmadania.RegLogin.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {

    Button btn_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



        btn_welcome = findViewById(R.id.btn_welcome);
        //btn_new_account_create = findViewById(R.id.btn_new_account_create);


        //Run Animation


        //btn_new_account_create.startAnimation(btt);

        btn_welcome.setOnClickListener((v ->  {
                Intent login = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(login);
            }));

    }

}

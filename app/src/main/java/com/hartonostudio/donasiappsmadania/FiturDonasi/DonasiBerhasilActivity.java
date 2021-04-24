package com.hartonostudio.donasiappsmadania.FiturDonasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hartonostudio.donasiappsmadania.MainActivity;
import com.hartonostudio.donasiappsmadania.R;

public class DonasiBerhasilActivity extends AppCompatActivity {

    Animation app_splash, btt, ttb;
    Button btn_home;
    TextView app_title, app_subtitle;
    ImageView icon_succes_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi_berhasil);

        btn_home = findViewById(R.id.btn_home);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);
        icon_succes_ticket = findViewById(R.id.icon_succes_ticket);

        //Load Animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        //Run Animation
        icon_succes_ticket.startAnimation(app_splash);

        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(ttb);

        btn_home.startAnimation(btt);

        //berpindah Activity MyProfile
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(gotohome);
            }
        });

    }
}
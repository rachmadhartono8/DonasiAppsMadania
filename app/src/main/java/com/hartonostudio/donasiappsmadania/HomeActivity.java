package com.hartonostudio.donasiappsmadania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.hartonostudio.donasiappsmadania.Donasi.ShodaqohActivity;
import com.hartonostudio.donasiappsmadania.Donasi.WakafActivity;

public class HomeActivity extends AppCompatActivity {


    LinearLayout btn_shodaqoh, btn_zakat,
            btn_wakaf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //getUsernameLocal();

        btn_zakat = findViewById(R.id.btn_zakat);
        btn_wakaf = findViewById(R.id.btn_wakaf);
        btn_shodaqoh = findViewById(R.id.btn_shodaqoh);


        btn_shodaqoh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoshodaqoh = new Intent(HomeActivity.this, ShodaqohActivity.class);
                //meletakan data kepada intent
                gotoshodaqoh.putExtra("jenis_tiket", "Pisa");
                startActivity(gotoshodaqoh);
            }
        });

        btn_zakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotozakat = new Intent(HomeActivity.this, ZakatActivity.class);
                gotozakat.putExtra("jenis_tiket", "Tori");
                startActivity(gotozakat);
            }
        });

        btn_wakaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotowakaf = new Intent(HomeActivity.this, WakafActivity.class);
                gotowakaf.putExtra("jenis_tiket", "Pagoda");
                startActivity(gotowakaf);
            }
        });

    }
}

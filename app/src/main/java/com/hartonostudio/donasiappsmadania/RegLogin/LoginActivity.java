package com.hartonostudio.donasiappsmadania.RegLogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hartonostudio.donasiappsmadania.HomeActivity;
import com.hartonostudio.donasiappsmadania.R;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
        private  Button btn_login, btn_lupa_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


            email = findViewById(R.id.email);
            password = findViewById(R.id.password);
            btn_login = findViewById(R.id.btn_login);
            btn_lupa_password = findViewById(R.id.btn_lupa_password);

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String usernameKey = email.getText().toString();
                    String passwordKey = password.getText().toString();

                    if (usernameKey.equals("admin") && passwordKey.equals("123")) {
                        //jika login berhasil
                        Toast.makeText(getApplicationContext(), "LOGIN SUKSES",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        LoginActivity.this.startActivity(intent);
                        finish();
                    } else {
                        //jika login gagal
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Username atau Password Anda salah!")
                                .setNegativeButton("Retry", null).create().show();

                    }
                }

            });
            btn_lupa_password.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent gotolupapassword = new Intent(LoginActivity.this, LupasPasswordActivity.class);
                    startActivity(gotolupapassword);
                }
            });

        }
    };


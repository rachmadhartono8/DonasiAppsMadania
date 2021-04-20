package com.hartonostudio.donasiappsmadania.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hartonostudio.donasiappsmadania.R;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;

public class AdminActivityy extends AppCompatActivity  implements View.OnClickListener {

    //variabel global teteapi hanya dikelas iini, jika ingin dikelas lain tinggal diganti public
    private static final String TAG = "AdminActivityy";
    private EditText emailet, passwordet ;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuthAdmin;
    private Button btnMasuk;
    private Button btnDaftar;

    private AwesomeText awesomeText;
    private boolean pwd_status = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_activityy);


        //variabel tadi untuk memanggil fungsi
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuthAdmin = FirebaseAuth.getInstance();

        // diatur sesuai id komponennya
        progressDialog = new ProgressDialog(this);
        emailet = (EditText) findViewById(R.id.email);
        passwordet = (EditText) findViewById(R.id.password);
        btnMasuk = (Button) findViewById(R.id.btn_masuk);
        btnDaftar = (Button) findViewById(R.id.btn_daftar);

        //nambahin method onClick, biar tombolnya bisa diklik
        btnMasuk.setOnClickListener(this);
        btnDaftar.setOnClickListener(this);

        //show hide password
        awesomeText = findViewById(R.id.awesome);
        awesomeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd_status){
                    passwordet.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_status = false;
                    awesomeText.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY);
                    passwordet.setSelection(passwordet.length());
                }else {
                    passwordet.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    pwd_status = true;
                    awesomeText.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY_OFF);
                    passwordet.setSelection(passwordet.length());
                }
            }
        });




    }

    //fungsi signin untuk mengkonfirmasi data pengguna yang sudah mendaftar sebelumnya
    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;

        }

        //showProgressDialog();
        String email = emailet.getText().toString();
        String password = passwordet.getText().toString();

        mAuthAdmin.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        //hideProgressDialog();
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(AdminActivityy.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();
                        progressDialog.setCanceledOnTouchOutside(false);
                    }
                });
    }

    //fungsi ini untuk mendaftarkan data pengguna ke Firebase
    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();
        //variabel lokal
        String email = emailet.getText().toString();
        String password = passwordet.getText().toString();

        mAuthAdmin.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        //hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(AdminActivityy.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //fungsi dipanggil ketika proses Authentikasi berhasil
    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // membuat User admin baru
        writeNewAdmin(user.getUid(), username, user.getEmail());

        // Go to FetchActivity
        Intent fetch = new Intent(AdminActivityy.this, FetchActivity.class);
        startActivity(fetch);
    }

    /*
        ini fungsi buat bikin username dari email
            contoh email: abcdefg@mail.com
            maka username nya: abcdefg
     */
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    //fungsi untuk memvalidasi EditText email dan password agar tak kosong dan sesuai format
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(emailet.getText().toString())) {
            emailet.setError("Required");
            result = false;
        } else {
            emailet.setError(null);
        }

        if (TextUtils.isEmpty(passwordet.getText().toString())) {
            passwordet.setError("Required");
            result = false;
        } else {
            passwordet.setError(null);
        }

        return result;
    }

    // menulis ke Database
    private void writeNewAdmin(String userId, String name, String email) {
        Admins admin = new Admins(name, email);

        mDatabase.child("admins").child(userId).setValue(admin);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_masuk) {
            signIn();
        } else if (i == R.id.btn_daftar) {
            signUp();
        }

    }


}
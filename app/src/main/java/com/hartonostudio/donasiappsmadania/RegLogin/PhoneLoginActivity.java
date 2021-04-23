package com.hartonostudio.donasiappsmadania.RegLogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hartonostudio.donasiappsmadania.R;

import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity {


    private static final String TAG = "PhoneLoginActivity";

    private ProgressBar loadingProgress;
    private Button regBtn;

    TextView txt_phone;
    EditText et_phone;
    String phoneNumber;


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        et_phone = findViewById(R.id.et_phone);
        loadingProgress = findViewById(R.id.regProgressBar);
        loadingProgress.setVisibility(View.INVISIBLE);

        //variabel tadi untuk memanggil fungsi
        mAuth = FirebaseAuth.getInstance();

        regBtn = findViewById(R.id.regBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = et_phone.getText().toString().trim();
                if (phoneNumber.isEmpty()) { //untuk mengecek nomor hp kosong atau tidak
                    et_phone.setError("Invalid Phone Number");
                } else {
                    loadingProgress.setVisibility(View.VISIBLE);
                    sendVerificationCode(phoneNumber);
                }
            }
        });
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+62" + phoneNumber, //nomor yang diverifikasi
                60,//timeout
                TimeUnit.SECONDS,//unittimeout
                this,
                mCall);
        Toast.makeText(getApplicationContext(), "Sedang Memverifikasi, Mohon Tunggu", Toast.LENGTH_SHORT).show();
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCall = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);

            Log.e(TAG, "onCodeAutoRetrievalTimeOut: " + s);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.e(TAG, "onVerificationCompleted: " + phoneAuthCredential.toString());
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.e(TAG, "onVerificationFailed: " + e.toString());
            loadingProgress.setVisibility(View.GONE);
        }

        //kode verif dikirim
        @Override
        public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
            Log.e(TAG, "onCodeSent: " + token.toString());
            loadingProgress.setVisibility(View.GONE);
            String mVerificationId = verificationId;
            Log.e("MainActivity", "Verification id : " + verificationId);
            Intent intent = new Intent(PhoneLoginActivity.this, KodeActivity.class);
            intent.putExtra("verificationId", mVerificationId);
            startActivity(intent);
            finish();
        }
    };

    public void ClickPhone(View view){

        et_phone = findViewById(R.id.et_phone);
        txt_phone = findViewById(R.id.txt_phone);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Referensi database yang dituju
        DatabaseReference myRef =
                database.getReference("Login Number").child(txt_phone.getText().toString());

        //memberi nilai pada referensi yang dituju
        myRef.child("Phone").setValue(et_phone.getText().toString());

    }
}


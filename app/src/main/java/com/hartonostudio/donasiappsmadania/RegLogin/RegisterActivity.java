package com.hartonostudio.donasiappsmadania.RegLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hartonostudio.donasiappsmadania.R;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;

public class RegisterActivity extends AppCompatActivity {


    ImageView ImgUserPhoto;
    static int PReqCode = 1;
    static int REQUESCODE = 1;

    private EditText inputNama, inputEmail, inputPassword, inputConfirmPassword, inputPhone;
    private Button btnRegister;
    private ProgressBar loadingProgress;
    private AwesomeText awesomeText, awesomeText2;
    private boolean pwd_status = true;

    FirebaseAuth mAuth;

    String nama, email, password, confirmPassword, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Init
        inputNama = findViewById(R.id.regName);
        inputEmail = findViewById(R.id.regMail);
        inputPassword = findViewById(R.id.regPassword);
        inputConfirmPassword = findViewById(R.id.regConfirmPassword);

        loadingProgress = findViewById(R.id.regProgressBar);
        loadingProgress.setVisibility(View.GONE);

        btnRegister = findViewById(R.id.regBtn);

        //Firebase Init
        mAuth = FirebaseAuth.getInstance();

        Register();

        //show hide password
        awesomeText = findViewById(R.id.awesome);
        awesomeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd_status) {
                    inputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_status = false;
                    awesomeText.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY);
                    inputPassword.setSelection(inputPassword.length());
                } else {
                    inputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    pwd_status = true;
                    awesomeText.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY_OFF);
                    inputPassword.setSelection(inputPassword.length());
                }
            }
        });

        //show hide confirm password
        awesomeText2 = findViewById(R.id.awesome2);
        awesomeText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd_status) {
                    inputConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_status = false;
                    awesomeText2.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY);
                    inputConfirmPassword.setSelection(inputConfirmPassword.length());
                } else {
                    inputConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    pwd_status = true;
                    awesomeText2.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY_OFF);
                    inputConfirmPassword.setSelection(inputConfirmPassword.length());
                }
            }
        });


    }


    //on click listener at btn register
    public void Register() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateDataAndDoRegister();
            }
        });
    }

    private void ValidateDataAndDoRegister() {

        nama = inputNama.getText().toString().trim();
        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        confirmPassword = inputConfirmPassword.getText().toString().trim();


        if (nama.isEmpty()) {
            inputNama.setError("Masukkan Nama");
            inputNama.requestFocus();
        } else if (email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher((CharSequence) inputEmail).matches()) {
            inputEmail.setError("Enter Valid Email Address");
            inputEmail.requestFocus();
        } else if (password.isEmpty()) {
            inputPassword.setError("Enter Password");
            inputPassword.requestFocus();
        } else if (inputPassword.length() < 7) {
            inputPassword.setError("Password should be greater than 7 character");
            inputPassword.requestFocus();
        } else if (confirmPassword.isEmpty()) {
            inputConfirmPassword.setError("Enter Confirm Password");
            inputConfirmPassword.requestFocus();
        } else if (confirmPassword.length() < 7) {
            inputConfirmPassword.setError("Password should be greater than 7 character");
            inputConfirmPassword.requestFocus();
        } else if (!password.equals(confirmPassword)) //if password not equal to confirm password
        {
            inputPassword.setError("Password not matched");
            inputPassword.requestFocus();
            inputConfirmPassword.setError("Password not matched");
            inputConfirmPassword.requestFocus();
            inputPassword.setText("");
            inputConfirmPassword.setText("");
        } else {
            doRegister(email, password);
        }
    }

    private void doRegister(String email, String password) {
        btnRegister.setEnabled(false);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Register register = new Register(inputNama.getText().toString(),
                            inputEmail.getText().toString(),
                            inputPassword.getText().toString());

                    updateUserInfo(register);
                    loadingProgress.setVisibility(View.VISIBLE);
                    sendVerificationEmail();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthUserCollisionException) //Apabila Email Sudah Register
                {
                    btnRegister.setEnabled(true);
                    inputEmail.setError("Email Already Registered");
                    inputEmail.requestFocus();
                } else {
                    btnRegister.setEnabled(true);
                    Toast.makeText(RegisterActivity.this, "Opps! Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void updateUserInfo(Register register) {


        //untuk memasukkan ke firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //membuat server pada realtime database
        //Referensi database yang dituju("RSBW KELUHAN")
        //Fungsi push() untuk menghasilkan kunci unik untuk setiap turunan baru
        DatabaseReference reg = database.getReference("Register").push();

        //memberi nilai pada referensi yang dituju
        reg.child("Nama").setValue(inputNama.getText().toString());
        reg.child("Email").setValue(inputEmail.getText().toString());
        reg.child("Password").setValue(inputPassword.getText().toString());

        reg.setValue(register).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                loadingProgress.setVisibility(View.INVISIBLE);
                showMessage("Berhasil Mendaftar!");
                updateUI();


            }
        });

    }

    //11
    private void updateUI() {
        Intent RegisterActivity = new Intent(getApplicationContext(), VerifyActivity.class);
        startActivity(RegisterActivity);
        finish();
    }

    // simple method to show toast message //7
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void sendVerificationEmail() {
        if (mAuth.getCurrentUser() != null) {
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        btnRegister.setEnabled(true);
                    } else {
                        btnRegister.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Oops! failed to send verification email", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}




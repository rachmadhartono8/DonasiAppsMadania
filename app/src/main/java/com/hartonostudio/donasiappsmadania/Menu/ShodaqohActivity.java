package com.hartonostudio.donasiappsmadania.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hartonostudio.donasiappsmadania.R;

import java.text.NumberFormat;
import java.util.Locale;

public class ShodaqohActivity extends AppCompatActivity {

    private EditText edt_nominal;
    private Button btn_submit;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shodaqoh);

            initComponents();

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String harga = edt_nominal.getText().toString();
                    if (TextUtils.isEmpty(harga)){
                        Toast.makeText(ShodaqohActivity.this, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        String resultRupiah = "Harga : " + formatRupiah(Double.parseDouble(harga));
                        tvResult.setText(resultRupiah);
                    }
                }
            });
        }

        private void initComponents(){
            edt_nominal = findViewById(R.id.edt_nominal);
            btn_submit = findViewById(R.id.btn_submit);
            tvResult = findViewById(R.id.tvResult);
        }


        //Fungsi Format Rupiah
        private String formatRupiah(Double number){
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            return formatRupiah.format(number);
        }
    }

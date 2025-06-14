package com.app.appli;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.appli.Entity.Appartement;
import com.google.android.material.textfield.TextInputEditText;
import com.owlike.genson.Genson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class AddFormActivity extends AppCompatActivity {
    private Button btn_create_confirm,btn_back_af;
    private TextInputEditText input_design_add,input_rent_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_form);

        btn_create_confirm = findViewById(R.id.btn_create_confirm);
        btn_back_af = findViewById(R.id.btn_back_af);
        input_design_add = findViewById(R.id.input_design_add);
        input_rent_add = findViewById(R.id.input_rent_add);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_create_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String des = Objects.requireNonNull(input_design_add.getText()).toString();
                String rent = Objects.requireNonNull(input_rent_add.getText()).toString();

                Appartement nouvelAppart = new Appartement();
                nouvelAppart.setNumApp(des.toUpperCase().charAt(0)+rent.substring(0,4));
                nouvelAppart.setDesignation(des);
                nouvelAppart.setLoyer(Double.parseDouble(rent));

                Log.i("POST_DATA",nouvelAppart.toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        try{
                            URL url = new URL("http://192.168.1.101:8080/appartement/add");
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");

                            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                            out.write(new Genson().serialize(nouvelAppart).getBytes());

                            InputStream in = new BufferedInputStream(connection.getInputStream());
                            Scanner scan =  new Scanner(in);

                            Log.i("CONNECTION_RESULTS",scan.toString());
                            in.close();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        btn_back_af.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(AddFormActivity.this, MainActivity.class);
                startActivity(it);
            }
        });
    }
}
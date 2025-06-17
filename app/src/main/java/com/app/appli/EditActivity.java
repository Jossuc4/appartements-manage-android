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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class EditActivity extends AppCompatActivity {

    private TextInputEditText design_edit, rent_edit;
    private Button btn_confirm_edit,btn_back_to_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);

        String designation = Objects.requireNonNull(getIntent().getExtras().get("designation")).toString();
        String loyer = Objects.requireNonNull(getIntent().getExtras().get("loyer")).toString();
        String numApp = Objects.requireNonNull(getIntent().getExtras().get("numApp")).toString();

        design_edit= findViewById(R.id.edit_design_text);
        rent_edit = findViewById(R.id.edit_rent_text);
        btn_confirm_edit = findViewById(R.id.btn_confirm_edit);
        btn_back_to_list = findViewById(R.id.btn_back_to_list);

        design_edit.setText(designation);
        rent_edit.setText(loyer);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_back_to_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(EditActivity.this, ListActivity.class);
                startActivity(it);
            }
        });

        btn_confirm_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String des = Objects.requireNonNull(design_edit.getText()).toString();
                String rent = Objects.requireNonNull(rent_edit.getText()).toString();

                Appartement editedAppart = new Appartement();
                editedAppart.setNumApp(des.toUpperCase().charAt(0)+rent.substring(0,4));
                editedAppart.setDesignation(des);
                editedAppart.setLoyer(Double.parseDouble(rent));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        try{
                            URL url = new URL(getString(R.strings.API_URL)+"/appartement/"+numApp);
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("PUT");

                            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                            out.write(new Genson().serialize(editedAppart).getBytes());

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

    }
}
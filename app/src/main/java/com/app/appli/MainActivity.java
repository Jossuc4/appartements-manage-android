package com.app.appli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.appli.Entity.Appartement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton nextActivityButton;

    private Button btn_create;
    private List<Appartement> allApps;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View mainView = findViewById(R.id.main);

        nextActivityButton = findViewById(R.id.changeActivityButton);
        btn_create = findViewById(R.id.btn_create);

        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            var systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL("http://192.168.1.101:8080/personne/all");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    Scanner scan =  new Scanner(in);

                    allApps = new Genson().deserialize(scan.nextLine(), new GenericType<List<Appartement>>(){});

                    Log.i("CONNECTION_RESULTS",scan.toString());
                    in.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        nextActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                intent.putExtra("data", (CharSequence) allApps);
                startActivity(intent);
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddFormActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://localhost:8080/personne/all");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    Scanner scan = new Scanner(in);

                    Log.i("CONNECTION_RESULTS", scan.toString());
                    in.close();
                } catch (Exception e) {
                    Log.e("ERROR", Objects.requireNonNull(e.getMessage()));
                }
            }
        }).start();

    }

}
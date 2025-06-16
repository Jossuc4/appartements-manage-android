package com.app.appli;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

    private ListView liste ;
    private Button btn_graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_with_list_layout);

        View mainView = findViewById(R.id.list_main);

        liste = (ListView) findViewById(R.id.list_view);
        btn_graph = (Button) findViewById(R.id.btn_graph);

        ArrayList<HashMap<String,String>> listItems = new ArrayList<HashMap<String,String>>();


        String[] appartements = new String[]{"Trano Mora","Villa basse","Manoir","Chateau","3 etages","Avec piscine","5 etoiles","3 etoiles","Près de la plage"};
        int [] loyers = new int[]{700,1000,1200,1800,950,2000,2400,1900,1700};
        int i = 0;

        int max , min , total;
        total = 0;
        min = loyers[0];
        max = min;

        for (String appartement : appartements) {

            String loyer = Integer.toString(loyers[i]);
            HashMap<String, String> items = new HashMap<String, String>();
            items.put("designation", appartement);
            items.put("loyer",loyer);
            items.put("numApp",appartement.toUpperCase().charAt(0)+loyer.substring(0,4));

            if(loyers[i]<1000){
                items.put("observation","Bas");
            }else if(loyers[i]>1000){
                items.put("observation","Cher");
            }else{
                items.put("observation","Normal");
            }

            listItems.add(items);
            if(min > loyers[i]){
                min = loyers[i];
            }

            if(max<loyers[i]){
                max = loyers[i];
            }

            total += loyers[i];

            i++;

        }

        CustomListAdapter adapter = new CustomListAdapter(this, listItems);
        liste.setAdapter(adapter);

        btn_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ListActivity.this, StatsActivity.class);
                startActivity(it);
            }
        });
    }
}
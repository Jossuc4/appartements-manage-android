package com.app.appli;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CustomListAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<HashMap<String, String>> items;

    public CustomListAdapter(Context context, ArrayList<HashMap<String, String>> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() { return items.size(); }

    @Override
    public Object getItem(int position) { return items.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.item_list_layout, parent, false);
        }

        TextView design_text = row.findViewById(R.id.text_content);
        TextView rent_text = row.findViewById(R.id.text_loyer_content);
        TextView obs_text = row.findViewById(R.id.text_obs_content);
        ImageButton menuButton = row.findViewById(R.id.button_menu);

        HashMap<String, String> currentItem = items.get(position);
        String designation = currentItem.get("designation");
        String loyer = currentItem.get("loyer");
        String observation = currentItem.get("observation");
        String numApp = currentItem.get("numApp");

        design_text.setText(designation);
        rent_text.setText(loyer);
        obs_text.setText(observation);

        assert observation != null;
        if (observation.equalsIgnoreCase("cher")) {
            obs_text.setTextColor(ContextCompat.getColor(context, R.color.high_text_color));
        } else if (observation.equalsIgnoreCase("normal")) {
            obs_text.setTextColor(ContextCompat.getColor(context, R.color.medium_text_color));
        } else if (observation.equalsIgnoreCase("bas")) {
            obs_text.setTextColor(ContextCompat.getColor(context, R.color.low_text_color));
        }

        menuButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.layout.item_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_edit) {
                    Intent it = new Intent(context, EditActivity.class);
                    it.putExtra("designation",designation);
                    it.putExtra("loyer",loyer);
                    it.putExtra("numApp","M1234");
                    context.startActivity(it);
                    return true;
                }else if(item.getItemId() == R.id.menu_delete) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpURLConnection connection = null;
                            try{
                                URL url = new URL("http://192.168.1.101:8080/appartement/delete/"+numApp);
                                connection = (HttpURLConnection) url.openConnection();
                                connection.setRequestMethod("DELETE");

                                InputStream in = new BufferedInputStream(connection.getInputStream());
                                Scanner scan =  new Scanner(in);

                                Log.i("CONNECTION_RESULTS",scan.toString());
                                in.close();
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    Toast.makeText(context, "Supprimer: " + designation, Toast.LENGTH_SHORT).show();
                    items.remove(position);
                    notifyDataSetChanged();
                    return true;

                }else {
                    return false;
                }
            });
            popup.show();
        });

        return row;
    }
}

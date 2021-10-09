package com.example.uasppb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Tips extends AppCompatActivity {

    private ListView lvItem;
    private String[] KategoriBuku = new String[]{
            "Drink your glass of water slowly with some small sips",
            "Hold the water in yout mouth for a while before swallowing",
            "Drinking water in a sitting posture is better than in standing or running position",
            "Do not drink cold water or water with ice too often",
            "Do not drink water immediately after eating"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setTitle("Genre Buku");
        lvItem = (ListView) findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(Tips.this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                KategoriBuku);
        lvItem.setAdapter(adapter);
//        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int
//                    position, long id) {
//                if (position == 0) {
//                    Intent intent = new Intent(view.getContext(), scifi.class);
//                    startActivity(intent);
//                }
//                if (position == 1) {
//                    Intent intent = new Intent(view.getContext(), album.class);
//                    startActivity(intent);
//                }
//            }
        };
    }

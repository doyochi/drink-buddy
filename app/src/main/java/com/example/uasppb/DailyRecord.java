package com.example.uasppb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.uasppb.login.Login;

public class DailyRecord extends AppCompatActivity {
    String[] daftar;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbcenter;
    public static DailyRecord ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_record);
        ma = this;
        dbcenter = new DataHelper(this);
        RefreshList();
    }


    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM data", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }
        ListView01 = (ListView) findViewById(R.id.listView1);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Edit","Delete"};
                AlertDialog.Builder builder = new AlertDialog.Builder(DailyRecord.this);
                builder.setTitle("Option");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("delete from data where persen = '" + selection + "'");
                                RefreshList();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                break;
                            case 1:
                                SQLiteDatabase dbb = dbcenter.getWritableDatabase();
                                dbb.execSQL("delete from data where persen = '" + selection + "'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) ListView01.getAdapter()).notifyDataSetInvalidated();

        Button btntambah = findViewById(R.id.btn_tam);
        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyRecord.this, MainActivity.class);
                startActivity(intent);
            }
        });


        ImageView imageLogOut = findViewById(R.id.imagekel);
        imageLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showLogOutDialog()
                SharedPreferences sharedpreferences = getSharedPreferences
                        (Login.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();
                finish();
                Intent keli = new Intent(DailyRecord.this,
                        Login.class);
                keli.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(keli, 0);
            }
        });
    }}

//    private void showLogOutDialog(){
//        if (dialogLogOut == null){
//            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//            View view = LayoutInflater.from(this).inflate(
//                    R.layout.layout_logout, (ViewGroup) findViewById(R.id.layoutLogOutContainer)
//            );
//            builder.setView(view);
//            dialogLogOut = builder.create();
//            if (dialogLogOut.getWindow() != null){
//                dialogLogOut.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//            }
//
//            view.findViewById(R.id.logOut).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    SharedPreferences sharedpreferences = getSharedPreferences
//                            (Login.MyPREFERENCES, Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedpreferences.edit();
//                    editor.clear();
//                    editor.apply();
//                    finish();
//                    Intent keli = new Intent(DailyRecord.this,
//                            Login.class);
//                    keli.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    startActivityIfNeeded(keli, 0);
//                }
//            });
//            view.findViewById(R.id.cancelLogOut).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialogLogOut.dismiss();
//                }
//            });
//        }
//        dialogLogOut.show();
//    }
//
//}}
//
////    }
////
////
////
////}
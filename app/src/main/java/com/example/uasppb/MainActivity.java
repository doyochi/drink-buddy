package com.example.uasppb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uasppb.login.Login;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    protected Cursor cursor;
    DataHelper dbHelper;
    Button btn_save, btnPindah;
    private int progr = 0;
    private ProgressBar progressBar;
    private AlertDialog dialogLogOut;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    Button btnAlertAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        Button btin = (findViewById(R.id.button_incr));
        Button button = (findViewById(R.id.r_btn));
        ImageView sett = (findViewById(R.id.sett));
        ImageView tipp = (findViewById(R.id.bt_tip));

        btin.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
//                if (progr <= 90)
                {
//                    progr += 10;
                    progressBar = findViewById(R.id.progress_bar);
                    int progressValue = progressBar.getProgress();
                    progr = progressValue+10;
                    progressBar.setProgress(progr);
                    String h = Integer.toString(progr*10);
                    TextView tt = (findViewById(R.id.text_view_progress));
                    tt.setText(h);
                }

//                updateProgressbar();
            }

        }));

        Button btde = (findViewById(R.id.button_decr));
        btde.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
//                if (progr >= 10)
                {
//                    progr -= 10;
                    progressBar = findViewById(R.id.progress_bar);
                    int progressValue = progressBar.getProgress();
                    progr = progressValue-10;
                    progressBar.setProgress(progr);
                    String h = Integer.toString(progr*10);
                    TextView tt = (findViewById(R.id.text_view_progress));
                    tt.setText(h);
                }

//                updateProgressbar();
            }

        }));

        //Activate reminder
        ImageView imageRemind = findViewById(R.id.imageT);
        imageRemind.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getBaseContext(), "Reminder Set!", Toast.LENGTH_SHORT).show();
                alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(MainActivity.this,ReminderBroadcast.class);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                long timeAtButtonClick = System.currentTimeMillis();

                long tenSecondsMllis = 1000 * 10;

                alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtButtonClick + tenSecondsMllis,pendingIntent);
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeAtButtonClick+tenSecondsMllis , alarmManager.INTERVAL_HOUR,pendingIntent);
            }
        }));

//        sett
//        sett.setOnClickListener((new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Dialog dialog = new Dialog(this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.about_ly);
//                final Button dialobButton = (Button) dialog.findViewById(R.id.btn_custom_alert_ok);
//                dialobButton.setOnClickListener(new View.OnClickListener(){
//                    public void onClick(View v){
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();}
//        }));
        ImageView imageLogOut = findViewById(R.id.sett);
        sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTing();
            }
        });
        tipp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seTip();
            }
        });
//        ImageView imageLogOut = findViewById(R.id.sett);
//        imageLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showLogOutDialog();
//            }
//        });



        dbHelper = new DataHelper(this);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("insert into data(persen) values('" +
                        (progr*10) + "')");
                Toast.makeText(getApplicationContext(), "Good Job! ^^", Toast.LENGTH_LONG).show();
                DailyRecord.ma.RefreshList();
            }
        });
        btnPindah = findViewById(R.id.btnPindah);
        btnPindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DailyRecord.class);
                startActivity(intent);
            }
        });

//        ImageView imageLogOut = findViewById(R.id.imageLogOut);
//        imageLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showLogOutDialog();
//            }
//        });


//        ImageView imageTip = findViewById(R.id.imageT);
//        imageTip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,Tips.class);
//                startActivity(intent);
//            }
//        });
        ImageView imageHist = findViewById(R.id.imageH);
        imageHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DailyRecord.class);
                startActivity(intent);
            }
        });
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LemubitReminderChannel";
            String description = "Channel for Lemubit Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel) ;
        }

    }

    private void showLogOutDialog(){
        if (dialogLogOut == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.layout_logout, (ViewGroup) findViewById(R.id.layoutLogOutContainer)
            );
            builder.setView(view);
            dialogLogOut = builder.create();
            if (dialogLogOut.getWindow() != null){
                dialogLogOut.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            view.findViewById(R.id.logOut).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedpreferences = getSharedPreferences
                            (Login.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.apply();
                    finish();
                    Intent keli = new Intent(MainActivity.this,
                            Login.class);
                    keli.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityIfNeeded(keli, 0);
                }
            });
            view.findViewById(R.id.cancelLogOut).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogLogOut.dismiss();
                }
            });
        }
        dialogLogOut.show();
    }

    private void setTing() {
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.about_ly);
                final Button dialobButton = (Button) dialog.findViewById(R.id.btn_custom_alert_ok);
                dialobButton.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        dialog.dismiss();
                    }
                });
                dialog.show();
        }
    private void seTip() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tip_ly);
        final Button dialobButton = (Button) dialog.findViewById(R.id.btn_custom_alert_ok);
        dialobButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        MainActivity.this.finish();
    }
}
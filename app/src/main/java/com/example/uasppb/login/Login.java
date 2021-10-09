package com.example.uasppb.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;

import com.example.uasppb.MainActivity;
import com.example.uasppb.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static  final String name = "nameKey";
    public static  final String pass = "passwordKey";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText txtUserName = (EditText) findViewById(R.id.inputUsername);
        final EditText txtPassword = (EditText) findViewById(R.id.inputPassword);
        Button btnLogin = (Button) findViewById(R.id.buttonLogin);
        TextView btnRegister = (TextView) findViewById(R.id.textSignUp);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();

                if(txtUserName.getText().toString().trim().isEmpty() && txtPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(Login.this,"Username and Password cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (txtUserName.getText().toString().trim().isEmpty()){
                    Toast.makeText(Login.this,"Username cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (txtPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(Login.this,"Password cannot be empty!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Login.this
                            , "Successfully Registered"
                            , Toast.LENGTH_LONG).show();
                    setBB();
                }

                try {
                    LoginAdapters dbaUser = new LoginAdapters(Login.this);
                    dbaUser.open();
                    dbaUser.Register(username, password);
                    dbaUser.close();
                } catch (Exception e) {
                    Toast.makeText(Login.this, e.getMessage()
                            , Toast.LENGTH_LONG).show();
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                try {
                    if (username.length() > 0 && password.length() > 0) {
                        LoginAdapters dbUser = new LoginAdapters(Login.this);
                        dbUser.open();

                        if (dbUser.Login(username, password)) {
                            Toast.makeText(Login.this
                                    , "Successfully Logged In"
                                    , Toast.LENGTH_LONG).show();
                            editor.putString(name, username);
                            editor.putString(pass, password);
                            editor.commit();
                            Intent kela = new Intent(Login.this
                                    , MainActivity.class);
                            startActivity(kela);
                        } else {
                            Toast.makeText(Login.this
                                    , "Invalid Username/Password or Profile is not registered yet!" +
                                            "\n" + "Click the link below to sign up, then re-attempt login!"
                                    , Toast.LENGTH_LONG).show();
                        }

                        dbUser.close();

                    }else{
                        Toast.makeText(Login.this
                                , "Username or Password cannot be empty!"
                                , Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(Login.this, e.getMessage()
                            , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    protected void onResume() {
        sharedpreferences=getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(name))
        {
            if(sharedpreferences.contains(pass)){
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
            }
        }
        super.onResume();
    }

    private void setBB() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bb_ly);
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
        Login.this.finish();
    }
}
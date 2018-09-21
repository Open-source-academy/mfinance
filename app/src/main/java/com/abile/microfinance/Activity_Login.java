package com.abile.microfinance;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abile.microfinance.FinanceDatabaseHandler.DataHandler;


public class Activity_Login extends Activity {
    TextView linkreg;
    EditText email,pin;
    Button login;
    String username,Pin;

    DataHandler handler;
    Cursor cursor;

    private Session session;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_login);
        session = new Session(this);

        if(session.loggedin()){
            Intent in=new Intent(getApplicationContext(),Activity_Main_Menu.class);
            startActivity(in);
            finish();
        }
        email= (EditText) findViewById(R.id.email);
        pin= (EditText) findViewById(R.id.pin);
        login= (Button) findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //username validation
                if(email.getText().toString().trim().length()==0){
                    email.setError("Enter Username");
                    //Toast.makeText(getApplicationContext(),"Enter Username",Toast.LENGTH_LONG).show();
                }
                else{
                    username=email.getText().toString().trim();
                }

                //pin validation
                if(pin.getText().toString().trim().length()==0){
                    pin.setError("Enter Your Pin");
                    // Toast.makeText(getApplicationContext(),"Enter your Pin",Toast.LENGTH_LONG).show();
                }
                else if(pin.getText().toString().trim().length()<4){
                    pin.setError("Enter 4 Digit Pin");
                    //Toast.makeText(getApplicationContext(),"Enter 4 Digit Pin",Toast.LENGTH_LONG).show();
                }
                else{
                    Pin=pin.getText().toString().trim();
                 handler=new DataHandler(getApplicationContext());
                 cursor=handler.checklogin(username,Pin);
                 if(cursor.getCount()>0){
                     Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                     session.setName(username);
                     session.setPin(Pin);
                     session.setLoggedin(true);
                     Intent in=new Intent(getApplicationContext(),Activity_Main_Menu.class);
                     startActivity(in);
                 }
                 else if(cursor.getCount()==0){
                     Toast.makeText(getApplicationContext(),"User not registered with us!",Toast.LENGTH_LONG).show();
                 }
                 else{
                     Toast.makeText(getApplicationContext(),"Username or Password is incorrect!",Toast.LENGTH_LONG).show();
                 }
                }
            }
        });

        linkreg= (TextView) findViewById(R.id.link_signup);
        linkreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Activity_Login.this,Activity_Register.class);
                startActivity(in);
            }
        });
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}

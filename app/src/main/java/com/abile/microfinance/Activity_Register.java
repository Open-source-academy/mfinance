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


public class Activity_Register extends Activity {
    TextView linklogin;
    EditText email,pin,cpin;
    Button register;
    String username,Pin,Cpin;

    DataHandler handler;
    Cursor cursor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_register);

        handler=new DataHandler(this);

        //signup link
        linklogin= (TextView) findViewById(R.id.link_login);
        linklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),Activity_Login.class);
                startActivity(in);
            }
        });

        email= (EditText) findViewById(R.id.email);
        pin= (EditText) findViewById(R.id.pin);
        cpin= (EditText) findViewById(R.id.cpin);

        register= (Button) findViewById(R.id.btn_signup);
        register.setOnClickListener(new View.OnClickListener() {
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
                }

                //Confirm pin validation
                if(cpin.getText().toString().trim().length()==0){
                    cpin.setError("Re-enter Your Pin");
                   // Toast.makeText(getApplicationContext(),"Enter Your Pin Again",Toast.LENGTH_LONG).show();
                }
                else if(cpin.getText().toString().trim().length()<4){
                    cpin.setError("Pin Must Have 4 Digits");
                    //Toast.makeText(getApplicationContext(),"Pin Must Have 4 Digits",Toast.LENGTH_LONG).show();
                }
                else if(Pin.equals(cpin.getText().toString().trim())){
                    Cpin=cpin.getText().toString().trim();
                    cursor=handler.getDataByReg_User_Name(username);
                    if(cursor.getCount()>0) {
                    Toast.makeText(getApplicationContext(),"UserName is Already Registered!",Toast.LENGTH_LONG).show();
                        email.setText("");
                        pin.setText("");
                        cpin.setText("");
                    }
                    else {
                        handler.insert(username, Pin, Cpin);
                        Toast.makeText(getApplicationContext(), "You Can Login Now!", Toast.LENGTH_LONG).show();
                        Intent in = new Intent(getApplicationContext(), Activity_Login.class);
                        startActivity(in);
                        email.setText("");
                        pin.setText("");
                        cpin.setText("");
                    }
                }

                else{
                    cpin.setError("Pin and Confirm Pin not matched");
                    //Toast.makeText(getApplicationContext(),"Pin and Confirm Pin not matched",Toast.LENGTH_LONG).show();
                }
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

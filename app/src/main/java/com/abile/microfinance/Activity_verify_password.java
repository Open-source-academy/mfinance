package com.abile.microfinance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_verify_password extends Activity {
    private Session session;
    String Name,Pin;
    EditText pwd;
    Button login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_verify_password);
        session=new Session(this);
       Name= session.getName();
       Pin=session.getPin();
      pwd= (EditText) findViewById(R.id.pin);
      login= (Button) findViewById(R.id.btn_login);
      login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String Lpin=pwd.getText().toString();
              if(Pin.equals(Lpin)){
                  startActivity(new Intent(getApplicationContext(),Activity_Main_Menu.class));
              }
              else{
                  pwd.setText("");
                  Snackbar.make(view,"PIN Number is Incorrect!!!",Snackbar.LENGTH_LONG).show();
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

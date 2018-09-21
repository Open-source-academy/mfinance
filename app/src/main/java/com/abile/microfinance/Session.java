package com.abile.microfinance;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    String uname,pin;
    public Session(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean logggedin){
        editor.putBoolean("loggedInmode",logggedin);
        editor.commit();
    }

    public String getName(){
        return prefs.getString("Uname","");
    }
    public void setName(String name){
        this.uname=name;
        editor.putString("Uname",name);
        editor.commit();
    }

    public String getPin(){
        return prefs.getString("pin","");
    }
    public void setPin(String pin){
        this.pin=pin;
        editor.putString("pin",pin);
        editor.commit();

    }

    public boolean loggedin(){

        return prefs.getBoolean("loggedInmode", false);

    }
}

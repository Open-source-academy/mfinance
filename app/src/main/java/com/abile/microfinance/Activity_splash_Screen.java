package com.abile.microfinance;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;
import android.widget.Toast;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.SEND_SMS;

public class Activity_splash_Screen extends Activity {

    Handler h;
    Runnable run;


    private Session session;
    private static final int PERMISSION_REQUEST_CODE = 200;

    private static final int PERMS_REQUEST_CODE = 123;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_splash_screen);
        if (checkPermission()) {

           // Toast.makeText(getApplicationContext(), "Permission already granted.", Toast.LENGTH_LONG).show();
            session=new Session(this);
            if(session.loggedin()){
                Intent in=new Intent(getApplicationContext(),Activity_verify_password.class);
                startActivity(in);
            }
            else{
                h=new Handler();
                h.postDelayed(run=new Runnable() {
                    @Override
                    public void run() {
                        Intent in=new Intent(getApplicationContext(),Activity_Login.class);
                        startActivity(in);
                    }
                },3000);
            }


        } else {

            requestPermission();
        }

        /*private void logout(){
            session.setLoggedin(false);
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }*/


    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, SEND_SMS}, PERMISSION_REQUEST_CODE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean smsAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted && smsAccepted) {
                        //Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                        h = new Handler();
                        h.postDelayed(run = new Runnable() {
                            @Override
                            public void run() {
                                Intent in = new Intent(getApplicationContext(), Activity_Login.class);
                                startActivity(in);
                            }
                        }, 3000);
                    }
                    else {

                        Toast.makeText(getApplicationContext(), "Permission Denied, era.", Toast.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, SEND_SMS},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Activity_splash_Screen.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private void makeFolder() {
        h=new Handler();
        h.postDelayed(run=new Runnable() {
            @Override
            public void run() {
                Intent in=new Intent(getApplicationContext(),Activity_Login.class);
                startActivity(in);
            }
        },3000);
    }
}

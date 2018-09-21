package com.abile.microfinance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.abile.microfinance.FinanceDatabaseHandler.DataHandler;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;


import custom_font.MyEditText;
import custom_font.MyTextView;

import static android.content.ContentValues.TAG;

public class Activity_Create_Customer extends  AppCompatActivity implements View.OnClickListener {
    MyTextView login;
    MyEditText name,fname,mobile,address;
    String Customer_Name,Customer_Father_Name,Customer_Mobile,Customer_Address;
    AwesomeValidation awesomeValidation;

    DataHandler handler;
    Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icons_home);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        //toggleHideyBar();
        setContentView(R.layout.activity_create_customer);



        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        name= (MyEditText) findViewById(R.id.cname);
        fname= (MyEditText) findViewById(R.id.fname);
        mobile= (MyEditText) findViewById(R.id.mobile);
        address= (MyEditText) findViewById(R.id.address);
        addValidationToViews();
        login= (MyTextView) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    //back press click event
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void addValidationToViews() {
        awesomeValidation.addValidation(this, R.id.cname, RegexTemplate.NOT_EMPTY, R.string.name_error);
        awesomeValidation.addValidation(this,R.id.fname,RegexTemplate.NOT_EMPTY,R.string.fname_error);
       // awesomeValidation.addValidation(this,R.id.mobile,"^[+]?[0-9]{10,13}$",R.string.mobile_error);
        awesomeValidation.addValidation(this,R.id.address,RegexTemplate.NOT_EMPTY,R.string.address_error);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                submitForm();
                break;
        }
    }
    private void submitForm() {
        // Validate the form...
        if (awesomeValidation.validate()) {
            // Here, we are sure that form is successfully validated. So, do your stuffs now...
            Customer_Name=name.getText().toString().trim();
            Customer_Mobile=mobile.getText().toString().trim();
            Customer_Address=address.getText().toString().trim();
            handler=new DataHandler(getApplicationContext());
            cursor=handler.CheckMobile(Customer_Mobile);

            if(Customer_Mobile.length()==0){
                mobile.setError("Mobile field cant be empty!!");
            }
            else if(Customer_Mobile.length()!=10){
                mobile.setError("mobile number must contains 10 dgits");
            }
            else if(cursor.getCount()>0){
                Toast.makeText(getApplicationContext(),"Customer Mobile already exists!",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(getApplicationContext(),Activity_Create_Customer.class));
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.mobile_exisits_alert);

                Button yes=(Button)dialog.findViewById(R.id.btn_dialog);
                Button No= (Button) dialog.findViewById(R.id.btn_dialog_no);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(Activity_Create_Customer.this, Activity_Customer_Loan_Entry.class);
                        in.putExtra("Cus_Name", Customer_Name);
                        in.putExtra("Cus_Mobile", Customer_Mobile);
                        in.putExtra("Cus_Address", Customer_Address);
                        startActivity(in);
                        name.setText("");
                        fname.setText("");
                        mobile.setText("");
                        address.setText("");
                        dialog.dismiss();
                    }
                });
                No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
            else{
                Intent in = new Intent(Activity_Create_Customer.this, Activity_Customer_Loan_Entry.class);
                in.putExtra("Cus_Name", Customer_Name);
                in.putExtra("Cus_Mobile", Customer_Mobile);
                in.putExtra("Cus_Address", Customer_Address);
                startActivity(in);
                name.setText("");
                fname.setText("");
                mobile.setText("");
                address.setText("");

            }

        }
    }

    /**
     * Detects and toggles immersive mode (also known as "hidey bar" mode).
     */
    public void toggleHideyBar() {

        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = Activity_Create_Customer.this.getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(TAG, "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        Activity_Create_Customer.this.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
}

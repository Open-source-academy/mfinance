package com.abile.microfinance;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.abile.microfinance.FinanceDatabaseHandler.DataHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import custom_font.MyTextView;

import static android.content.ContentValues.TAG;

public class Activity_Customer_Loan_Result extends AppCompatActivity {
    String cus_name,loan_num,loan_type="",Customer_Mobile,Customer_Address;
    int  loan_amt,time,due,interest;
    double total_amt,net_amt;
    MyTextView cname,loan_no,loan_amount,loan_int,loan_dur,loan_due_amount,loan_total_amount,loan_net_amount,save;

    String Loan_Amount,Loan_Interest,Loan_Duration,Loan_Due,Loan_Total_Amount,Loan_Net_Amount;
    LinearLayout linterest,ldue;

    DataHandler handler;
    Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
       // toggleHideyBar();
        setContentView(R.layout.activity_customer_loan_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icons_home);
        cname= (MyTextView) findViewById(R.id.cname);
        loan_no= (MyTextView) findViewById(R.id.loan_no);
        loan_amount= (MyTextView) findViewById(R.id.loan_amount);
        loan_int= (MyTextView) findViewById(R.id.lint);
        loan_dur= (MyTextView) findViewById(R.id.loan_time);
        loan_due_amount= (MyTextView) findViewById(R.id.loan_due_amount);
        loan_total_amount= (MyTextView) findViewById(R.id.total_amount);
        linterest= (LinearLayout) findViewById(R.id.layout_interest);
        ldue= (LinearLayout) findViewById(R.id.layout_due);
        loan_int.setVisibility(View.GONE);
        linterest.setVisibility(View.GONE);
        loan_due_amount.setVisibility(View.VISIBLE);
        ldue.setVisibility(View.VISIBLE);

        loan_type=getIntent().getStringExtra("Loan_Type");


        if(loan_type.equals("Daily")) {

            loan_int.setVisibility(View.GONE);
            linterest.setVisibility(View.GONE);
            loan_due_amount.setVisibility(View.VISIBLE);
            ldue.setVisibility(View.VISIBLE);
            cus_name=getIntent().getStringExtra("Cus_Name");
            Customer_Mobile=getIntent().getStringExtra("Cus_Mobile");
            Customer_Address=getIntent().getStringExtra("Cus_Address");
            loan_amt=getIntent().getIntExtra("Loan_Amount",0);
            time=getIntent().getIntExtra("Loan_Duration",0);
            due=getIntent().getIntExtra("Due_Amount",0);
            total_amt=getIntent().getIntExtra("Total_Amount",0);


            loan_num=Generate_Loan_num();
            Loan_Amount=String.valueOf(loan_amt);
            Loan_Duration=String.valueOf(time) + " Days";
            Loan_Due=String.valueOf(due);
            Loan_Total_Amount=String.valueOf(total_amt);
            Loan_Net_Amount=String.valueOf(net_amt);


            cname.setText(cus_name);
            loan_no.setText("Loan No: "+loan_num);
            loan_amount.setText("Loan Amount: "+Loan_Amount);
            loan_dur.setText("Duration :"+Loan_Duration);
            loan_due_amount.setText("Due Amount: "+Loan_Due);
            loan_total_amount.setText("Total: "+Loan_Total_Amount);
            //name,mobile,address,loan_id,loan_amount,loan_type,loan_interest,loan_time,loan_due
            //sara,9698,madu,cus-456,900,daily,0,10,100

        }
        else if(loan_type.equals("Weekly")){

            loan_int.setVisibility(View.VISIBLE);
            linterest.setVisibility(View.VISIBLE);
            loan_due_amount.setVisibility(View.GONE);
            ldue.setVisibility(View.GONE);
            cus_name=getIntent().getStringExtra("Cus_Name");
            Customer_Mobile=getIntent().getStringExtra("Cus_Mobile");
            Customer_Address=getIntent().getStringExtra("Cus_Address");
            loan_amt=getIntent().getIntExtra("Loan_Amount",0);
            interest=getIntent().getIntExtra("Loan_Interest",0);
            time=getIntent().getIntExtra("Loan_Duration",0);
            total_amt=getIntent().getDoubleExtra("Total_Amount",0);
            net_amt=getIntent().getDoubleExtra("Net_Amount",0);

            loan_num=Generate_Loan_num();
            Loan_Amount=String.valueOf(loan_amt);
            Loan_Interest=String.valueOf(interest);
            Loan_Duration=String.valueOf(time) + " Weeks";
            Loan_Total_Amount=String.valueOf(net_amt);

            cname.setText(cus_name);
            loan_no.setText("Loan No: "+loan_num);
            loan_amount.setText("Loan Amount: "+Loan_Amount);
            loan_int.setText("Loan Interest:"+Loan_Interest+" %");
            loan_dur.setText("Duration :"+Loan_Duration);
            loan_due_amount.setText("Due Amount: "+Loan_Due);
            loan_total_amount.setText("Total: "+Loan_Total_Amount);

        }
        else{
            loan_int.setVisibility(View.VISIBLE);
            linterest.setVisibility(View.VISIBLE);
            loan_due_amount.setVisibility(View.GONE);
            ldue.setVisibility(View.GONE);

            cus_name=getIntent().getStringExtra("Cus_Name");
            Customer_Mobile=getIntent().getStringExtra("Cus_Mobile");
            Customer_Address=getIntent().getStringExtra("Cus_Address");
            loan_amt=getIntent().getIntExtra("Loan_Amount",0);
            interest=getIntent().getIntExtra("Loan_Interest",0);
            time=getIntent().getIntExtra("Loan_Duration",0);
            total_amt=getIntent().getDoubleExtra("Total_Amount",0);
            net_amt=getIntent().getDoubleExtra("Net_Amount",0);

            loan_num=Generate_Loan_num();
            Loan_Amount=String.valueOf(loan_amt);
            Loan_Interest=String.valueOf(interest);
            Loan_Duration=String.valueOf(time) + " Months";
            Loan_Total_Amount=String.valueOf(net_amt);

            cname.setText(cus_name);
            loan_no.setText("Loan No: "+loan_num);
            loan_amount.setText("Loan Amount: "+Loan_Amount);
            loan_int.setText("Loan Interest:"+Loan_Interest+" %");
            loan_dur.setText("Duration :"+Loan_Duration);
            loan_due_amount.setText("Due Amount: "+Loan_Due);
            loan_total_amount.setText("Total: "+Loan_Total_Amount);
        }

        save= (MyTextView) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savecontact();
            }
        });


    }



    //back press click event
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(getApplicationContext(),Activity_Main_Menu.class));
        finish();
        return true;
    }



    private String Generate_Loan_num() {

        // create instance of Random class
        Random rand = new Random();
        // Generate random integers in range 0 to 999
        int rand_int = rand.nextInt(10000);
        String id="CUS-"+rand_int;
        return id;
    }


    private void savecontact() {

        if(loan_type.equals("Daily")) {

            handler=new DataHandler(getApplicationContext());
            String Balance_Amount=Loan_Total_Amount;
                handler.insert(cus_name, Customer_Mobile, Customer_Address, loan_num, Loan_Amount, loan_type, "0", Loan_Duration, Loan_Due, Loan_Total_Amount,Balance_Amount);
                String interest="0";
                SendSms(cus_name,Customer_Mobile,loan_num,Loan_Amount,loan_type,interest,Loan_Duration,Loan_Due,Loan_Total_Amount);
               /* Intent in = new Intent(Activity_Customer_Loan_Result.this, Customer_List_Activity.class);
                in.putExtra("Cus_Name", cus_name);
                in.putExtra("Cus_Mobile", Customer_Mobile);
                in.putExtra("Cus_Address", Customer_Address);
                in.putExtra("Loan_Id", loan_num);
                in.putExtra("Loan_Type", loan_type);
                in.putExtra("Loan_Amount", Loan_Amount);
                in.putExtra("Loan_Duration", Loan_Duration);
                in.putExtra("Due_Amount", Loan_Due);
                in.putExtra("Loan_Total_Amount", Loan_Total_Amount);
                startActivity(in);*/
            //}

        }
        else if(loan_type.equals("Weekly")){
           /* handler=new DataHandler(getApplicationContext());
            cursor=handler.getDataByMobile(Customer_Mobile);
            if(cursor.getCount()>0){
                Toast.makeText(getApplicationContext(),"Customer Mobile already exists!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Activity_Create_Customer.class));
            }
            else {*/

                handler = new DataHandler(getApplicationContext());
            String Balance_Amount=Loan_Total_Amount;
                handler.insert(cus_name, Customer_Mobile, Customer_Address, loan_num, Loan_Amount, loan_type , Loan_Interest, Loan_Duration, Loan_Due, Loan_Total_Amount,Balance_Amount);
                SendSms(cus_name,Customer_Mobile,loan_num,Loan_Amount,loan_type,Loan_Interest,Loan_Duration,Loan_Due,Loan_Total_Amount);
               /* Intent in = new Intent(Activity_Customer_Loan_Result.this, Customer_List_Activity.class);
                in.putExtra("Cus_Name", cus_name);
                in.putExtra("Cus_Mobile", Customer_Mobile);
                in.putExtra("Cus_Address", Customer_Address);
                in.putExtra("Loan_Id", loan_num);
                in.putExtra("Loan_Type", loan_type);
                in.putExtra("Loan_Amount", Loan_Amount);
                in.putExtra("Loan_Duration", Loan_Duration);
                in.putExtra("Due_Amount", Loan_Due);
                in.putExtra("Loan_Total_Amount", Loan_Total_Amount);
                startActivity(in);*/
           // }
        }
        else{
           /* handler=new DataHandler(getApplicationContext());
            cursor=handler.getDataByMobile(Customer_Mobile);
            if(cursor.getCount()>0){
                Toast.makeText(getApplicationContext(),"Customer Mobile already exists!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Activity_Create_Customer.class));
            }
            else {*/
                handler = new DataHandler(getApplicationContext());
            String Balance_Amount=Loan_Total_Amount;
                handler.insert(cus_name, Customer_Mobile, Customer_Address, loan_num, Loan_Amount, loan_type, Loan_Interest, Loan_Duration, Loan_Due, Loan_Total_Amount,Balance_Amount);
                SendSms(cus_name,Customer_Mobile,loan_num,Loan_Amount,loan_type,Loan_Interest,Loan_Duration,Loan_Due,Loan_Total_Amount);
                /*Intent in = new Intent(Activity_Customer_Loan_Result.this, Customer_List_Activity.class);
                in.putExtra("Cus_Name", cus_name);
                in.putExtra("Cus_Mobile", Customer_Mobile);
                in.putExtra("Cus_Address", Customer_Address);
                in.putExtra("Loan_Id", loan_num);
                in.putExtra("Loan_Type", loan_type);
                in.putExtra("Loan_Amount", Loan_Amount);
                in.putExtra("Loan_Duration", Loan_Duration);
                in.putExtra("Due_Amount", Loan_Due);
                in.putExtra("Loan_Total_Amount", Loan_Total_Amount);
                startActivity(in);*/
           // }

        }
    }

    private void SendSms(String cus_name, String customer_mobile, String loan_num, String loan_amount, String loan_type, String loan_interest, String loan_duration, String loan_due, String loan_total_amount) {
       /* String msg="Dear "+ cus_name +" Your loan was successfully created."+"\n"+ "Loan ID: " +loan_num+"\n"+ "Loan Type: "+ loan_type+ "\n"
                + "Loan Amount: " + loan_amount+ "\n" +"Loan Interest: "+ loan_interest+ "\n" +"Duration: "+ loan_duration+"\n" +"Total Amount: "+ loan_total_amount;
        //Getting intent and PendingIntent instance
        Intent intent=new Intent(getApplicationContext(),Customer_List_Activity.class);
        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

        //Get the SmsManager instance and call the sendTextMessage method to send message
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(customer_mobile, null, msg, pi,null);*/
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.sucess_message_alert);
        TextView text = (TextView) dialog.findViewById(R.id.msg_text);
        text.setText("New Loan Issued Successfully");
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Customer_List_Activity.class);
                startActivity(intent);
                dialog.dismiss();

            }
        });
dialog.show();
       /* Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                Toast.LENGTH_LONG).show();*/
    }


    /**
     * Detects and toggles immersive mode (also known as "hidey bar" mode).
     */
    public void toggleHideyBar() {

        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = Activity_Customer_Loan_Result.this.getWindow().getDecorView().getSystemUiVisibility();
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

        Activity_Customer_Loan_Result.this.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
}

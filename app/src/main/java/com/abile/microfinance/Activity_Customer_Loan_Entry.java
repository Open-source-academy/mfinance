package com.abile.microfinance;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import custom_font.MyEditText;
import custom_font.MyTextView;

import static android.content.ContentValues.TAG;

public class Activity_Customer_Loan_Entry extends AppCompatActivity implements View.OnClickListener {
    RadioGroup loan_types;
    RadioButton daily,weeks,month;
    MyEditText loanamount, loan_times,loan_interest,loandue;
    MyTextView cname,save;
    String Customer_Name,Customer_Father_Name,Customer_Mobile,Customer_Address;
    int loan_amount,duration,interest,due_amount;
    double total_amount,net_amount;
    //900,10,0,100,1000,1100
    LinearLayout linterest,ldue;
    AwesomeValidation awesomeValidation;
    String loan_type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
       // toggleHideyBar();
        setContentView(R.layout.activity_customer_loan_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icons_home);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        Customer_Name=getIntent().getStringExtra("Cus_Name");
        Customer_Mobile=getIntent().getStringExtra("Cus_Mobile");
        Customer_Address=getIntent().getStringExtra("Cus_Address");

        cname= (MyTextView) findViewById(R.id.cname);
        cname.setText(Customer_Name);

        loan_times= (MyEditText) findViewById(R.id.ltimes);
        loan_times.setHint("Number Of Days");

        loan_interest= (MyEditText) findViewById(R.id.lint);
        loan_interest.setVisibility(View.GONE);


        linterest= (LinearLayout) findViewById(R.id.layout_interest);
        linterest.setVisibility(View.GONE);


        loanamount= (MyEditText) findViewById(R.id.lamt);
        loandue= (MyEditText) findViewById(R.id.ldue);


        ldue= (LinearLayout) findViewById(R.id.layout_due);
        loandue.setVisibility(View.VISIBLE);

        ldue.setVisibility(View.VISIBLE);
        daily= (RadioButton) findViewById(R.id.radioDaily);
        weeks= (RadioButton) findViewById(R.id.radioWeek);
        month= (RadioButton) findViewById(R.id.radioMonth);

        loan_types= (RadioGroup) findViewById(R.id.radioLoan);
        loan_types.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.radioDaily){
                    loan_times.setHint("NUMBER OF DAYS");
                    loan_interest.setVisibility(View.GONE);
                    linterest.setVisibility(View.GONE);
                    loandue.setVisibility(View.VISIBLE);
                    ldue.setVisibility(View.VISIBLE);;
                    //loan_interest.setHint("Interest %");
                }
                else if(checkedId==R.id.radioWeek){
                    loan_times.setHint("NUMBER OF WEEKS");
                    loan_interest.setHint("INTEREST %");
                    loan_interest.setVisibility(View.VISIBLE);
                    linterest.setVisibility(View.VISIBLE);
                    loandue.setVisibility(View.GONE);
                    ldue.setVisibility(View.GONE);
                }
                else if(checkedId==R.id.radioMonth){
                    loan_times.setHint("NUMBER OF MONTHS");
                    loan_interest.setHint("INTEREST %");
                    loan_interest.setVisibility(View.VISIBLE);
                    linterest.setVisibility(View.VISIBLE);
                    loandue.setVisibility(View.GONE);
                    ldue.setVisibility(View.GONE);
                }
            }
        });
        addValidationToViews();

        save= (MyTextView) findViewById(R.id.save);
        save.setOnClickListener(this);
        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loan_type="Daily";
            }
        });
        weeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loan_type="Weekly";
            }
        });
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loan_type="Monthly";
            }
        });
    }


    //back press click event
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(getApplicationContext(),Activity_Main_Menu.class));
        finish();
        return true;
    }

    private void addValidationToViews() {
        awesomeValidation.addValidation(this, R.id.lamt, RegexTemplate.NOT_EMPTY, R.string.loan_error);
        awesomeValidation.addValidation(this, R.id.ldue, RegexTemplate.NOT_EMPTY, R.string.due_error);

        if(daily.isChecked() && loan_times.getText().toString().equals("")){
            awesomeValidation.addValidation(this, R.id.ltimes, RegexTemplate.NOT_EMPTY, R.string.days_error);
        }
        else if(weeks.isChecked() && loan_times.getText().toString().equals("")){
            awesomeValidation.addValidation(this, R.id.ltimes, RegexTemplate.NOT_EMPTY, R.string.week_error);
            awesomeValidation.addValidation(this, R.id.lint, RegexTemplate.NOT_EMPTY, R.string.interest_error);

        }
        else {
            awesomeValidation.addValidation(this, R.id.ltimes, RegexTemplate.NOT_EMPTY, R.string.month_error);
            awesomeValidation.addValidation(this, R.id.lint, RegexTemplate.NOT_EMPTY, R.string.interest_error);
        }



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                submitForm();
                break;
        }

    }

    public void submitForm() {

        // Validate the form...
        if(loan_type=="Daily"|| loan_type=="Weekly"||loan_type=="Monthly") {

                if (loan_type.equals("Daily")) {
                    if(loanamount.getText().toString().equals("")){
                        loanamount.setError("Loan amount is required");
                    }
                    else if(loan_times.getText().toString().equals("")){
                        loan_times.setError("Loan duration is required");
                    }
                    else if(loandue.getText().toString().equals("")){
                        loandue.setError("Due amount is required");
                    }
                    else {
                        String amt = loanamount.getText().toString();
                        loan_amount = Integer.parseInt(amt);
                        duration = Integer.parseInt(loan_times.getText().toString());
                        due_amount = Integer.parseInt(loandue.getText().toString());
                        total_amount = duration * due_amount;

                        // net_amount=total_amount+due_amount;

                        Intent in = new Intent(Activity_Customer_Loan_Entry.this, Activity_Customer_Loan_Result.class);
                        in.putExtra("Cus_Name", Customer_Name);
                        in.putExtra("Cus_Mobile", Customer_Mobile);
                        in.putExtra("Cus_Address", Customer_Address);
                        in.putExtra("Loan_Type", loan_type);
                        in.putExtra("Loan_Amount", loan_amount);
                        in.putExtra("Loan_Duration", duration);
                        in.putExtra("Due_Amount", due_amount);
                        in.putExtra("Total_Amount", total_amount);
                        startActivity(in);
                        loanamount.setText("");
                        loan_times.setText("");
                        loandue.setText("");
                        loan_interest.setText("");
                    }
                } else if (loan_type.equals("Weekly")) {

                        if(loanamount.getText().toString().equals("")){
                            loanamount.setError("Loan amount is required");
                        }
                        else if(loan_times.getText().toString().equals("")){
                            loan_times.setError("Loan duration is required");
                        }
                        else if(loan_interest.getText().toString().equals("")){
                            loan_interest.setError("Loan Interest is required");
                        }
                        else {
                            loan_amount = Integer.parseInt(loanamount.getText().toString());
                            duration = Integer.parseInt(loan_times.getText().toString());
                            interest = Integer.parseInt(loan_interest.getText().toString());
                            double P=(double)loan_amount;
                            double N=(double)duration;
                            double r=(double)interest;

                            double R = (r / 12) / 100;
                            double EMI = (P * R * (Math.pow((1 + R), N)) / ((Math.pow((1 + R), N)) - 1));
                            double totalInt = Math.round((EMI * N) - P);
                            double totalAmt = Math.round((EMI * N));
                            total_amount = totalAmt;
                            net_amount = total_amount;

                            Intent in = new Intent(Activity_Customer_Loan_Entry.this, Activity_Customer_Loan_Result.class);
                            in.putExtra("Cus_Name", Customer_Name);
                            in.putExtra("Cus_Mobile", Customer_Mobile);
                            in.putExtra("Cus_Address", Customer_Address);
                            in.putExtra("Loan_Type", loan_type);
                            in.putExtra("Loan_Amount", loan_amount);
                            in.putExtra("Loan_Interest", interest);
                            in.putExtra("Loan_Duration", duration);
                            in.putExtra("Total_Amount", total_amount);
                            in.putExtra("Net_Amount", net_amount);
                            startActivity(in);
                            loanamount.setText("");
                            loan_times.setText("");
                            loan_interest.setText("");
                        }

                } else {
                    if(loanamount.getText().toString().equals("")){
                        loanamount.setError("Loan amount is required");
                    }
                    else if(loan_times.getText().toString().equals("")){
                        loan_times.setError("Loan duration is required");
                    }
                    else if(loan_interest.getText().toString().equals("")){
                        loan_interest.setError("Loan Interest is required");
                    }
                    else {
                        loan_amount = Integer.parseInt(loanamount.getText().toString());
                        duration = Integer.parseInt(loan_times.getText().toString());
                        interest = Integer.parseInt(loan_interest.getText().toString());

                        double P=(double)loan_amount;
                        double N=(double)duration;
                        double r=(double)interest;
                        double R = (r / 12) / 100;
                        double EMI = (P * R * (Math.pow((1 + R), N)) / ((Math.pow((1 + R), N)) - 1));
                        double totalInt = Math.round((EMI * N) - P);
                        double totalAmt = Math.round((EMI * N));
                        total_amount = totalAmt;
                        net_amount = total_amount;

                        Intent in = new Intent(Activity_Customer_Loan_Entry.this, Activity_Customer_Loan_Result.class);
                        in.putExtra("Cus_Name", Customer_Name);
                        in.putExtra("Cus_Mobile", Customer_Mobile);
                        in.putExtra("Cus_Address", Customer_Address);
                        in.putExtra("Loan_Type", loan_type);
                        in.putExtra("Loan_Amount", loan_amount);
                        in.putExtra("Loan_Interest", interest);
                        in.putExtra("Loan_Duration", duration);
                        in.putExtra("Total_Amount", total_amount);
                        in.putExtra("Net_Amount", net_amount);
                        startActivity(in);
                        loanamount.setText("");
                        loan_times.setText("");
                        loan_interest.setText("");
                    }
                }

        }
        else{
            Toast.makeText(Activity_Customer_Loan_Entry.this,"Select any one loan type",Toast.LENGTH_LONG).show();
        }

    }
    /**
     * Detects and toggles immersive mode (also known as "hidey bar" mode).
     */
    public void toggleHideyBar() {

        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = Activity_Customer_Loan_Entry.this.getWindow().getDecorView().getSystemUiVisibility();
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

        Activity_Customer_Loan_Entry.this.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

}

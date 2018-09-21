package com.abile.microfinance;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.abile.microfinance.FinanceDatabaseHandler.DataHandler;

import java.util.List;

public class Activity_get_Customer_loan_data extends AppCompatActivity {
    EditText mobile;
    Button look;
    String mobile_number,loan_id;
    // Spinner element
    Spinner spinner;
    SQLiteDatabase myDatabase = null;
    private DataHandler handler;
    Cursor cursor;
    ImageView select;




    String DataBase_Name = DataHandler.DataHelper.DATABASE_NAME;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_get_customer_loan_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icons_home);
        myDatabase = openOrCreateDatabase(DataHandler.DataHelper.DATABASE_NAME, MODE_PRIVATE,
                null);

        mobile= (EditText) findViewById(R.id.ed_mobile);
        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);
        look= (Button) findViewById(R.id.select_view);
        look.setVisibility(View.GONE);

        select= (ImageView) findViewById(R.id.select_btn);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile_number = mobile.getText().toString();
               /* Intent in=new Intent(getApplicationContext(),TimeLineActivity.class);
                in.putExtra("cus_mobile",mobile_number);
                startActivity(in);;*/
                // database handler

                if(mobile_number.length()==0){
                    mobile.setError("Mobile field cant be empty!!");
                }
                else if(mobile_number.length()!=10){
                    mobile.setError("mobile number must contains 10 dgits");
                }
                else {
                    handler=new DataHandler(getApplicationContext());
                    cursor=handler.getDataByMobile(mobile_number);

                    if (cursor.getCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No collection record for this mobile Number", Toast.LENGTH_LONG).show();

                        spinner.setVisibility(View.GONE);

                        look.setVisibility(View.GONE);

                    } else {
                        handler = new DataHandler(getApplicationContext());

                        // Spinner Drop down elements
                        List<String> lables = handler.getAllLabels(mobile_number);
                        lables.add(0, "Select Loan id");

                        // Creating adapter for spinner
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.loanid_spinner_item, lables);

                        // Drop down layout style - list view with radio button
                        arrayAdapter
                                .setDropDownViewResource(R.layout.loanid_spinner_item);

                        // attaching data adapter to spinner
                        spinner.setAdapter(arrayAdapter);
                        spinner.setVisibility(View.VISIBLE);
                        look.setVisibility(View.VISIBLE);

                    }
                }

            }
        });

        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loan_id.equals("Select Loan id")){
                    Snackbar.make(view,"Invalid LoanID.",Snackbar.LENGTH_LONG).show();
                }else {
                    Intent in = new Intent(getApplicationContext(), TimeLineActivity.class);
                    in.putExtra("cus_mobile", mobile_number);
                    in.putExtra("Cus_loan_id", loan_id);
                    startActivity(in);
                    mobile.setText("");
                    look.setVisibility(View.GONE);
                    spinner.setVisibility(View.GONE);
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loan_id=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    //back press click event
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(getApplicationContext(),Activity_Main_Menu.class));
        finish();
        return true;
    }
}

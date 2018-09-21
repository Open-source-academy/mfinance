package com.abile.microfinance;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.abile.microfinance.FinanceDatabaseHandler.DataHandler;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TimeLineActivity extends AppCompatActivity {

    Intent in;
    private DataHandler handler;
    Cursor cursor;

    String mobile_number,loan_id;
    SQLiteDatabase myDatabase = null;


    String DataBase_Name = DataHandler.DataHelper.DATABASE_NAME;
    String name,loanid,amount,loantype,totalpayamount,paidamt,date,balanceamt;
    String error_msg="No collections made for this customer";
    //Create Timeline Rows List
    private ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();
    ArrayAdapter<TimelineRow> myAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_timeline_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icons_home);
        mobile_number=getIntent().getStringExtra("cus_mobile");
        loan_id=getIntent().getStringExtra("Cus_loan_id");
        myDatabase = openOrCreateDatabase(DataHandler.DataHelper.DATABASE_NAME, MODE_PRIVATE,
                null);



        //Get the ListView and Bind it with the Timeline Adapter
        ListView myListView = (ListView) findViewById(R.id.timeline_listView);


        handler=new DataHandler(getApplicationContext());
        cursor=handler.getDataByDatas(mobile_number,loan_id);



        // Add Random Rows to the List
        if(cursor.moveToFirst()){
            do {
                name=cursor.getString(1);
                loanid=cursor.getString(0);
                amount=cursor.getString(3);
                loantype=cursor.getString(4);
                totalpayamount=cursor.getString(5);
                paidamt=cursor.getString(6);
                date=cursor.getString(7);
                balanceamt=cursor.getString(8);

                timelineRowsList.add(createRandomTimelineRow(name,loanid,amount,loantype,totalpayamount,paidamt,date,balanceamt));

            }while(cursor.moveToNext());

        }

        else{

        }

        //Create the Timeline Adapter
        myAdapter = new TimelineViewAdapter(this, 0, timelineRowsList,
                //if true, list will be sorted by date
                true);
        myListView.setAdapter(myAdapter);

        //if you wish to handle list scrolling
        myListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;
            private LinearLayout lBelow;


            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                this.currentScrollState = scrollState;
               // this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;


            }



        });

        //if you wish to handle the clicks on the rows
        AdapterView.OnItemClickListener adapterListener = new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TimelineRow row = timelineRowsList.get(position);
                Toast.makeText(getApplicationContext(), row.getTitle(), Toast.LENGTH_SHORT).show();
            }
        };
        myListView.setOnItemClickListener(adapterListener);
    }

    //back press click event
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(getApplicationContext(),Activity_Main_Menu.class));
        finish();
        return true;
    }



    private TimelineRow createRandomTimelineRow(String name, String loanid, String amount, String loantype, String totalpayamount, String paidamt, String date, String balanceamt) {
        // Create new timeline row (pass your Id)
        int id=Integer.parseInt(amount);
        TimelineRow myRow = new TimelineRow(id);

        //to set the row Title (optional)

        myRow.setTitle("Customer Name " + name);
        myRow.setDescription("Loan Amount: " +amount+"\n"+"Loan Type:"+loantype+"\n"+"Total Amount With Interest: "+totalpayamount+
                "\n"+"Paid Amount: "+paidamt+"\n"+"Paid on: "+date+"\n"+"Balance Amount: "+balanceamt );
       /* myRow.setDescription("Loan Type:"+loantype);
        myRow.setDescription("Total Amount With Interest "+totalpayamount);
        myRow.setDescription("Paid Amount "+paidamt);
        myRow.setDescription("Paid on "+date);
        myRow.setDescription("Balance Amount"+balanceamt);*/
       // myRow.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.ic_ghost + getRandomNumber(0, 10)));
        //to set the row bitmap image (optional)
        myRow.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.ic_ghost));
        //to set row Below Line Color (optional)
        myRow.setBellowLineColor(Color.argb(255, 255, 64, 129));
// To set row Below Line Size in dp (optional)
        myRow.setBellowLineSize(6);
// To set row Image Size in dp (optional)
        myRow.setImageSize(40);
        // To set background color of the row image (optional)
// To set row Title text color (optional)
        myRow.setTitleColor(Color.argb(255, 243, 59, 30));
// To set row Description text color (optional)
        myRow.setDescriptionColor(Color.argb(255, 255, 255, 255));

        return  myRow;
    }




    //Random Methods
    public int getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        ;
        return color;
    }

    public int getRandomNumber(int min, int max) {
        return min + (int) (Math.random() * max);
    }




}

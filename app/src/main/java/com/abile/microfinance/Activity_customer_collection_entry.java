package com.abile.microfinance;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.abile.microfinance.FinanceDatabaseHandler.DataHandler;
import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;

import org.qap.ctimelineview.TimelineRow;

import java.util.ArrayList;

public class Activity_customer_collection_entry extends AppCompatActivity {
    Intent in;
    private DataHandler handler;
    Cursor cursor;

    String mobile_number;
    SQLiteDatabase myDatabase = null;


    String DataBase_Name = DataHandler.DataHelper.DATABASE_NAME;
    String name,phone,loanid,amount,loantype,loaninterest,duration,dueamt,totamt;

    //Create Timeline Rows List
    private ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();
    ArrayAdapter<TimelineRow> myAdapter;


    ExpandingList mExpandingList;
    interface OnItemCreated {
        void itemCreated(String title);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_customer_collection_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mobile_number=getIntent().getStringExtra("cus_mobile");
        myDatabase = openOrCreateDatabase(DataHandler.DataHelper.DATABASE_NAME, MODE_PRIVATE,
                null);
        mExpandingList = (ExpandingList) findViewById(R.id.expanding_list_main);

        handler=new DataHandler(getApplicationContext());
        cursor=handler.getDataByMobile(mobile_number);


        if(cursor.moveToFirst()){
            do {

                addItem(cursor.getString(1),new String[]{"Loan id :" +cursor.getString(0),"Loan Amount :"  +cursor.getString(3),
                        "Loan Type :"  +cursor.getString(4),"Total Payable Amount :"+cursor.getString(5),"Paid Amount :"+cursor.getString(6),
                "Date :"+cursor.getString(7),"Balance Amount :"+cursor.getString(8)},R.color.pink,R.drawable.ic_ghost);
               /* addItem(cursor.getString(0),new String[]{"Loan Amount :"  +cursor.getString(1),"Loan Type :"  +cursor.getString(2),
                        "Total Amount :"  +cursor.getString(3),"Paid Amount :" +cursor.getString(4),"Date :"+cursor.getString(5),
                        "Balance Amount :" +cursor.getString(6)},R.color.pink,R.drawable.ic_ghost);*/
            }while(cursor.moveToNext());
        }



       /* int to[]={R.id.text_name,R.id.text_mobile,R.id.text_amount,R.id.text_type,R.id.text_due};
        String from[]={DataHandler.DataHelper.COL_CUSTOMER_NAME,DataHandler.DataHelper.COL_LOAN_AMOUNT,DataHandler.DataHelper.COL_LOAN_TYPE,DataHandler.DataHelper.COL_LOAN_DUE_AMOUNT};
        final SimpleCursorAdapter adapter=new SimpleCursorAdapter(getApplicationContext(), R.layout.list_customer_item,cursor,from,to);*/


        //lv.setAdapter(adapter);
       /* if(cursor.moveToFirst()){
            do {
                name=cursor.getString(0);
                phone=cursor.getString(1);
                loanid=cursor.getString(3);
                amount=cursor.getString(4);
                loantype=cursor.getString(5);
                loaninterest=cursor.getString(6);
                duration=cursor.getString(7);
                dueamt=cursor.getString(8);
                totamt=cursor.getString(9);

                // addItem(cursor.getString(0),new String[]{"Loan Id :"  +cursor.getString(3),"Amount :"  +cursor.getString(4),"Loan Type :"  +cursor.getString(5),"Loan_Interest :"  +cursor.getString(6),"Durations :"  +cursor.getString(7),"Due Amount :"  +cursor.getString(8),"Total Amount :"  +cursor.getString(9)},R.color.orange,R.drawable.ic_ghost);
            }while(cursor.moveToNext());
        }
        else{
            Toast.makeText(getApplicationContext(),"No data found for entered phone number!1",Toast.LENGTH_LONG).show();
        }*/
    }


    //back press click event
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void addItem(String title, String[] subItems, int colorRes, int iconRes) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(colorRes);
            item.setIndicatorIconRes(iconRes);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item
            ((TextView) item.findViewById(R.id.title)).setText(title);

            //We can create items in batch.
            item.createSubItems(subItems.length);
            for (int i = 0; i < item.getSubItemsCount(); i++) {
                //Let's get the created sub item by its index
                final View view = item.getSubItemView(i);

                //Let's set some values in
                configureSubItem(item, view, subItems[i]);
            }
               /* item.findViewById(R.id.add_more_sub_items).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showInsertDialog(new OnItemCreated() {
                            @Override
                            public void itemCreated(String title) {
                                View newSubItem = item.createSubItem();
                                configureSubItem(item, newSubItem, title);
                            }
                        });
                    }
                });*/

            item.findViewById(R.id.remove_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpandingList.removeItem(item);
                }
            });
        }
    }

    private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
        ((TextView) view.findViewById(R.id.sub_title)).setText(subTitle);
           /* view.findViewById(R.id.remove_sub_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.removeSubItem(view);
                }
            });*/
    }
}

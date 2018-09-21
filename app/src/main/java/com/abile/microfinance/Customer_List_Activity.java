package com.abile.microfinance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.abile.microfinance.FinanceDatabaseHandler.DataHandler;
import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;



import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;


import org.apache.poi.ss.usermodel.Cell;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;


import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Customer_List_Activity extends AppCompatActivity {

    String Cus_Name;

    //for excel use
    String Name,Cus_Mobile,Cus_Address,Loan_Id,Loan_Type,Loan_Amount,Loan_Interest,Loan_Duration,Due_Amount,Loan_Total_Amount;
    DataHandler handler;
    Cursor cursor;
    FloatingActionButton fab;
    EditText pay_amount;
    SQLiteDatabase myDatabase = null;
    String DataBase_Name = DataHandler.DataHelper.DATABASE_NAME;
    String FilePath; String filename;
    public static final int requestcode = 1;
    String final_loan="";

    ExpandingList mExpandingList;
    interface OnItemCreated {
        void itemCreated(String title);
    }

    private Session session;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_main);

        AssetManager am = getApplicationContext().getAssets();



        fab= (FloatingActionButton) findViewById(R.id.fab);
        mExpandingList = (ExpandingList) findViewById(R.id.expanding_list_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Customer_List_Activity.this,Activity_Create_Customer.class);
                startActivity(in);
            }
        });

        handler=new DataHandler(getApplicationContext());
        cursor=handler.getAllCustomers();

        if(cursor.moveToFirst()){
            do {

                addItem(cursor.getString(0),new String[]{"Mobile :" +cursor.getString(1),"Loan Id :"  +cursor.getString(3),
                        "Amount :"  +cursor.getString(4),"Loan Type :"  +cursor.getString(5),
                        "Loan_Interest :"  +cursor.getString(6) + "%","Durations :"  +cursor.getString(7),
                        "Due Amount :"  +cursor.getString(8),
                        "Total Amount :"  +cursor.getString(9),
                "Balance Amount :" +cursor.getString(10),"Loan Issued Date :"+cursor.getString(11)},R.color.pink,R.drawable.ic_ghost);
            }while(cursor.moveToNext());
        }





        //dispay data by filter
        EditText et = (EditText) findViewById(R.id.myFilter);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mExpandingList.removeAllViews();
                mExpandingList.clearAnimation();
                handler = new DataHandler(getApplicationContext());
                String filter_data=String.valueOf(charSequence);
                cursor = handler.getDataByNames(filter_data);
                if(cursor.moveToFirst()){
                    do {
                        addItem(cursor.getString(0),new String[]{"Loan Id :"  +cursor.getString(3),"Amount :"  +cursor.getString(4),"Loan Type :"  +cursor.getString(5),
                                "Loan_Interest :"  +cursor.getString(6) +" %","Durations :"  +cursor.getString(7),
                                "Due Amount :"  +cursor.getString(8),
                                "Total Amount :"  +cursor.getString(9), "Balance Amount :" +cursor.getString(10),"Loan Issued Date :"+cursor.getString(11)},R.color.green,R.drawable.ic_ghost);
                    }while(cursor.moveToNext());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });


       //createItems();
    }


    //back press click event
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }



    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options item to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(menu instanceof MenuBuilder){

            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);//.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_export:
                try {
                    ExportExcel();
                    break;
                } catch (Exception ex) {
                    Log.e("Error in Activity", ex.toString());
                }
                break;
            case R.id.action_import:
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.import_process_alert);
                Button okButton = (Button) dialog.findViewById(R.id.btn_dialog);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handler = new DataHandler(getApplicationContext());
                        handler.delete_all_loan_records();
                        Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
                        fileintent.setType("gagt/sdf");
                        try {
                            startActivityForResult(fileintent, requestcode);

                            // startActivityForResult(fileintent, requestcode);
                        } catch (ActivityNotFoundException e) {

                            //lbl.setText("No activity can handle picking a file. Showing alternatives.");
                        }
                    }
                });
                dialog.show();
                Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog_cancel);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });

                break;
            case R.id.action_logout:
                logout();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String Name,Cus_Mobile,Cus_Address,Loan_Id,Loan_Type,Loan_Amount,Loan_Interest,Loan_Duration,Due_Amount,Loan_Total_Amount,Loan_Balance_Amount,Date;
        if (data == null)
            return;
        switch (requestCode) {
            case requestcode:
                 FilePath = data.getData().getPath();
                 filename=FilePath.substring(FilePath.lastIndexOf("/")+1);
                try {
                    if (resultCode == RESULT_OK) {
                       // Toast.makeText(getApplicationContext(),FilePath,Toast.LENGTH_LONG).show();
                      //  File sd = Environment.getExternalStorageDirectory();
                       // File directory = new File(sd.getAbsolutePath());
                       // File file = new File(directory, filename);
                        String folder_main = "MyFinance";
                        File sd = Environment.getExternalStorageDirectory();
                        String csvFile = "myData.xls";

                        File directory = new File(Environment.getExternalStorageDirectory(),folder_main);
                        File file = new File(directory, filename);
                        InputStream inStream;
                        HSSFWorkbook wb = null;
                        try {
                            inStream = new FileInputStream(file);
                            wb = new HSSFWorkbook(inStream);
                            inStream.close();
                            HSSFSheet sheet = wb.getSheetAt(0);
                            for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); ) {
                                Row row = rit.next();

                                int rowcount=row.getRowNum();//0
                                if(rowcount!=0) {

                                    //ContentValues contentValues = new ContentValues();
                                    row.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                                    row.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                                    row.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                                    row.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                                    row.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                                    row.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                                    row.getCell(6, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                                    row.getCell(7, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                                    row.getCell(8, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                                    row.getCell(9, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                                    row.getCell(10, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                                    row.getCell(11, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);

                                    Name = row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                                    Cus_Mobile = row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                                    Cus_Address = row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                                    Loan_Id = row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                                    Loan_Amount = row.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                                    Loan_Type = row.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                                    Loan_Interest = row.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                                    Loan_Duration = row.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                                    Due_Amount = row.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                                    Loan_Total_Amount = row.getCell(9, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                                    Loan_Balance_Amount=row.getCell(10, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                                    Date=row.getCell(11, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                                    try {
                                        handler.insertdate(Name, Cus_Mobile, Cus_Address, Loan_Id, Loan_Amount, Loan_Type, Loan_Interest, Loan_Duration, Due_Amount, Loan_Total_Amount,Loan_Balance_Amount,Date);
                                    } catch (Exception ex) {
                                        Log.d("Exception in importing", ex.getMessage().toString());
                                    }
                                }
                            }

                            Toast.makeText(getApplicationContext(),"Excel sheet imported successfully",Toast.LENGTH_LONG).show();
                           startActivity(new Intent(Customer_List_Activity.this,Customer_List_Activity.class));
                            } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {

                }
        }
    }

    private void LoadList() {
        handler=new DataHandler(getApplicationContext());
        cursor=handler.getAllCustomers();
        if(cursor.moveToFirst()){
            do {

                addItem(cursor.getString(0),new String[]{"Mobile :" +cursor.getString(1),"Loan Id :"  +cursor.getString(3),
                        "Amount :"  +cursor.getString(4),"Loan Type :"  +cursor.getString(5),
                        "Loan_Interest :"  +cursor.getString(6) + "%","Durations :"  +cursor.getString(7),
                        "Due Amount :"  +cursor.getString(8),
                        "Total Amount :"  +cursor.getString(9),
                        "Balance Amount :" +cursor.getString(10),"Date :"+cursor.getString(11)},R.color.pink,R.drawable.ic_ghost);
            }while(cursor.moveToNext());
        }
    }


    private void logout(){
            session.setLoggedin(false);
            Toast.makeText(getApplicationContext(),"Logged out successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(Customer_List_Activity.this,Activity_Login.class));
       // finish();
        }

    private void ExportExcel() {
        handler=new DataHandler(getApplicationContext());
        cursor=handler.getAllCustomers();
        if(cursor.getCount()>0) {
            String folder_main = "MyFinance";
            File sd = Environment.getExternalStorageDirectory();
            String csvFile = "Finance_Customer_List_backup.xls";

            File directory = new File(Environment.getExternalStorageDirectory(), folder_main);
            //create directory if not exist
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }
            try {

                //file path
                File file = new File(directory, csvFile);
                file.createNewFile();
                WorkbookSettings wbSettings = new WorkbookSettings();
                wbSettings.setLocale(new Locale("en", "EN"));
                WritableWorkbook workbook;
                workbook = Workbook.createWorkbook(file, wbSettings);
                //Excel sheet name. 0 represents first sheet
                WritableSheet sheet = workbook.createSheet("userList", 0);

                sheet.addCell(new Label(0, 0, "CustomerName")); // column and row
                sheet.addCell(new Label(1, 0, "Mobile"));
                sheet.addCell(new Label(2, 0, "Address"));
                sheet.addCell(new Label(3, 0, "LoanId"));
                sheet.addCell(new Label(4, 0, "Loan_Amount "));
                sheet.addCell(new Label(5, 0, "Loan_Type"));
                sheet.addCell(new Label(6, 0, "Loan_Interest"));
                sheet.addCell(new Label(7, 0, "Loan_Durartions"));
                sheet.addCell(new Label(8, 0, "Loan_Due_Amount"));
                sheet.addCell(new Label(9, 0, "Total_Payable_Amount"));
                sheet.addCell(new Label(10, 0, "Loan_Balance_Amount"));
                sheet.addCell(new Label(11, 0, "Date"));


                if (cursor.moveToFirst()) {
                    do {
                        //  addItem(cursor.getString(0),new String[]{"Loan Id :"  +cursor.getString(3),"Amount :"  +cursor.getString(4),"Loan Type :"  +cursor.getString(5),"Loan_Interest :"  +cursor.getString(6),"Durations :"  +cursor.getString(7),
                        //"Due Amount :"  +cursor.getString(8),"Total Amount :"  +cursor.getString(9)},
                        //  R.color.orange,R.drawable.ic_ghost);
                        String title = cursor.getString(0);
                        String mobile = cursor.getString(1);
                        String Address = cursor.getString(2);
                        String LoanId = cursor.getString(3);
                        String Loan_Amount = cursor.getString(4);
                        String Loan_Type = cursor.getString(5);
                        String Loan_Interest = cursor.getString(6);
                        String Loan_Durartions = cursor.getString(7);
                        String Loan_Due_Amount = cursor.getString(8);
                        String Total_Payable_Amount = cursor.getString(9);
                        String Balance_Amount = cursor.getString(10);
                        String Date = cursor.getString(11);

                        int i = cursor.getPosition() + 1;
                        sheet.addCell(new Label(0, i, title));
                        sheet.addCell(new Label(1, i, mobile));
                        sheet.addCell(new Label(2, i, Address));
                        sheet.addCell(new Label(3, i, LoanId));
                        sheet.addCell(new Label(4, i, Loan_Amount));
                        sheet.addCell(new Label(5, i, Loan_Type));
                        sheet.addCell(new Label(6, i, Loan_Interest));
                        sheet.addCell(new Label(7, i, Loan_Durartions));
                        sheet.addCell(new Label(8, i, Loan_Due_Amount));
                        sheet.addCell(new Label(9, i, Total_Payable_Amount));
                        sheet.addCell(new Label(10, i, Balance_Amount));
                        sheet.addCell(new Label(11, i, Date));


                    } while (cursor.moveToNext());
                }
                //closing cursor
                cursor.close();
                workbook.write();
                workbook.close();
                // Toast.makeText(getApplication(), "Data Exported into this location Internalstorage/MyFinance  ", Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.sucess_message_alert);
                TextView text = (TextView) dialog.findViewById(R.id.msg_text);
                text.setText("Data Exported into this location Internalstorage/MyFinance");
                Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });
                dialog.show();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.sucess_message_alert);
            TextView text = (TextView) dialog.findViewById(R.id.msg_text);
            text.setText("There is no datas are available for export,Add some datas and try again");
            Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });
            dialog.show();

        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Activity_Main_Menu.class));
    }

   /* private void createItems() {
            addItem(Cus_Name, new String[]{"House", "Boat", "Candy", "Collection", "Sport", "Ball", "Head"}, R.color.pink, R.drawable.ic_ghost);
            addItem("Mary", new String[]{"Dog", "Horse", "Boat"}, R.color.blue, R.drawable.ic_ghost);
            addItem("Ana", new String[]{"Cat"}, R.color.purple, R.drawable.ic_ghost);
            addItem("Peter", new String[]{"Parrot", "Elephant", "Coffee"}, R.color.yellow, R.drawable.ic_ghost);
            addItem("Joseph", new String[]{}, R.color.orange, R.drawable.ic_ghost);
            addItem("Paul", new String[]{"Golf", "Football"}, R.color.green, R.drawable.ic_ghost);
            addItem("Larry", new String[]{"Ferrari", "Mazda", "Honda", "Toyota", "Fiat"}, R.color.blue, R.drawable.ic_ghost);
            addItem("Moe", new String[]{"Beans", "Rice", "Meat"}, R.color.yellow, R.drawable.ic_ghost);
            addItem("Bart", new String[]{"Hamburger", "Ice cream", "Candy"}, R.color.purple, R.drawable.ic_ghost);
        }*/

        private void addItem(String title, final String[] subItems, int colorRes, int iconRes) {
            //Let's create an item with R.layout.expanding_layout
            final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout);
            TextView ScrollTitle=(TextView) item.findViewById(R.id.title);
            Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Lato-Regular.ttf");

            ScrollTitle.setTypeface(custom_font);

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
                item.findViewById(R.id.add_more_sub_items).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = ((TextView) item.findViewById(R.id.title)).getText().toString();

                        String loanid=subItems[1];

                        String loan[]=loanid.split(":");
                        String final_loan=loan[1];

                        //Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
                        handler=new DataHandler(getApplicationContext());
                        cursor=handler.getDataByNames_loanid(title,final_loan);

                        if(cursor.moveToFirst()) {
                            do {

                                String name = cursor.getString(0);
                                String mobile=cursor.getString(1);
                                String Loan_id=cursor.getString(3);
                                String Loan_Amount = cursor.getString(4);
                                String Loan_type = cursor.getString(5);
                                String Total_Payable_Amount = cursor.getString(9);
                                String Balance_Amount=cursor.getString(10);
                                // Toast.makeText(getApplicationContext(),mobile,Toast.LENGTH_LONG).show();
                                AlertBox(name,mobile,Loan_id, Loan_Amount, Loan_type, Total_Payable_Amount,Balance_Amount);
                            }while(cursor.moveToNext());
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No Data!",Toast.LENGTH_LONG).show();
                        }
                       /* showInsertDialog(new OnItemCreated() {
                            @Override
                            public void itemCreated(String title) {
                                View newSubItem = item.createSubItem();
                                configureSubItem(item, newSubItem, title);
                            }
                        });*/
                    }
                });
               item.findViewById(R.id.remove_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Balance_Amount=null ;
                        float balance_amount;
                       // mExpandingList.removeItem(item);
                        String title = ((TextView) item.findViewById(R.id.title)).getText().toString();


                        String loanid=subItems[1];

                        String loan[]=loanid.split(":");
                         final_loan=loan[1];
                        handler=new DataHandler(getApplicationContext());
                        cursor=handler.getDataByNames_loanid(title,final_loan);

                        if(cursor.moveToFirst()) {
                            do {
                                 Balance_Amount=cursor.getString(10);
                            } while (cursor.moveToNext());
                        }
                        balance_amount=Float.parseFloat(Balance_Amount);
                        if(balance_amount==0){
                            handler.delete_single_customer_by_loanid(final_loan);
                            Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Customer_List_Activity.this,Customer_List_Activity.class));
                        }
                        else{
                            final Dialog dialog = new Dialog(Customer_List_Activity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.error_message_alert);
                            TextView text = (TextView) dialog.findViewById(R.id.text_cname);
                            text.setText("Loan Amount Pending. Do you really want delete the customer?");
                            Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    handler.delete_single_customer_by_loanid(final_loan);
                                    Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Customer_List_Activity.this,Customer_List_Activity.class));

                                }
                            });
                            Button dialog_cancel_button= (Button) dialog.findViewById(R.id.btn_dialog_cancel);
                            dialog_cancel_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();
                        }

                    }
                });


               item.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       String db_mobile=subItems[0];
                       String pho[]=db_mobile.split(":");
                      final String Mobile=pho[1];
                       final String loanid=subItems[1];

                       String loan[]=loanid.split(":");
                     final   String final_loan=loan[1];

                       final Dialog dialog = new Dialog(Customer_List_Activity.this);
                       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                       dialog.setCancelable(false);
                       dialog.setContentView(R.layout.edit_mobile_page);

                       final EditText mobile_txt=(EditText)dialog.findViewById(R.id.txt_mobile);
                       mobile_txt.setText(Mobile);


                       Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
                       dialogButton.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               String updated_mobile=mobile_txt.getText().toString();
                               if(updated_mobile.equals("")){
                                   Toast.makeText(Customer_List_Activity.this,"Enter mobile number!",Toast.LENGTH_LONG).show();
                               }
                               else if(updated_mobile.length()!=10){
                                   Toast.makeText(Customer_List_Activity.this,"Mobile number must have 10 numbers",Toast.LENGTH_LONG).show();
                               }
                               else {

                                   handler = new DataHandler(getApplicationContext());
                                   cursor=handler.get_collection_data(Mobile);
                                   if(cursor.getCount()>0){
                                      handler.update_collection_customer_mobile(updated_mobile, final_loan);
                                       handler.update_customer_mobile(updated_mobile, final_loan);
                                       Toast.makeText(Customer_List_Activity.this, "Mobile Number Updated Successfully", Toast.LENGTH_LONG).show();
                                       Intent in = new Intent(getApplicationContext(), Customer_List_Activity.class);
                                       startActivity(in);
                                       dialog.dismiss();
                                   }
                                   else {
                                       handler.update_customer_mobile(updated_mobile, final_loan);
                                       Toast.makeText(Customer_List_Activity.this, "Mobile Number Updated Successfully", Toast.LENGTH_LONG).show();
                                       Intent in = new Intent(getApplicationContext(), Customer_List_Activity.class);
                                       startActivity(in);
                                       dialog.dismiss();
                                   }

                               }

                           }
                       });
                       Button dialogButton1= (Button) dialog.findViewById(R.id.btn_dialog_cancel);
                       dialogButton1.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               dialog.dismiss();
                           }
                       });
                       dialog.show();
                   }
               });
            }
        }



    private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
            ((TextView) view.findViewById(R.id.sub_title)).setText(subTitle);

        ((TextView) item.findViewById(R.id.title)).getText().toString();
        }

    private void AlertBox(final String name, final String mobile, final String loan_id, final String loan_amount, final String Loan_type, final String total_payable_amount, final String balance_amount) {
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Lato-Light.ttf");


            final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alertdialog_layout);

        TextView text = (TextView) dialog.findViewById(R.id.text_cname);
        text.setText("Customer Name: "+name);
       // text.setTypeface(custom_font);
        //text_cloanid
        TextView textloanid = (TextView) dialog.findViewById(R.id.text_cloanid);
        textloanid.setText("Loan Number: "+loan_id);
        //textloanid.setTypeface(custom_font);

        TextView textloan_amount=(TextView) dialog.findViewById(R.id.text_loan_amount);
        textloan_amount .setText("Loan Amount: "+loan_amount);
       // textloan_amount.setTypeface(custom_font);  text_bal_amount
        TextView textloan_balance_amount=(TextView) dialog.findViewById(R.id.text_bal_amount);
        textloan_balance_amount= (TextView) dialog.findViewById(R.id.text_bal_amount);
        textloan_balance_amount.setText("Balance Amount: "+balance_amount);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(Color.TRANSPARENT); // make the background transparent
        gd.setStroke(4, Color.rgb(48, 63, 159)); // border width and color
        gd.setCornerRadius(15.0f); // border corner radius
        text.setBackground(gd);
        textloanid.setBackground(gd);
        textloan_amount.setBackground(gd);
        textloan_balance_amount.setBackground(gd);
       final EditText pay_amount=(EditText)dialog.findViewById(R.id.text_pay_amount);


        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pay=pay_amount.getText().toString();
                if(pay.matches("")){
                    pay_amount.setError("Enter valid amount");
                    Toast.makeText(getApplicationContext(),"Enter paying amount",Toast.LENGTH_LONG).show();
                }
                else {
                    /*int paying_amount = Integer.parseInt(pay);
                    int loan_main_balance = Integer.parseInt(loan_amount);
                    int loan_total_amount = Integer.parseInt(total_payable_amount);
                    int balance_amount = loan_total_amount - paying_amount;
                    final String balance = String.valueOf(balance_amount);*/

                    float paying_amount = Float.parseFloat(pay);
                    float loan_main_balance = Float.parseFloat(loan_amount);
                    float loan_total_balance_amount = Float.parseFloat(balance_amount);
                    float balance_amount = loan_total_balance_amount - paying_amount;
                    final String balance = String.valueOf(balance_amount);
                    //String Customer_Name,String Loan_Amount,String Loan_Type,String Total_Amount,String Paid_Amount,
                    // String Paid_Date,String Total_Paid_Amount,String Balance_Amount
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    handler = new DataHandler(getApplicationContext());
                    handler.insert_loan_collection(name, mobile, loan_id, loan_amount, Loan_type, total_payable_amount, pay, date, balance);
                    handler.update(loan_id, balance);
                    SendSms(name, mobile, loan_id, loan_amount, Loan_type, total_payable_amount, pay, date, balance);
                    Toast.makeText(getApplicationContext(), "Payment updated successfully.", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "Balance amount:" + balance, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
        //btn_dialog_cancel
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.btn_dialog_cancel);
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void SendSms(String name, String mobile, String loan_id, String loan_amount, String loan_type, String total_payable_amount, String pay, String date, String balance) {
       /* String msg="Dear "+ name +" Thank you for your payment Rs "+pay+ " Paid on "+ date + " towards Your loan id "+ loan_id+ "\n"
                + "and Balance amount " + balance;
        //Getting intent and PendingIntent instance
        Intent intent=new Intent();
        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

        //Get the SmsManager instance and call the sendTextMessage method to send message
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(mobile, null, msg, pi,null);

        Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                Toast.LENGTH_LONG).show()*/;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.sucess_message_alert);
        TextView text = (TextView) dialog.findViewById(R.id.msg_text);
        text.setText("Payment Successfully Completed");
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Customer_List_Activity.this,Customer_List_Activity.class));
                dialog.dismiss();

            }
        });
        dialog.show();
       /* Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                Toast.LENGTH_LONG).show();*/
    }


    private void showInsertDialog(final OnItemCreated positive) {
            final EditText text = new EditText(this);
            text.setHint("Enter the amount to pay");
            text.setHintTextColor(getResources().getColor(R.color.colorPrimary));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(text);
            builder.setTitle("Enter_Title");
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    positive.itemCreated(text.getText().toString());
                }
            });
            builder.setNegativeButton(android.R.string.cancel, null);
            builder.show();
        }
    }

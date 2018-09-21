package com.abile.microfinance;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.abile.microfinance.FinanceDatabaseHandler.DataHandler;
import com.codetroopers.betterpickers.expirationpicker.ExpirationPickerBuilder;
import com.codetroopers.betterpickers.expirationpicker.ExpirationPickerDialogFragment;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Activity_Reports_menu extends AppCompatActivity implements ExpirationPickerDialogFragment.ExpirationPickerDialogHandler{
    Button day,week,month,year;
    DataHandler handler;
    Cursor cursor;
     TextView from_date,to_date;
    private int mYear, mMonth, mDay;
    private int year1, month1, day1;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

     String s1,s2;
     String cday,cmonth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_menus);
        day = (Button) findViewById(R.id.day_report);
        week = (Button) findViewById(R.id.week_report);
        month = (Button) findViewById(R.id.month_report);
        year = (Button) findViewById(R.id.year_report);

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DayCollection_ExcelReport();

            }
        });
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Activity_Reports_menu.this,Reports_Inputs_Layout.class);
                in.putExtra("key","Week");
                startActivity(in);
            }
        });
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Activity_Reports_menu.this,Reports_Inputs_Layout.class);
                in.putExtra("key","Month");
                startActivity(in);
            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Activity_Reports_menu.this,Reports_Inputs_Layout.class);
                in.putExtra("key","Year");
                startActivity(in);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Activity_Main_Menu.class));
    }

      /*  week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Activity_Reports_menu.this );
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.week_report_input_layout);
                from_date = (TextView) dialog.findViewById(R.id.text_from_date);
                to_date = (TextView) dialog.findViewById(R.id.text_to_date);

                //from date click event
                from_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Reports_menu.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {


                                        if(dayOfMonth>=10){
                                            cday=String.valueOf(dayOfMonth);

                                        }
                                        else{
                                            cday="0"+dayOfMonth;
                                        }
                                        if(monthOfYear>=9){
                                            monthOfYear+=1;
                                            cmonth=String.valueOf(monthOfYear);
                                        }
                                        else{
                                            monthOfYear+=1;
                                            cmonth="0"+monthOfYear;
                                        }
                                        from_date.setText(cday + "-" + (cmonth) + "-" + year);
                                        s1=cday + "-" + (cmonth) + "-" + year;
                                    }
                                }, mYear, mMonth, mDay);


                        datePickerDialog.show();
                    }
                });
                to_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Reports_menu.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                       // to_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                        //s2="0"+dayOfMonth + "-" +"0"+ (monthOfYear + 1) + "-" + year;
                                        if(dayOfMonth>=10){
                                            cday=String.valueOf(dayOfMonth);

                                        }
                                        else{
                                            cday="0"+dayOfMonth;
                                        }
                                        if(monthOfYear>=9){
                                            monthOfYear+=1;
                                            cmonth=String.valueOf(monthOfYear);
                                        }
                                        else{
                                            monthOfYear+=1;
                                            cmonth="0"+monthOfYear;
                                        }
                                        to_date.setText(cday + "-" + (cmonth) + "-" + year);
                                        s2=cday + "-" + (cmonth) + "-" + year;

                                    }
                                }, mYear, mMonth, mDay);


                        datePickerDialog.show();
                    }
                });
                Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handler=new DataHandler(getApplicationContext());
                        cursor=handler.Week_Collection(s1,s2);
                        String folder_main = "MyFinance";
                        File sd = Environment.getExternalStorageDirectory();
                        String csvFile = "WeeklyCollectionReport_from " +s1+ "to "+s2+ ".xls";

                        File directory = new File(Environment.getExternalStorageDirectory(),folder_main);
                        //create directory if not exist
                        if (!directory.isDirectory()) {
                            directory.mkdirs();
                        }
                        if(cursor.getCount()>0) {
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

                                sheet.addCell(new Label(0, 0, "LoanID")); // column and row
                                sheet.addCell(new Label(1, 0, "Name"));
                                sheet.addCell(new Label(2, 0, "Mobile"));
                                sheet.addCell(new Label(3, 0, "Loan_Amount "));
                                sheet.addCell(new Label(4, 0, "Loan_Type"));
                                sheet.addCell(new Label(5, 0, "Total_Payable_Amount"));
                                sheet.addCell(new Label(6, 0, "Paid Amount"));
                                sheet.addCell(new Label(7, 0, "Paid Date"));
                                sheet.addCell(new Label(8, 0, "Balance Amount"));


                                if (cursor.moveToFirst()) {
                                    do {
                                        //  addItem(cursor.getString(0),new String[]{"Loan Id :"  +cursor.getString(3),"Amount :"  +cursor.getString(4),"Loan Type :"  +cursor.getString(5),"Loan_Interest :"  +cursor.getString(6),"Durations :"  +cursor.getString(7),
                                        //"Due Amount :"  +cursor.getString(8),"Total Amount :"  +cursor.getString(9)},
                                        //  R.color.orange,R.drawable.ic_ghost);
                                        String LoanId = cursor.getString(1);
                                        String title = cursor.getString(2);
                                        String mobile = cursor.getString(0);
                                        String Loan_Amount = cursor.getString(3);
                                        String Loan_Type = cursor.getString(4);
                                        String Total_Payable_Amount = cursor.getString(5);
                                        String Paid_Amount = cursor.getString(6);
                                        String PaidDate = cursor.getString(7);
                                        String Balance_Amount = cursor.getString(8);


                                        int i = cursor.getPosition() + 1;
                                        sheet.addCell(new Label(0, i, title));
                                        sheet.addCell(new Label(1, i, mobile));
                                        sheet.addCell(new Label(2, i, LoanId));
                                        sheet.addCell(new Label(3, i, Loan_Amount));
                                        sheet.addCell(new Label(4, i, Loan_Type));
                                        sheet.addCell(new Label(5, i, Total_Payable_Amount));
                                        sheet.addCell(new Label(6, i, Paid_Amount));
                                        sheet.addCell(new Label(7, i, PaidDate));
                                        sheet.addCell(new Label(8, i, Balance_Amount));


                                    } while (cursor.moveToNext());
                                }
                                //closing cursor
                                cursor.close();
                                workbook.write();
                                workbook.close();
                                // Toast.makeText(getApplication(), "Data Exported into this location Internalstorage/MyFinance  ", Toast.LENGTH_SHORT).show();
                                final Dialog dialog = new Dialog(Activity_Reports_menu.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.sucess_message_alert);
                                TextView text = (TextView) dialog.findViewById(R.id.msg_text);
                                text.setText("Data Exported into this location Internalstorage/MyFinance");
                                dialog.dismiss();
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
                        else{
                            final Dialog dialog = new Dialog(Activity_Reports_menu.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.sucess_message_alert);
                            TextView text = (TextView) dialog.findViewById(R.id.msg_text);
                            text.setText("OOPS!No datas for entered dates!");
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
                });
                dialog.dismiss();
                Button cancelButton = (Button) dialog.findViewById(R.id.btn_dialog_cancel);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpirationPickerBuilder epb = new ExpirationPickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment)
                        .setMinYear(2000);
                epb.show();
            }
        });


        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c;
                c = Calendar.getInstance(); // reset
                String today = dateFormat.format(c.getTime());

                Calendar cal = Calendar.getInstance();
                Date today1 = cal.getTime();
                cal.add(Calendar.YEAR, -1); // to get previous year add -1
                String previousyear=dateFormat.format(cal.getTime());
                handler=new DataHandler(getApplicationContext());
                cursor=handler.Yearly_Collection(previousyear,today);
                String folder_main = "MyFinance";
                File sd = Environment.getExternalStorageDirectory();
                String csvFile = "YearlyCollectionReport_from  "+ previousyear+" to " +today+ ".xls";

                File directory = new File(Environment.getExternalStorageDirectory(),folder_main);
                //create directory if not exist
                if (!directory.isDirectory()) {
                    directory.mkdirs();
                }
                if(cursor.getCount()>0) {
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

                        sheet.addCell(new Label(0, 0, "LoanID")); // column and row
                        sheet.addCell(new Label(1, 0, "Name"));
                        sheet.addCell(new Label(2, 0, "Mobile"));
                        sheet.addCell(new Label(3, 0, "Loan_Amount "));
                        sheet.addCell(new Label(4, 0, "Loan_Type"));
                        sheet.addCell(new Label(5, 0, "Total_Payable_Amount"));
                        sheet.addCell(new Label(6, 0, "Paid Amount"));
                        sheet.addCell(new Label(7, 0, "Paid Date"));
                        sheet.addCell(new Label(8, 0, "Balance Amount"));


                        if (cursor.moveToFirst()) {
                            do {
                                //  addItem(cursor.getString(0),new String[]{"Loan Id :"  +cursor.getString(3),"Amount :"  +cursor.getString(4),"Loan Type :"  +cursor.getString(5),"Loan_Interest :"  +cursor.getString(6),"Durations :"  +cursor.getString(7),
                                //"Due Amount :"  +cursor.getString(8),"Total Amount :"  +cursor.getString(9)},
                                //  R.color.orange,R.drawable.ic_ghost);
                                String LoanId = cursor.getString(1);
                                String title = cursor.getString(2);
                                String mobile = cursor.getString(0);
                                String Loan_Amount = cursor.getString(3);
                                String Loan_Type = cursor.getString(4);
                                String Total_Payable_Amount = cursor.getString(5);
                                String Paid_Amount = cursor.getString(6);
                                String PaidDate = cursor.getString(7);
                                String Balance_Amount = cursor.getString(8);


                                int i = cursor.getPosition() + 1;
                                sheet.addCell(new Label(0, i, title));
                                sheet.addCell(new Label(1, i, mobile));
                                sheet.addCell(new Label(2, i, LoanId));
                                sheet.addCell(new Label(3, i, Loan_Amount));
                                sheet.addCell(new Label(4, i, Loan_Type));
                                sheet.addCell(new Label(5, i, Total_Payable_Amount));
                                sheet.addCell(new Label(6, i, Paid_Amount));
                                sheet.addCell(new Label(7, i, PaidDate));
                                sheet.addCell(new Label(8, i, Balance_Amount));


                            } while (cursor.moveToNext());
                        }
                        //closing cursor
                        cursor.close();
                        workbook.write();
                        workbook.close();
                        // Toast.makeText(getApplication(), "Data Exported into this location Internalstorage/MyFinance  ", Toast.LENGTH_SHORT).show();
                        final Dialog dialog = new Dialog(Activity_Reports_menu.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.sucess_message_alert);
                        TextView text = (TextView) dialog.findViewById(R.id.msg_text);
                        text.setText("Data Exported into this location Internalstorage/MyFinance");
                        dialog.dismiss();
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
                else{
                    final Dialog dialog = new Dialog(Activity_Reports_menu.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.sucess_message_alert);
                    TextView text = (TextView) dialog.findViewById(R.id.msg_text);
                    text.setText("OOPS!No datas for entered dates!please check your input datas");
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
        });
    }*/

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options item to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        if(menu instanceof MenuBuilder){

            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);//.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_export_collection:
                ExportExcel();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ExportExcel() {
        handler=new DataHandler(getApplicationContext());
        cursor=handler.Collection_datas();
        String folder_main = "MyFinance";
        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "CollectionReportFull.xls";

        File directory = new File(Environment.getExternalStorageDirectory(),folder_main);
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        if(cursor.getCount()>0) {
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

                sheet.addCell(new Label(0, 0, "LoanID")); // column and row
                sheet.addCell(new Label(1, 0, "Name"));
                sheet.addCell(new Label(2, 0, "Mobile"));
                sheet.addCell(new Label(3, 0, "Loan_Amount "));
                sheet.addCell(new Label(4, 0, "Loan_Type"));
                sheet.addCell(new Label(5, 0, "Total_Payable_Amount"));
                sheet.addCell(new Label(6, 0, "Paid Amount"));
                sheet.addCell(new Label(7, 0, "Paid Date"));
                sheet.addCell(new Label(8, 0, "Balance Amount"));


                if (cursor.moveToFirst()) {
                    do {
                        //  addItem(cursor.getString(0),new String[]{"Loan Id :"  +cursor.getString(3),"Amount :"  +cursor.getString(4),"Loan Type :"  +cursor.getString(5),"Loan_Interest :"  +cursor.getString(6),"Durations :"  +cursor.getString(7),
                        //"Due Amount :"  +cursor.getString(8),"Total Amount :"  +cursor.getString(9)},
                        //  R.color.orange,R.drawable.ic_ghost);
                        String LoanId = cursor.getString(0);
                        String title = cursor.getString(1);
                        String mobile = cursor.getString(2);
                        String Loan_Amount = cursor.getString(3);
                        String Loan_Type = cursor.getString(4);
                        String Total_Payable_Amount = cursor.getString(5);
                        String Paid_Amount = cursor.getString(6);
                        String PaidDate = cursor.getString(7);
                        String Balance_Amount = cursor.getString(8);


                        int i = cursor.getPosition() + 1;
                        sheet.addCell(new Label(0, i, LoanId));
                        sheet.addCell(new Label(1, i, title));
                        sheet.addCell(new Label(2, i, mobile));
                        sheet.addCell(new Label(3, i, Loan_Amount));
                        sheet.addCell(new Label(4, i, Loan_Type));
                        sheet.addCell(new Label(5, i, Total_Payable_Amount));
                        sheet.addCell(new Label(6, i, Paid_Amount));
                        sheet.addCell(new Label(7, i, PaidDate));
                        sheet.addCell(new Label(8, i, Balance_Amount));




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
        else{
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.sucess_message_alert);
            TextView text = (TextView) dialog.findViewById(R.id.msg_text);
            text.setText("OOPS!Collections are not yet made today!");
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
    public void onDialogExpirationSet(int reference, int year, int monthOfYear) {
        //Toast.MakeText(getString(R.string.expiration_picker_result_value, String.format("%02d", monthOfYear), year));
        String month_start_date="";
        String month_end_date="";
        String month=String.valueOf(monthOfYear);
        String year1=String.valueOf(year);
        int mo=Integer.parseInt(month);

        if(mo>=10){
            month_start_date="01"+"-"+month +"-"+year1;
            month_end_date="31"+"-"+month +"-"+year1;
        }
        else{
            month_start_date="01"+"-"+"0"+month +"-"+year1;
            month_end_date="31"+"-"+"0"+month +"-"+year1;
        }

       // String selected_month="01"+"-"+"0"+month +"-"+year1;
       // String month_end_date="31"+"-"+"0"+month +"-"+year1;
        handler=new DataHandler(getApplicationContext());
        cursor=handler.Monthly_Collection(month_start_date,month_end_date);
        String folder_main = "MyFinance";
        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "MonthCollectionReport_from "+ month_start_date+ " to_ " +month_end_date+" .xls";

        File directory = new File(Environment.getExternalStorageDirectory(),folder_main);
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        if(cursor.getCount()>0) {
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

                sheet.addCell(new Label(0, 0, "LoanID")); // column and row
                sheet.addCell(new Label(1, 0, "Name"));
                sheet.addCell(new Label(2, 0, "Mobile"));
                sheet.addCell(new Label(3, 0, "Loan_Amount "));
                sheet.addCell(new Label(4, 0, "Loan_Type"));
                sheet.addCell(new Label(5, 0, "Total_Payable_Amount"));
                sheet.addCell(new Label(6, 0, "Paid Amount"));
                sheet.addCell(new Label(7, 0, "Paid Date"));
                sheet.addCell(new Label(8, 0, "Balance Amount"));


                sheet.addCell(new Label(0, 0, "LoanID")); // column and row
                sheet.addCell(new Label(1, 0, "Name"));
                sheet.addCell(new Label(2, 0, "Mobile"));
                sheet.addCell(new Label(3, 0, "Loan_Amount "));
                sheet.addCell(new Label(4, 0, "Loan_Type"));
                sheet.addCell(new Label(5, 0, "Total_Payable_Amount"));
                sheet.addCell(new Label(6, 0, "Paid Amount"));
                sheet.addCell(new Label(7, 0, "Paid Date"));
                sheet.addCell(new Label(8, 0, "Balance Amount"));


                if (cursor.moveToFirst()) {
                    do {
                        //  addItem(cursor.getString(0),new String[]{"Loan Id :"  +cursor.getString(3),"Amount :"  +cursor.getString(4),"Loan Type :"  +cursor.getString(5),"Loan_Interest :"  +cursor.getString(6),"Durations :"  +cursor.getString(7),
                        //"Due Amount :"  +cursor.getString(8),"Total Amount :"  +cursor.getString(9)},
                        //  R.color.orange,R.drawable.ic_ghost);
                        String LoanId = cursor.getString(1);
                        String title = cursor.getString(2);
                        String mobile = cursor.getString(0);
                        String Loan_Amount = cursor.getString(3);
                        String Loan_Type = cursor.getString(4);
                        String Total_Payable_Amount = cursor.getString(5);
                        String Paid_Amount = cursor.getString(6);
                        String PaidDate = cursor.getString(7);
                        String Balance_Amount = cursor.getString(8);


                        int i = cursor.getPosition() + 1;
                        sheet.addCell(new Label(0, i, title));
                        sheet.addCell(new Label(1, i, mobile));
                        sheet.addCell(new Label(2, i, LoanId));
                        sheet.addCell(new Label(3, i, Loan_Amount));
                        sheet.addCell(new Label(4, i, Loan_Type));
                        sheet.addCell(new Label(5, i, Total_Payable_Amount));
                        sheet.addCell(new Label(6, i, Paid_Amount));
                        sheet.addCell(new Label(7, i, PaidDate));
                        sheet.addCell(new Label(8, i, Balance_Amount));


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
        else{
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.sucess_message_alert);
            TextView text = (TextView) dialog.findViewById(R.id.msg_text);
            text.setText("OOPS!No Collection report for selected month! please check your input datas");
            Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });
            dialog.show();
        }
       // Toast.makeText(Activity_Reports_menu.this,month_start_date,Toast.LENGTH_LONG).show();
    }




    //Daily collection report function
    public void  DayCollection_ExcelReport(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        String current_date=dateFormat.format(date);

        handler=new DataHandler(getApplicationContext());
        cursor=handler.Day_Collection(current_date);
        String folder_main = "MyFinance";
        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "DailyCollectionReport_ " +current_date+ ".xls";

        File directory = new File(Environment.getExternalStorageDirectory(),folder_main);
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        if(cursor.getCount()>0) {
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

                sheet.addCell(new Label(0, 0, "LoanID")); // column and row
                sheet.addCell(new Label(1, 0, "Name"));
                sheet.addCell(new Label(2, 0, "Mobile"));
                sheet.addCell(new Label(3, 0, "Loan_Amount "));
                sheet.addCell(new Label(4, 0, "Loan_Type"));
                sheet.addCell(new Label(5, 0, "Total_Payable_Amount"));
                sheet.addCell(new Label(6, 0, "Paid Amount"));
                sheet.addCell(new Label(7, 0, "Paid Date"));
                sheet.addCell(new Label(8, 0, "Balance Amount"));


                if (cursor.moveToFirst()) {
                    do {
                        //  addItem(cursor.getString(0),new String[]{"Loan Id :"  +cursor.getString(3),"Amount :"  +cursor.getString(4),"Loan Type :"  +cursor.getString(5),"Loan_Interest :"  +cursor.getString(6),"Durations :"  +cursor.getString(7),
                        //"Due Amount :"  +cursor.getString(8),"Total Amount :"  +cursor.getString(9)},
                        //  R.color.orange,R.drawable.ic_ghost);
                        String LoanId = cursor.getString(1);
                        String title = cursor.getString(2);
                        String mobile = cursor.getString(0);
                        String Loan_Amount = cursor.getString(3);
                        String Loan_Type = cursor.getString(4);
                        String Total_Payable_Amount = cursor.getString(5);
                        String Paid_Amount = cursor.getString(6);
                        String PaidDate = cursor.getString(7);
                        String Balance_Amount = cursor.getString(8);


                        int i = cursor.getPosition() + 1;
                        sheet.addCell(new Label(0, i, title));
                        sheet.addCell(new Label(1, i, mobile));
                        sheet.addCell(new Label(2, i, LoanId));
                        sheet.addCell(new Label(3, i, Loan_Amount));
                        sheet.addCell(new Label(4, i, Loan_Type));
                        sheet.addCell(new Label(5, i, Total_Payable_Amount));
                        sheet.addCell(new Label(6, i, Paid_Amount));
                        sheet.addCell(new Label(7, i, PaidDate));
                        sheet.addCell(new Label(8, i, Balance_Amount));


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
        else{
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.sucess_message_alert);
            TextView text = (TextView) dialog.findViewById(R.id.msg_text);
            text.setText("OOPS!Collections are not yet made today!");
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



}

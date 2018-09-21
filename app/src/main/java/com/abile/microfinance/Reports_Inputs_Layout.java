    package com.abile.microfinance;

    import android.app.Activity;
    import android.app.Dialog;
    import android.content.Intent;
    import android.database.Cursor;
    import android.graphics.Color;
    import android.graphics.drawable.GradientDrawable;
    import android.os.Bundle;
    import android.os.Environment;
    import android.support.annotation.Nullable;
    import android.support.design.widget.Snackbar;
    import android.view.View;
    import android.view.Window;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;


    import com.abile.microfinance.FinanceDatabaseHandler.DataHandler;
    import com.tsongkha.spinnerdatepicker.DatePicker;
    import com.tsongkha.spinnerdatepicker.DatePickerDialog;
    import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

    import java.io.File;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.GregorianCalendar;
    import java.util.Locale;

    import jxl.Workbook;
    import jxl.WorkbookSettings;
    import jxl.write.Label;
    import jxl.write.WritableSheet;
    import jxl.write.WritableWorkbook;

    public class Reports_Inputs_Layout extends Activity  implements DatePickerDialog.OnDateSetListener  {

        TextView start_date,end_date;
        Button export,cancel;
        SimpleDateFormat simpleDateFormat;
        int i=0;
        String key="",From_Date="",To_Date="";

        DataHandler handler;
        Cursor cursor;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.reports_inputs_layout);
            key=getIntent().getStringExtra("key");
            start_date= (TextView) findViewById(R.id.start_date);
            end_date= (TextView) findViewById(R.id.end_date);
            export= (Button) findViewById(R.id.btn_export);
            cancel= (Button) findViewById(R.id.btn_dialog_cancel);

            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setColor(Color.TRANSPARENT); // make the background transparent
            gd.setStroke(4, Color.rgb(255, 48, 142)); // border width and color
            gd.setCornerRadius(15.0f); // border corner radius
            start_date.setBackground(gd);
            end_date.setBackground(gd);

            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            start_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i=1;
                    showDate(2000, 0, 1, R.style.DatePickerSpinner);
                }
            });
            end_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i=2;
                    showDate(2000, 0, 1, R.style.DatePickerSpinner);
                }
            });

            export.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    From_Date=start_date.getText().toString();
                    To_Date=end_date.getText().toString();
                    if(key.equals("Week")){
                        ExportDataByWeek(From_Date,To_Date);
                    }
                    else if(key.equals("Month")){
                       ExportDataByMonth(From_Date,To_Date);
                    }
                    else if(key.equals("Year")){
                       ExportDataByYear(From_Date,To_Date);
                    }
                    else{
                        Snackbar.make(view,"Something went wrong!",Snackbar.LENGTH_LONG).show();
                    }
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),Activity_Reports_menu.class));
                }
            });

        }

        //Yearly Report generation Function
        private void ExportDataByYear(String from_date, String to_date) {
            handler=new DataHandler(getApplicationContext());
            cursor=handler.Week_Collection(from_date,to_date);
            String folder_main = "MyFinance";
            File sd = Environment.getExternalStorageDirectory();
            String csvFile = "Yearly_CollectionReport_from " +from_date+ "to "+to_date+ ".xls";

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
                    final Dialog dialog = new Dialog(Reports_Inputs_Layout.this);
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
                final Dialog dialog = new Dialog(Reports_Inputs_Layout.this);
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

        //Monthly Report Generation Function
        private void ExportDataByMonth(String from_date, String to_date) {
            handler=new DataHandler(getApplicationContext());
            cursor=handler.Week_Collection(from_date,to_date);
            String folder_main = "MyFinance";
            File sd = Environment.getExternalStorageDirectory();
            String csvFile = "Monthly_CollectionReport_from " +from_date+ "to "+to_date+ ".xls";

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
                    final Dialog dialog = new Dialog(Reports_Inputs_Layout.this);
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
                final Dialog dialog = new Dialog(Reports_Inputs_Layout.this);
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

        //Weekly Report Generation Function
        private void ExportDataByWeek(String from_date, String to_date) {
            handler=new DataHandler(getApplicationContext());
            cursor=handler.Week_Collection(from_date,to_date);
            String folder_main = "MyFinance";
            File sd = Environment.getExternalStorageDirectory();
            String csvFile = "WeeklyCollectionReport_from " +from_date+ "to "+to_date+ ".xls";

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
                    final Dialog dialog = new Dialog(Reports_Inputs_Layout.this);
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
                final Dialog dialog = new Dialog(Reports_Inputs_Layout.this);
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


        @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        if (i == 1) {
            start_date.setText(simpleDateFormat.format(calendar.getTime()));
        } else {
            end_date.setText(simpleDateFormat.format(calendar.getTime()));
        }
    }

        void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
            new SpinnerDatePickerDialogBuilder()
                    .context(Reports_Inputs_Layout.this)
                    .callback(Reports_Inputs_Layout.this)
                    .spinnerTheme(spinnerTheme)
                    .defaultDate(year, monthOfYear, dayOfMonth)
                    .build()
                    .show();
        }
    }

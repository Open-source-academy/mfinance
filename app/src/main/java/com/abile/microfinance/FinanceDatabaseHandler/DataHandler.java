package com.abile.microfinance.FinanceDatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class DataHandler {

    public DataHelper helper;
    Context context;
    public DataHandler(Context context)
    {
        this.context=context;
        helper=new DataHelper(context);
    }


    //Check phnumber
    public void Checkphnumber(){
        SQLiteDatabase db=helper.getWritableDatabase();
    }




    //Customer_basic_info table CRUD operations
    public void insert(String Name,String Phone,String Address,String id,String lamount,String ltype,String lint,String ltime,String
                       ldueamount,String totalamount,String balanceamount){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DataHelper.COL_CUSTOMER_NAME,Name);
        cv.put(DataHelper.COL_CUSTOMER_MOBILE,Phone);
        cv.put(DataHelper.COL_CUSTOMER_ADDRESS,Address);
        cv.put(DataHelper.COL_LOAN_ID,id);
        cv.put(DataHelper.COL_LOAN_AMOUNT,lamount);
        cv.put(DataHelper.COL_LOAN_TYPE,ltype);
        cv.put(DataHelper.COL_LOAN_INTEREST,lint);
        cv.put(DataHelper.COL_LOAN_TIME_PERIOD,ltime);
        cv.put(DataHelper.COL_LOAN_DUE_AMOUNT,ldueamount);
        cv.put(DataHelper.COL_LOAN_FINAL_AMOUNT,totalamount);
        cv.put(DataHelper.COL_CUS_LOAN_BALANCE_AMOUNT,balanceamount);
        cv.put(DataHelper.COL_LOAN_ISSUED_DATE,getDateTime());
        db.insert(DataHelper.CUSTOMER_LOAN_ENTRY_TABLE,null,cv);
       // Toast.makeText(context,"Data inserted successfully",Toast.LENGTH_LONG).show();
    }


    public void insertdate(String Name,String Phone,String Address,String id,String lamount,String ltype,String lint,String ltime,String
            ldueamount,String totalamount,String balanceamount,String date){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DataHelper.COL_CUSTOMER_NAME,Name);
        cv.put(DataHelper.COL_CUSTOMER_MOBILE,Phone);
        cv.put(DataHelper.COL_CUSTOMER_ADDRESS,Address);
        cv.put(DataHelper.COL_LOAN_ID,id);
        cv.put(DataHelper.COL_LOAN_AMOUNT,lamount);
        cv.put(DataHelper.COL_LOAN_TYPE,ltype);
        cv.put(DataHelper.COL_LOAN_INTEREST,lint);
        cv.put(DataHelper.COL_LOAN_TIME_PERIOD,ltime);
        cv.put(DataHelper.COL_LOAN_DUE_AMOUNT,ldueamount);
        cv.put(DataHelper.COL_LOAN_FINAL_AMOUNT,totalamount);
        cv.put(DataHelper.COL_CUS_LOAN_BALANCE_AMOUNT,balanceamount);
        cv.put(DataHelper.COL_LOAN_ISSUED_DATE,date);
        db.insert(DataHelper.CUSTOMER_LOAN_ENTRY_TABLE,null,cv);
        // Toast.makeText(context,"Data inserted successfully",Toast.LENGTH_LONG).show();
    }


    //getting current date
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    public void update(String loan_id,String NewBalance){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DataHelper.COL_CUS_LOAN_BALANCE_AMOUNT, NewBalance);
        db.update(DataHelper.CUSTOMER_LOAN_ENTRY_TABLE, cv, DataHelper.COL_LOAN_ID+"='"+loan_id+"'", null);
        //Toast.makeText(context, "Amount  Updated Successfully", Toast.LENGTH_SHORT).show();
    }

    public Cursor getAllCustomers(){

             SQLiteDatabase db=helper.getWritableDatabase();
        String[] columns={DataHelper.COL_CUSTOMER_NAME,DataHelper.COL_CUSTOMER_MOBILE,DataHelper.COL_CUSTOMER_ADDRESS,DataHelper.COL_LOAN_ID,
                DataHelper.COL_LOAN_AMOUNT,DataHelper.COL_LOAN_TYPE,DataHelper.COL_LOAN_INTEREST,DataHelper.COL_LOAN_TIME_PERIOD,DataHelper.COL_LOAN_DUE_AMOUNT,
                DataHelper.COL_LOAN_FINAL_AMOUNT,DataHelper.COL_LOAN_BALANCE_AMOUNT,DataHelper.COL_LOAN_ISSUED_DATE};
        return db.query(DataHelper.CUSTOMER_LOAN_ENTRY_TABLE,columns,null,null,null,null,null);
    }


    public Cursor getDataByNames(String pos) {
     SQLiteDatabase db = helper.getWritableDatabase();
        String FROM[] = {DataHelper.COL_CUSTOMER_NAME,DataHelper.COL_CUSTOMER_MOBILE,DataHelper.COL_CUSTOMER_ADDRESS,DataHelper.COL_LOAN_ID,
                DataHelper.COL_LOAN_AMOUNT,DataHelper.COL_LOAN_TYPE,DataHelper.COL_LOAN_INTEREST,DataHelper.COL_LOAN_TIME_PERIOD,DataHelper.COL_LOAN_DUE_AMOUNT,DataHelper.COL_LOAN_FINAL_AMOUNT,
                DataHelper.COL_LOAN_ISSUED_DATE};
        String where = DataHelper.COL_CUSTOMER_NAME;
        String[] whereArgs = {pos};
        String query = "SELECT * from  "+DataHelper.CUSTOMER_LOAN_ENTRY_TABLE+" WHERE "+DataHelper.COL_CUSTOMER_NAME+" LIKE'%"+pos+"%'";
        Log.d("MyQuery", query);
        Cursor c=db.rawQuery(query, null);
        return c;
    }

    public Cursor getDataByNames_loanid(String name,String loan_id) {


        SQLiteDatabase db = helper.getWritableDatabase();

        String query = "SELECT * from  "+DataHelper.CUSTOMER_LOAN_ENTRY_TABLE+" WHERE "+DataHelper.COL_CUSTOMER_NAME+" = '"+name+"' AND "+DataHelper.COL_LOAN_ID+" = '"+loan_id+"'";
        Log.d("MyQuery", query);
        Cursor c=db.rawQuery(query, null);
        return c;


        //return db.query(DataHelper.TABLE_LEAD, FROM, where, whereArgs, null, null, null);
    }




    public Cursor getDataByMobile(String pos) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String FROM[] = {DataHelper.COL_CUS_Name,DataHelper.COL_CUS_MOBILE,DataHelper.COL_CUS_LOAN_ID,
                DataHelper.COL_CUS_LOAN_AMOUNT,DataHelper.COL_CUS_LOAN_TYPE,DataHelper.COL_CUS_LOAN_TOTAL_AMOUNT,
        DataHelper.COL_CUS_LOAN_PAID_AMOUNT,DataHelper.COL_CUS_PAID_DATE,DataHelper.COL_CUS_LOAN_BALANCE_AMOUNT};
        String where = DataHelper.COL_CUS_MOBILE;
        String[] whereArgs = {pos};
        String query = "SELECT * from  "+DataHelper.CUSTOMER_LOAN_COLLECTION_TABLE+" WHERE "+DataHelper.COL_CUS_MOBILE+" LIKE'%"+pos+"%'";
        Log.d("MyQuery", query);
        Cursor c=db.rawQuery(query, null);
        return c;



        //return db.query(DataHelper.TABLE_LEAD, FROM, where, whereArgs, null, null, null);
    }


    public Cursor CheckMobile(String mobile){
        SQLiteDatabase db = helper.getWritableDatabase();
        String query = "SELECT * from  "+DataHelper.CUSTOMER_LOAN_ENTRY_TABLE+" WHERE "+DataHelper.COL_CUSTOMER_MOBILE+" LIKE'%"+mobile+"%'";
        Log.d("MyQuery", query);
        Cursor c=db.rawQuery(query, null);
        return c;
    }

    public Cursor getDataByDatas(String ph,String loanid) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String query = "SELECT * from  "+DataHelper.CUSTOMER_LOAN_COLLECTION_TABLE+" WHERE "+DataHelper.COL_CUS_MOBILE+" LIKE'%"+ph+"%' and "+DataHelper.COL_CUS_LOAN_ID+" LIKE'%"+loanid+"%'";
        Log.d("MyQuery", query);
        Cursor c=db.rawQuery(query, null);
        return c;
        //return db.query(DataHelper.TABLE_LEAD, FROM, where, whereArgs, null, null, null);
    }


    /**
     * Getting all labels
     * returns list of labels
     * */
    public List<String> getAllLabels(String pos){
        List<String> labels = new ArrayList<String>();



        SQLiteDatabase db = helper.getWritableDatabase();

        String query = "SELECT * from  "+DataHelper.CUSTOMER_LOAN_ENTRY_TABLE+" WHERE "+DataHelper.COL_CUSTOMER_MOBILE+" LIKE'%"+pos+"%'";
        Log.d("MyQuery", query);
        Cursor cursor=db.rawQuery(query, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(3));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    public Cursor get_collection_data(String pos){
        SQLiteDatabase db = helper.getWritableDatabase();

        String query = "SELECT * from  "+DataHelper.CUSTOMER_LOAN_ENTRY_TABLE+" WHERE "+DataHelper.COL_CUSTOMER_MOBILE+" LIKE'%"+pos+"%'";
        Log.d("MyQuery", query);
        Cursor cursor=db.rawQuery(query, null);
        return  cursor;
    }


public void delete_single_customer_by_loanid(String loan_id) {
    SQLiteDatabase db = helper.getWritableDatabase();
    db.delete(DataHelper.CUSTOMER_LOAN_ENTRY_TABLE,DataHelper.COL_LOAN_ID+"='"+loan_id+"'",null);
    db.delete(DataHelper.CUSTOMER_LOAN_COLLECTION_TABLE,DataHelper.COL_LOAN_ID+"='"+loan_id+"'",null);
    getAllCustomers();
    //Toast.makeText()
}

public void delete_all_loan_records(){
    SQLiteDatabase db = helper.getWritableDatabase();
    db.delete(DataHelper.CUSTOMER_LOAN_ENTRY_TABLE,null,null);

}


public void update_customer_mobile(String mobile,String loan_id){

    SQLiteDatabase db=helper.getWritableDatabase();
    ContentValues cv=new ContentValues();
    cv.put(DataHelper.COL_CUSTOMER_MOBILE, mobile);
    db.update(DataHelper.CUSTOMER_LOAN_ENTRY_TABLE, cv, DataHelper.COL_LOAN_ID+"='"+loan_id+"'", null);




}


public void update_collection_customer_mobile(String mobile,String loan_id){

        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DataHelper.COL_CUS_MOBILE, mobile);
        db.update(DataHelper.CUSTOMER_LOAN_COLLECTION_TABLE, cv, DataHelper.COL_CUS_LOAN_ID+"='"+loan_id+"'", null);
    }

    public Cursor getAllCustomersCollections(){

        SQLiteDatabase db=helper.getWritableDatabase();
        String[] columns={DataHelper.COL_CUS_Name,DataHelper.COL_CUS_MOBILE, DataHelper.COL_CUS_LOAN_AMOUNT,
                DataHelper.COL_CUS_LOAN_TYPE, DataHelper.COL_CUS_LOAN_TOTAL_AMOUNT,
                DataHelper.COL_CUS_LOAN_PAID_AMOUNT, DataHelper.COL_CUS_PAID_DATE,
                DataHelper.COL_CUS_LOAN_BALANCE_AMOUNT};
        return db.query(DataHelper.CUSTOMER_LOAN_COLLECTION_TABLE,columns,null,null,null,null,null);
    }


    public void insert_loan_collection(String Customer_Name,String Customer_mobile,String Loan_Id,String Loan_Amount,String Loan_Type,String Total_Amount,String Paid_Amount,String Paid_Date,String Balance_Amount){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DataHelper.COL_CUS_Name,Customer_Name);
        cv.put(DataHelper.COL_CUS_MOBILE,Customer_mobile);
        cv.put(DataHelper.COL_LOAN_ID,Loan_Id);
        cv.put(DataHelper.COL_CUS_LOAN_AMOUNT,Loan_Amount);
        cv.put(DataHelper.COL_CUS_LOAN_TYPE,Loan_Type);
        cv.put(DataHelper.COL_CUS_LOAN_TOTAL_AMOUNT,Total_Amount);
        cv.put(DataHelper.COL_CUS_LOAN_PAID_AMOUNT,Paid_Amount);
        cv.put(DataHelper.COL_CUS_PAID_DATE,getDateTime());
       // cv.put(DataHelper.COL_CUS_TOTAL_PAID_AMOUNT,Total_Paid_Amount);
        cv.put(DataHelper.COL_CUS_LOAN_BALANCE_AMOUNT,Balance_Amount);
        db.insert(DataHelper.CUSTOMER_LOAN_COLLECTION_TABLE,null,cv);
        //Toast.makeText(context,"Data inserted successfully",Toast.LENGTH_LONG).show();
    }


    //retrieve loan data
    /*
    *

    public Cursor getAllCustomersByKey(String ph_number){
        SQLiteDatabase db=helper.getReadableDatabase();
        String from[]={DataHelper.COL_LOAN_ID,DataHandler.DataHelper.COL_CUSTOMER_NAME,DataHandler.DataHelper.COL_LOAN_AMOUNT,DataHandler.DataHelper.COL_LOAN_TYPE,DataHandler.DataHelper.COL_LOAN_DUE_AMOUNT};
        return db.query(DataHelper.CUSTOMER_LOAN_ENTRY_TABLE,from,DataHelper.COL_CUSTOMER_MOBILE+"='"+ph_number+"'",null,null,null,null);
    }

     */
    public Cursor getAllCustomersByKey(String ph_number){
        SQLiteDatabase db=helper.getReadableDatabase();
        String from[]={DataHelper.COL_CUS_LOAN_ID, DataHelper.COL_CUS_Name, DataHelper.COL_CUS_LOAN_AMOUNT, DataHelper.COL_CUS_LOAN_TYPE, DataHelper.COL_CUS_LOAN_TOTAL_AMOUNT};
        return db.query(DataHelper.CUSTOMER_LOAN_COLLECTION_TABLE,from,DataHelper.COL_CUS_MOBILE+"='"+ph_number+"'",null,null,null,null);
    }



    public Cursor getAllCustomersByMobile(String ph_number){
        SQLiteDatabase db=helper.getReadableDatabase();
        String from[]={DataHelper.COL_CUS_Name, DataHelper.COL_LOAN_AMOUNT, DataHelper.COL_LOAN_TYPE, DataHelper.COL_CUS_LOAN_TOTAL_AMOUNT};
        return db.query(DataHelper.CUSTOMER_LOAN_COLLECTION_TABLE,from,DataHelper.COL_CUS_MOBILE+"='"+ph_number+"'",null,null,null,null);
    }


    //Registration table process
    public void insert(String username,String pin,String cpin){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DataHelper.COL_USERNAME,username);
        cv.put(DataHelper.COL_PIN,pin);
        cv.put(DataHelper.COL_CONFIRM_PIN,cpin);
        db.insert(DataHelper.USERS_TABLE,null,cv);
        Toast.makeText(context,"Account Created Successfully!",Toast.LENGTH_LONG).show();
    }

    public Cursor getDataByReg_User_Name(String pos) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String query = "SELECT * from  "+DataHelper.USERS_TABLE+" WHERE "+DataHelper.COL_USERNAME+" LIKE'%"+pos+"%'";
        Log.d("MyQuery", query);
        Cursor c=db.rawQuery(query, null);
        return c;
        //return db.query(DataHelper.TABLE_LEAD, FROM, where, whereArgs, null, null, null);
    }

    public Cursor checklogin(String uname,String pin){
        SQLiteDatabase db = helper.getWritableDatabase();

        String query = "SELECT * from  "+DataHelper.USERS_TABLE+" WHERE "+DataHelper.COL_USERNAME+" LIKE'%"+uname+"%' and "+DataHelper.COL_PIN+" LIKE'%"+pin+"%'";
        Log.d("MyQuery", query);
        Cursor c=db.rawQuery(query, null);
        return c;
    }


    //Collection report queries



    //full collection record
    public Cursor Collection_datas(){
        SQLiteDatabase db = helper.getWritableDatabase();

        String query = "SELECT * from  "+DataHelper.CUSTOMER_LOAN_COLLECTION_TABLE+"";
        Log.d("MyQuery", query);
        Cursor c=db.rawQuery(query, null);
        return c;
    }


    //Day collection query
    public Cursor Day_Collection(String current_date){
        SQLiteDatabase db=helper.getReadableDatabase();
        String from[]={DataHelper.COL_CUS_Name, DataHelper.COL_CUS_MOBILE,
                DataHelper.COL_CUS_LOAN_ID, DataHelper.COL_CUS_LOAN_AMOUNT,
                DataHelper.COL_CUS_LOAN_TYPE, DataHelper.COL_CUS_LOAN_TOTAL_AMOUNT,
                DataHelper.COL_CUS_LOAN_PAID_AMOUNT, DataHelper.COL_CUS_PAID_DATE,
                DataHelper.COL_CUS_LOAN_BALANCE_AMOUNT};
        return db.query(DataHelper.CUSTOMER_LOAN_COLLECTION_TABLE,from,DataHelper.COL_CUS_PAID_DATE+"='"+current_date+"'",null,null,null,null);
    }


    //Week collection query
    public Cursor Week_Collection(String from_date,String to_date){
        SQLiteDatabase db = helper.getWritableDatabase();

        String query = "SELECT * from  "+DataHelper.CUSTOMER_LOAN_COLLECTION_TABLE+" WHERE "+DataHelper.COL_CUS_PAID_DATE+" BETWEEN '"+from_date+"' AND '"+to_date+" '";
        Log.d("MyQuery", query);
        Cursor c=db.rawQuery(query, null);
        return c;
    }

    //monthly collection query
    public Cursor Monthly_Collection(String MonthoftheYear,String MonthendDate){
        SQLiteDatabase db = helper.getWritableDatabase();
        // String query = "SELECT * from  "+DataHelper.USERS_TABLE+" WHERE "+DataHelper.COL_USERNAME+" LIKE'%"+uname+"%' and "+DataHelper.COL_PIN+" LIKE'%"+pin+"%'";
        String query = "SELECT * from  "+DataHelper.CUSTOMER_LOAN_COLLECTION_TABLE+" WHERE "+DataHelper.COL_CUS_PAID_DATE+" BETWEEN '"+MonthoftheYear+"' AND '"+MonthendDate+" '";
        Log.d("MyQuery", query);
        Cursor c=db.rawQuery(query, null);
        return c;
    }

    //yearly collection query
    public Cursor Yearly_Collection(String previousyear,String today){
        SQLiteDatabase db = helper.getWritableDatabase();
        // String query = "SELECT * from  "+DataHelper.USERS_TABLE+" WHERE "+DataHelper.COL_USERNAME+" LIKE'%"+uname+"%' and "+DataHelper.COL_PIN+" LIKE'%"+pin+"%'";
        String query = "SELECT * from  "+DataHelper.CUSTOMER_LOAN_COLLECTION_TABLE+" WHERE "+DataHelper.COL_CUS_PAID_DATE+" BETWEEN '"+previousyear+"' AND '"+today+" '";
        Log.d("MyQuery", query);
        Cursor c=db.rawQuery(query, null);
        return c;
    }


    public class DataHelper extends SQLiteOpenHelper{

        public static final String DATABASE_NAME="FinaceData";
        static final int DATABASE_VERSION=1;

        //Customer Loan Basic Information Table Creation
        public static final String COL_CUSTOMER_NAME="Customer_Name";
        public static final String COL_CUSTOMER_MOBILE="Customer_Mobile_Number";
        public static final String COL_CUSTOMER_ADDRESS="Customer_Address";
        public static final String COL_LOAN_ID="_id";
	    public static final String COL_LOAN_ISSUED_DATE="Loan_Issue_Date";
        public static final String COL_LOAN_AMOUNT="Loan_Amount";
        public static final String COL_LOAN_TYPE="Loan_Type";
        public static final String COL_LOAN_INTEREST="Loan_Interest";
        public static final String COL_LOAN_TIME_PERIOD="loan_Duration";
        public static final String COL_LOAN_DUE_AMOUNT="Due_Amount";
        public static final String COL_LOAN_FINAL_AMOUNT="Total_Amount";
        public static final String COL_LOAN_BALANCE_AMOUNT="Balance_Amount";       
        public static final String CUSTOMER_LOAN_ENTRY_TABLE="Customers_Loan_Info";



        //Customer loan collection entry table creation
        public static final String COL_CUS_Name="Customer_Name";
        public static final String COL_CUS_MOBILE="Customer_Mobile_Number";
        public static final String COL_CUS_LOAN_ID="_id";
        public static final String COL_CUS_LOAN_AMOUNT="Loan_Amount";
        public static final String COL_CUS_LOAN_TYPE="Loan_Type";
        public static final String COL_CUS_LOAN_TOTAL_AMOUNT="Total_Amount";
        public static final String COL_CUS_LOAN_PAID_AMOUNT="Paid_Amount";
        public static final String COL_CUS_PAID_DATE="Paid_Date";
        public static final String COL_CUS_LOAN_BALANCE_AMOUNT="Balance_Amount";
        public static final String CUSTOMER_LOAN_COLLECTION_TABLE="Customers_Loan_Collection_Info";


        //Registration table creation
        public static final String COL_USERNAME="UserName";
        public static final String COL_PIN="Pin";
        public static final String COL_CONFIRM_PIN="Confirm_Pin";
        public static final String USERS_TABLE="Registration";

        public static final String CREATE_USERS_LOGIN_REGISTRATION_TABLE="create table "+USERS_TABLE+"("+COL_USERNAME+" varchar(50),"+COL_PIN+" varchar(50),"+COL_CONFIRM_PIN+" varchar(50))";
        public static final String DROP_USERS_LOGIN_REGISTRATION_TABLE="drop table if exists "+USERS_TABLE;




        public static final String CREATE_LOAN_COLLECTION_TABLE="create table "+CUSTOMER_LOAN_COLLECTION_TABLE+"("+COL_CUS_LOAN_ID+" varchar(10),"+COL_CUS_Name+" varchar(50)," +
                ""+COL_CUS_MOBILE+" varchar(50),"+COL_CUS_LOAN_AMOUNT+" varchar(50)," +
                ""+COL_CUS_LOAN_TYPE+" varchar(50),"+COL_CUS_LOAN_TOTAL_AMOUNT+" varchar(50),"+COL_CUS_LOAN_PAID_AMOUNT+" varchar(50),"+COL_CUS_PAID_DATE+" DATETIME DEFAULT CURRENT_TIMESTAMP,"+COL_CUS_LOAN_BALANCE_AMOUNT+" varchar(50))";

        public static final String DROP_LOAN_COLLECTION_TABLE="drop table if exists "+CUSTOMER_LOAN_COLLECTION_TABLE;


        public static final String CREATE_TABLE="create table "+CUSTOMER_LOAN_ENTRY_TABLE+" ("+COL_CUSTOMER_NAME+" varchar(50),"+COL_CUSTOMER_MOBILE+" varchar(20),"+COL_CUSTOMER_ADDRESS+" varchar(50),"+
                COL_LOAN_ID+" varchar(10),"+COL_LOAN_AMOUNT+" varchar(20),"+COL_LOAN_TYPE+" varchar(50),"+COL_LOAN_INTEREST+" varchar(10),"+COL_LOAN_TIME_PERIOD+" varchar(10),"+COL_LOAN_DUE_AMOUNT+" varchar(20),"+COL_LOAN_FINAL_AMOUNT+" varchar(20),"+COL_LOAN_BALANCE_AMOUNT+" varchar(20),"+COL_LOAN_ISSUED_DATE+" DATETIME DEFAULT CURRENT_TIMESTAMP)";

        public static final String DROP_LOAN_TABLE="drop table if exists "+CUSTOMER_LOAN_ENTRY_TABLE;



        //End of Customer Loan Information Table Creation


        public DataHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_LOAN_COLLECTION_TABLE);
            db.execSQL(CREATE_USERS_LOGIN_REGISTRATION_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_LOAN_TABLE)  ;
            db.execSQL(DROP_LOAN_COLLECTION_TABLE);
            db.execSQL(DROP_USERS_LOGIN_REGISTRATION_TABLE);
        }
    }
}

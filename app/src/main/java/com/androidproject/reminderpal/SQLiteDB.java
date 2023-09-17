package com.androidproject.reminderpal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLiteDB extends SQLiteOpenHelper {
    public static final String SQLITEDBNAME= "ReminderPal.db";
    private Context context;
    public SQLiteDatabase sqliteDB = null;
    public Cursor cursor;
    public ContentValues contVal;
    public long Result,lngEvent,lngCurrentDate;
    public String strUserName,strUserPassw,strUserID,strCurDate;
    public List<String> lstSpinner,lstSpinner2;
    public SimpleDateFormat DateFormat;
    public Date dteEvent;

    //----------------------------------------------------------------
    public SQLiteDB(@Nullable Context context) {
        super(context, SQLITEDBNAME, null, 1);
        this.context = context;
    }

    //----------------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase sqliteDB) {
        sqliteDB.execSQL("CREATE TABLE IF NOT EXISTS tblUser " +
                "(userID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userName VARCHAR, userPassword VARCHAR," +
                "userStatus CHARACTER)");
        sqliteDB.execSQL("CREATE TABLE IF NOT EXISTS tblTypeEvent " +
                "(typeID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "typeName VARCHAR, typeIcon VARCHAR)");
        sqliteDB.execSQL("CREATE TABLE IF NOT EXISTS tblEvent " +
                "(eveDetail VARCHAR, eveDate TEXT, eveTime TEXT, " +
                "eveStatus VARCHAR, eveUserID INTEGER, eveTypeID INTEGER, " +
                "CONSTRAINT fk_tblUser FOREIGN KEY(eveUserID) " +
                    "REFERENCES tblUser(userID) ON DELETE CASCADE, " +
                " CONSTRAINT fk_tblTypeEvent FOREIGN KEY(eveTypeID) " +
                    "REFERENCES tblTypeEvent(typeID) ON DELETE CASCADE)");
        cursor = sqliteDB.rawQuery("SELECT * FROM tblTypeEvent", null);
        if(cursor != null){
            if(cursor.getCount() <= 0){
                sqliteDB.execSQL("INSERT INTO tblTypeEvent " +
                        "VALUES(?1,'Birthday','birthday.png')");
                sqliteDB.execSQL("INSERT INTO tblTypeEvent " +
                        "VALUES(?1,'Holly day','holly_day.png')");
                sqliteDB.execSQL("INSERT INTO tblTypeEvent " +
                        "VALUES(?1,'Special Occasion','special_occasion.png')");
                sqliteDB.execSQL("INSERT INTO tblTypeEvent " +
                        "VALUES(?1,'Company Evenyt','company_event.png')");
                sqliteDB.execSQL("INSERT INTO tblTypeEvent " +
                        "VALUES(?1,'Project Deadline','project_deadline.png')");
            }
        }
    }

    //----------------------------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase sqliteDB, int i, int i1) {
        sqliteDB.execSQL(" DROP TABLE IF EXISTS tblUser");
        sqliteDB.execSQL(" DROP TABLE IF EXISTS tblTypeEvent");
        sqliteDB.execSQL(" DROP TABLE IF EXISTS tblEvent");
        onCreate(sqliteDB);
    }

    //----------------------------------------------------------------
    public Boolean insertUserData(String user, String password){
        sqliteDB = this.getWritableDatabase();
        contVal = new ContentValues();
        contVal.put("userName", user);
        contVal.put("userPassword", password);
        contVal.put("userStatus","A");
        Result = sqliteDB.insert("tblUser", null, contVal);
        if(Result == -1) return false;
        else return true;
    }

    //----------------------------------------------------------------
    public Boolean checkLogin(String user, String passw){
        sqliteDB = this.getWritableDatabase();
        cursor = sqliteDB.rawQuery("SELECT * FROM tblUser " +
                "WHERE userName = ? AND userPassword = ?",
                new String[]{user, passw});
        if(cursor.getCount() > 0) return true;
        else return false;
    }

    //----------------------------------------------------------------
    public Cursor getUserID(){
        strUserName = new LoginActivity().edtUserName.getText().toString();
        strUserPassw = new LoginActivity().edtPassword.getText().toString();
        sqliteDB = this.getReadableDatabase();
        cursor = sqliteDB.rawQuery("SELECT * FROM tblUser " +
                        "WHERE userName = '"+strUserName+"' " +
                        "AND userPassword = '"+strUserPassw+"'",
                        null);
        return cursor;
    }

    //----------------------------------------------------------------
    public void addEventData(String parDetail,String parDate,
                                   String parTime,int parUser,int parType){
        sqliteDB = this.getWritableDatabase();
        contVal = new ContentValues();
        contVal.put("eveDetail", parDetail);
        contVal.put("eveDate", parDate);
        contVal.put("eveTime", parTime);
        contVal.put("eveStatus","A");
        contVal.put("eveUserID", parUser);
        contVal.put("eveTypeID", parType);
        long result = sqliteDB.insert("tblEvent", null, contVal);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
        sqliteDB.close();
    }

    //----------------------------------------------------------------
    public Cursor getListDetail() {
        strUserName = new LoginActivity().edtUserName.getText().toString();
        strUserPassw = new LoginActivity().edtPassword.getText().toString();
        sqliteDB = this.getWritableDatabase();
        cursor  = sqliteDB.rawQuery("SELECT * FROM tblEvent AS ev " +
                        "JOIN tblUser AS us ON us.userID = ev.eveUserID " +
                        "JOIN tblTypeEvent AS TE ON TE.typeID = ev.eveTypeID " +
                        "WHERE us.userName = '"+strUserName+"' " +
                        "AND us.userPassword = '"+strUserPassw+"'",
                null);
        return cursor;
    }

    //----------------------------------------------------------------
    public List<String> fillOutSpinner(){
        lstSpinner = new ArrayList<String>();
        lstSpinner2 = new ArrayList<String>();
        sqliteDB = this.getReadableDatabase();
        cursor = sqliteDB.rawQuery("SELECT  * FROM tblTypeEvent", null);
        if (cursor.moveToFirst()) {
            do {
                lstSpinner.add(cursor.getString(1));
                lstSpinner2.add(cursor.getString(0));
            }
            while (cursor.moveToNext());
        }
        return lstSpinner;
    }

    //----------------------------------------------------------------
    public void deleteUser(String strUser,String strPassw){
        sqliteDB = this.getWritableDatabase();
        sqliteDB.execSQL("PRAGMA foreign_keys=ON;");
        Result = sqliteDB.delete("tblUser ","userName = ? " +
                "AND userPassword = ?",new String[]{strUser, strPassw});
        if(Result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    //----------------------------------------------------------------
    public void deleteEvent(){
        try {
            DateFormat = new SimpleDateFormat("yyyy-MM-dd");
            strCurDate = DateFormat.format(new Date());
            sqliteDB = this.getWritableDatabase();
            sqliteDB.execSQL("PRAGMA foreign_keys=ON;");
            Result = sqliteDB.delete("tblEvent ",
                    "eveDate < '"+strCurDate+"' " ,null);
            if(Result == -1){
                Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){ }
    }
}











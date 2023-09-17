package com.androidproject.reminderpal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.List;

public class NewEventActivity extends AppCompatActivity {
    public Spinner sprEventType;
    public EditText edtDetail,edtDate,edtTime;
    public Button btnSubEvent;
    public SQLiteDB sqliteDB = null;
    public Cursor cursor;
    public List<String> lstSpinner,lstSpinner2;
    public ArrayAdapter<String> arrSpinner;
    public String strSpinner;
    public Integer intUser,intTypeEvent;

    //----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        sprEventType = findViewById(R.id.sp_eventtype);
        edtDetail = findViewById(R.id.et_detail);
        edtDate = findViewById(R.id.et_date);
        edtTime = findViewById(R.id.et_time);
        btnSubEvent = findViewById(R.id.bt_submitevent);
        btnSubEvent.setOnClickListener(this::submitEvent);
        loadSpinnerData();
    }

    //----------------------------------------------------------------
    private void submitEvent(View view){
        sqliteDB = new SQLiteDB(NewEventActivity.this);
        getSelectedItem();
        sqliteDB.addEventData(edtDetail.getText().toString(),
                            edtDate.getText().toString(),
                            edtTime.getText().toString(),
                            intUser,
                            intTypeEvent);
        if(edtDetail.length() > 0){
            edtDetail.setText("");
            edtDate.setText("");
            edtTime.setText("");
            InputMethodManager Keyboard = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            Keyboard.hideSoftInputFromWindow(edtDetail.getWindowToken(), 0);
            loadSpinnerData();
        }
    }

    //----------------------------------------------------------------
    private void getSelectedItem(){
        strSpinner = sprEventType.getSelectedItem().toString();
        switch(strSpinner){
            case "Birthday":
                intTypeEvent = 1; break;
            case "Holly day":
                intTypeEvent = 2;
                break;
            case "Special Occasion":
                intTypeEvent = 3; break;
            case "Company Event":
                intTypeEvent = 4; break;
            case "Project Deadline":
                intTypeEvent = 5; break;
        }
        cursor = sqliteDB.getUserID();
        if(cursor.moveToNext()){
            intUser = Integer.valueOf(cursor.getString(0));
        }
    }

    //----------------------------------------------------------------
    private void loadSpinnerData(){
        sqliteDB = new SQLiteDB(getApplicationContext());
        lstSpinner = sqliteDB.fillOutSpinner();
        arrSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lstSpinner);
        arrSpinner.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        sprEventType.setAdapter(arrSpinner);
    }
}


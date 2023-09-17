package com.androidproject.reminderpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;

public class EventlistActivity extends AppCompatActivity {
    public RecyclerView rvEventList;
    public ArrayList<String> arrTypeName,arrTypeIcon,arrEventDetail,arrEventDate,arrEventTime;
    public SQLiteDB sqliteDB = null;
    public SQLiteDBAdapter adapter;
    public Cursor cursor;
    public Button btnNewEvent;

    //----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventlist);
        sqliteDB = new SQLiteDB(this);
        arrTypeName = new ArrayList<>();
        arrTypeIcon = new ArrayList<>();
        arrEventDetail = new ArrayList<>();
        arrEventDate = new ArrayList<>();
        arrEventTime = new ArrayList<>();
        rvEventList = findViewById(R.id.recyclerview);
        adapter = new SQLiteDBAdapter(this,arrTypeName,arrTypeIcon,
                arrEventDetail,arrEventDate,arrEventTime);
        rvEventList.setAdapter(adapter);
        rvEventList.setLayoutManager(new LinearLayoutManager(this));
        btnNewEvent = findViewById(R.id.bt_newevent);
        btnNewEvent.setOnClickListener(this::newEventActiv);
        displaydata();
    }

    //----------------------------------------------------------------
    private void displaydata() {
        cursor = sqliteDB.getListDetail();
        if(cursor.getCount()==0) {
            Toast.makeText(EventlistActivity.this,
                    "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while(cursor.moveToNext()) {
                arrTypeName.add(cursor.getString(11));
                arrTypeIcon.add(cursor.getString(12));
                arrEventDetail.add(cursor.getString(0));
                arrEventDate.add(cursor.getString(1));
                arrEventTime.add(cursor.getString(2));
            }
        }
    }

    //----------------------------------------------------------------
    public void newEventActiv(View view){
        Intent intent = new Intent(this, NewEventActivity.class);
        startActivity(intent);
        finish();
    }
}
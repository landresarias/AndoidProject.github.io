package com.androidproject.reminderpal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReminderActivity extends AppCompatActivity {
    private Button btnNewEvent;

    //----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        btnNewEvent = findViewById(R.id.idNewEvButton);
        btnNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReminderActivity.this, NewEventActivity.class));
                finish();
            }
        });
    }
}
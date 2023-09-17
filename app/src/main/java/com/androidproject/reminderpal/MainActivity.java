package com.androidproject.reminderpal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.androidproject.reminderpal.MESSAGE";
    public Button btnMainButton;
    public SQLiteDB sqLiteDB;

    //----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMainButton = findViewById(R.id.idEnterButton);
        btnMainButton.setOnClickListener(this::loginActiv);
        sqLiteDB = new SQLiteDB(this);
    }

    //----------------------------------------------------------------
    public void loginActiv(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
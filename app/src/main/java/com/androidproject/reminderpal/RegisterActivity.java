package com.androidproject.reminderpal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    public EditText edtUserNameReg, edtPasswordReg, edtConfirmReg;
    public Button btnSubmitReg,btnDeleteReg;
    public SQLiteDB sqliteDB = null;
    public String strUser,strPassw,strRepassw;
    public Boolean checkLogin,blnInsertUser;
    
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUserNameReg = findViewById(R.id.idUserReg);
        edtPasswordReg = findViewById(R.id.idPasswordReg);
        edtConfirmReg = findViewById(R.id.idConfirmPassw);
        btnSubmitReg = findViewById(R.id.idSubmitReg);
        btnSubmitReg.setOnClickListener(this::registerUser);
        btnDeleteReg = findViewById(R.id.idDeleteReg);
        btnDeleteReg.setOnClickListener(this::deleteUser);
                sqliteDB = new SQLiteDB(this);
    }

    //----------------------------------------------------------------
    public void registerUser(View view){
        strUser = edtUserNameReg.getText().toString();
        strPassw = edtPasswordReg.getText().toString();
        strRepassw = edtConfirmReg.getText().toString();
        if(strUser.equals("")||strPassw.equals("")||
                strRepassw.equals("")){
            Toast.makeText(RegisterActivity.this,
                    "Please enter all the fields!!",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            if(strPassw.equals(strRepassw)){
                checkLogin = sqliteDB.checkLogin(strUser,strPassw);
                if(checkLogin == false){
                    blnInsertUser = sqliteDB.insertUserData(strUser,strPassw);
                    if(blnInsertUser == (true)){
                        Toast.makeText(RegisterActivity.this,
                                "Registered Successfully!!",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,
                                "Registration Failed!!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this,
                            "User already exists!! Please sign in.",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(RegisterActivity.this,
                        "Password not matching",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    //----------------------------------------------------------------
    public void deleteUser(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + edtUserNameReg.getText().toString() + " ?");
        builder.setMessage("Are you sure you want to delete " +
                edtUserNameReg.getText().toString() + " ?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sqliteDB = new SQLiteDB(RegisterActivity.this);
                sqliteDB.deleteUser(edtUserNameReg.getText().toString(),
                        edtPasswordReg.getText().toString());
                Intent intent = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { }
        });
        builder.create().show();
    }
}
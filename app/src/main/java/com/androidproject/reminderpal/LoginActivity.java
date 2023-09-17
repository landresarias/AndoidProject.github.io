package com.androidproject.reminderpal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    public static EditText edtUserName,edtPassword;
    private Button btnLogin,btnRegister;
    public SQLiteDB sqLiteDB;
    public Boolean isLogin;

    //----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUserName = findViewById(R.id.idUserName);
        edtPassword = findViewById(R.id.idPassword);
        sqLiteDB = new SQLiteDB(this);
        btnLogin = findViewById(R.id.idLoginButton);
        btnRegister =  findViewById(R.id.idLoginReg);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,
                        RegisterActivity.class));
            }
        });
        loginUser();
    }

    //----------------------------------------------------------------
    private void loginUser() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLogin = sqLiteDB.checkLogin(edtUserName.getText().toString(),
                        edtPassword.getText().toString());
                if (isLogin){
                    sqLiteDB.deleteEvent();
                    Toast.makeText(LoginActivity.this,
                            "Login Successfully",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,
                            EventlistActivity.class));
                    //finish();
                }
                else{
                    Toast.makeText(LoginActivity.this,
                            "Login Failed !!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}








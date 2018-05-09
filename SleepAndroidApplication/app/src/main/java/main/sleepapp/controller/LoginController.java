package main.sleepapp.controller;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import main.sleepapp.R;


/**
 * A login screen that offers login via email/password.
 */
public class LoginController extends AppCompatActivity {

    private EditText mUserID,mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginview);
        // Set up the login form.
        mUserID = (EditText) findViewById(R.id.userID);
        mPassword = (EditText) findViewById(R.id.password);

        Button handleLogin = (Button) findViewById(R.id.btn_login);
        handleLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String UserName = mUserID.getText().toString();
                String Pwd = mPassword.getText().toString();
                String type = "login";

                DatabaseController databaseController = new DatabaseController(LoginController.this);
                databaseController.execute(type, UserName, Pwd);

            }
        });
        Button goToRegister = (Button) findViewById(R.id.btn_gotoregister);
        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleGoToRegister();
            }
        });
    }
    public void handleGoToRegister() {
        setContentView(R.layout.registerview);
    }

}



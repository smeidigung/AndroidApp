package main.sleepapp.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import main.sleepapp.R;
import main.sleepapp.model.StudentModel;

public class LoginController extends AppCompatActivity {

    private EditText mUserID,mPassword,rPassword,rUserName;
    private StudentModel studentModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginview);
        mUserID = (EditText) findViewById(R.id.userID);
        mPassword = (EditText) findViewById(R.id.password);



        studentModel = new StudentModel();

        Button loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
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

    private void handleRegister() {
        String password = rPassword.getText().toString();
        String name = rUserName.getText().toString();
        String firstname, surname;

        if (name.contains(" ")) {
            firstname = name.substring(0,name.indexOf(" "));
            surname = name.substring(name.indexOf(" ")+1);
        } else {
            firstname = name;
            surname = "ukendt";
        }
        studentModel = new StudentModel();
        String string = studentModel.updateModel("register",password,firstname,surname);

        if (string.startsWith("Insert successful: Student_id")) {
            String registerSubString = "Insert successful: Student_id = ";

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder .setMessage("Brugernavn: " + string.replace(registerSubString, ""))
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            handleGoToLogin();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void handleLogin(){
        studentModel.setStudent_id(mUserID.getText().toString());
        studentModel.setPassword(mPassword.getText().toString());
        String string = validateID(studentModel.getStudent_id(),studentModel.getPassword());

        if (string.equalsIgnoreCase("login success")) {
            Intent intent = new Intent(this,MainController.class);
            this.studentModel.setStudent_id(mUserID.getText().toString());
            studentModel.loadModel();
            intent.putExtra("studentModel",this.studentModel);
            this.startActivity(intent);
        }
    }

    private String validateID(String studentID, String studentPassword){
        String string = studentModel.validateID();
        return string;
    }

    private void handleGoToRegister() {
        setContentView(R.layout.registerview);
        rPassword = (EditText) findViewById(R.id.RegisterPassword);
        rUserName = (EditText) findViewById(R.id.RegisterName);
        Button registerButton = (Button) findViewById(R.id.btn_register_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRegister();
            }
        });
    }

    private void handleGoToLogin () {
        Intent Intent = new Intent(this, LoginController.class);
        startActivity(Intent);
    }
}



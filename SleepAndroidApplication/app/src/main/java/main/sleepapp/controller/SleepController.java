package main.sleepapp.controller;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.sql.SQLOutput;

import main.sleepapp.R;
import main.sleepapp.model.StudentModel;
import main.sleepapp.util.DatePickerFragment;

public class SleepController extends AppCompatActivity implements DatabaseController.AsyncResponse {

    StudentModel studentModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentModel = getIntent().getParcelableExtra("studentModel");

        String string = getIntent().getStringExtra("type");

        if (string.equals("sleephabits")) {
            setContentView(R.layout.sleephabitsview);
        }
        if (string.equals("previoussleep")) {
            setContentView(R.layout.previoussleepview);
            fillView();
        }

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"dataPicker");
    }
    public void processFinish(String string) {

    }

    public void getDate(int year, int month, int day) {

    }
     public void fillView () {
        String type = "previoussleep";
         DatabaseController databaseController = new DatabaseController(SleepController.this,this);
         databaseController.execute(type);
     }

     public void setStudentModel() {

     }
}

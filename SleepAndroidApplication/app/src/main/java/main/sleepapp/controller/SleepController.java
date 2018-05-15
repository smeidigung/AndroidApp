package main.sleepapp.controller;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


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
            //fillView();
        }

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"dataPicker");
    }

    public void handleGoToAssessment(Context context){
        new AssessmentController(studentModel,context);
    }
    public void processFinish(String string) {

    }

    public void getDate(int year, int month, int day) {

    }
     public void fillView () {
        String type = "previoussleep";
         DatabaseController databaseController = new DatabaseController();
         databaseController.execute(type,studentModel.getStudent_id());
     }

     public void setStudentModel(StudentModel studentModel) {
        this.studentModel = studentModel;
     }
}

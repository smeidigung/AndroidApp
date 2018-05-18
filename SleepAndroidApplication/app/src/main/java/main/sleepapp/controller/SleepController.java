package main.sleepapp.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import main.sleepapp.R;
import main.sleepapp.model.StudentModel;

public class SleepController extends AppCompatActivity {

    private StudentModel studentModel;

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

    public void handleGoToAssessment(Context context){
        new AssessmentController(studentModel,context);
    }

     public void setStudentModel(StudentModel studentModel) {
        this.studentModel = studentModel;
     }
}

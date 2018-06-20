package main.sleepapp.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import main.sleepapp.R;
import main.sleepapp.model.StudentModel;

/**
 * Håndtere kommunikation mellem flere controllere.
 */
public class SleepController extends AppCompatActivity {

    private StudentModel studentModel;

    /**
     * Default metoden som kaldes når en activity oprettes.
     */
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

    @Override
    public void onPause(){
        super.onPause();
        finish();
    }

    /**
     * Håndtere en lukning af denne activity, ved at oprette en ny MainController.
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        Intent intent = new Intent(SleepController.this, MainController.class);
        intent.putExtra("studentModel",studentModel);
        SleepController.this.startActivity(intent);
    }

    /**
     * Håndtere oprettelsen af en ny AssessmentController.
     */
    public void handleGoToAssessment(Context context){
        new AssessmentController(studentModel,context);
    }

    /**
     * Sætter attributten studentModel af typen StudentModel til input argumentet studentModel
     */
     public void setStudentModel(StudentModel studentModel) {
        this.studentModel = studentModel;
     }
}

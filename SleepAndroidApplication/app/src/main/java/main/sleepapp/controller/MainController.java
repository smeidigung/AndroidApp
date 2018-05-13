package main.sleepapp.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.sql.SQLOutput;

import main.sleepapp.R;
import main.sleepapp.model.MeetingModel;
import main.sleepapp.model.SleepModel;
import main.sleepapp.model.StudentModel;
import main.sleepapp.model.UserModel;

public class MainController extends AppCompatActivity {

    StudentModel studentModel;
    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview);
        studentModel = getIntent().getParcelableExtra("studentModel");

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);

    }

    private void setSingleEvent(GridLayout mainGrid) {
        for (int i = 0; i< mainGrid.getChildCount();i++){
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (finalI == 0){

                    }
                    else if(finalI == 1){

                    }
                    else if(finalI == 2){

                    }
                    else {

                    }
                }
            })  ;
        }
    }

    public void handleGoToSleepHabits () {
        
    }
}

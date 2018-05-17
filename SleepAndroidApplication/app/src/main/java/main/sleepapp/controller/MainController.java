package main.sleepapp.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.sql.SQLOutput;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import main.sleepapp.R;
import main.sleepapp.model.MeetingModel;
import main.sleepapp.model.SleepModel;
import main.sleepapp.model.StudentModel;
import main.sleepapp.model.UserModel;

public class MainController extends AppCompatActivity{

    private StudentModel studentModel;
    private Date sleepDate, awokeDate;
    private Context context;

    private TextView timerTextView;
    long startTime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview);
        studentModel = getIntent().getParcelableExtra("studentModel");
        this.context = this;


        GridLayout mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timerTextView.setText("0:00");

        Button toggleTimerButton = (Button) findViewById(R.id.button);
        toggleTimerButton.setText("Start Måling af Søvnlængde");
        toggleTimerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("Stop Måling af Søvnlængde")) {
                    timerHandler.removeCallbacks(timerRunnable);

                    awokeDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");

                    String sleep_dateTime = sdfDate.format(sleepDate);
                    String awoke_dateTime = sdfDate.format(awokeDate);

                    SleepModel sleepModel = new SleepModel(studentModel.getStudent_id(),sleep_dateTime,awoke_dateTime);
                    sleepModel.updateModel();

                    SleepController sleepController = new SleepController();
                    sleepController.setStudentModel(studentModel);
                    sleepController.handleGoToAssessment(context);

                    b.setText("Start Måling af Søvnlængde");
                } else {
                    startTime = System.currentTimeMillis();
                    sleepDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("Stop Måling af Søvnlængde");
                }
            }
        });
    }
    private void setSingleEvent(GridLayout mainGrid) {
        for (int i = 0; i< mainGrid.getChildCount();i++){
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (finalI == 0){
                        handleGoToSleepHabits();
                    }
                    else if(finalI == 1){
                        handleGoToPreviousSleep();
                    }
                    else if(finalI == 2){
                        handleGoToMeeting();
                    }
                    else {
                        handleGoToConsent();
                    }
                }
            })  ;
        }
    }

    public void handleGoToSleepHabits () {
        Intent intent = new Intent(this, SleepController.class);
        intent.putExtra("type","sleephabits");
        startActivity(intent);
    }

    public void handleGoToConsent () {
        Intent intent = new Intent(this, ConsentController.class);
        intent.putExtra("studentModel",studentModel);
        startActivity(intent);
    }

    public void handleGoToPreviousSleep () {
        Intent intent = new Intent(this, SleepController.class);
        intent.putExtra("type","previoussleep");
        intent.putExtra("studentmodel", studentModel);
        startActivity(intent);
    }

    public void handleGoToMeeting() {
        if(new MeetingModel().checkModel(studentModel)) {
            Intent intent = new Intent(this, MeetingController.class);
            intent.putExtra("studentModel",studentModel);
            intent.putExtra("hasMeeting",true);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MeetingController.class);
            intent.putExtra("studentModel",studentModel);
            intent.putExtra("hasMeeting",false);
            startActivity(intent);
        }
    }


/*    Referer student model til databasecontroller*/

}

package main.sleepapp.controller;

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

public class MainController extends AppCompatActivity {

    StudentModel studentModel;
    GridLayout mainGrid;

    TextView timerTextView;
    long startTime = 0;

    private Date sleep_date;
    private Date awoke_date;
    private Time sleep_time;
    private Time awoke_time;

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

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timerTextView.setText("0:00");

        Button toggleTimerButton = (Button) findViewById(R.id.button);
        toggleTimerButton.setText("start");
        toggleTimerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("stop")) {
                    timerHandler.removeCallbacks(timerRunnable);

                    awoke_date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");

                    String sleep_dateTime = sdfDate.format(sleep_date);
                    String awoke_dateTime = sdfDate.format(awoke_date);

                    String type = "timer";
                    DatabaseController dbController = new DatabaseController(MainController.this);
                    dbController.execute(type,sleep_dateTime,awoke_dateTime);

                    b.setText("start");
                } else {
                    startTime = System.currentTimeMillis();
                    sleep_date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("stop");
                    //new AssessmentController(); //TODO: ADD THIS BACK IN
                }
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button b = (Button)findViewById(R.id.button);
        b.setText("start");
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
                        handleAssessment(); // TODO: SKAL IKKE VÆRE HER. SKAL BARE KØRE AF SIG SELV. Bruges kun til debug lige pt.
                    }
                    else {
                        handleGoToConcent();
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

    public void handleGoToConcent () {
        Intent intent = new Intent(this, ConsentController.class);
        startActivity(intent);
    }

    public void handleGoToPreviousSleep () {
        Intent intent = new Intent(this, SleepController.class);
        intent.putExtra("type","previoussleep");
        intent.putExtra("studentmodel", studentModel);
        startActivity(intent);
    }

    public void handleAssessment(){
        new AssessmentController(this);
    }
}

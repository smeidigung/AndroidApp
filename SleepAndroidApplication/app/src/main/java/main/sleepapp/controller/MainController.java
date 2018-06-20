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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import main.sleepapp.R;
import main.sleepapp.model.MeetingModel;
import main.sleepapp.model.SleepModel;
import main.sleepapp.model.StudentModel;

/**
 * Håndterer hovedmenuen og timeren.
 */
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


    /**
     * Dette er default metoden, som kaldes når en activity oprettes.
     * Den sætter viewet til at være mainView og opretter de fire knapper, som skal bruges til
     * at navigere mellem applikationens funktionaliteter.
     * Derefter oprettes en timer-knap, som gør en af to ting afhængigt af, hvad knappens status er.
     * Enten stoppes en timer, hvorfor start- og stoptid gemmes sammen med brugerid'et i
     * modellen studentModel. Modellen opdaterer databasen ved updateModel, og en SleepController
     * oprettes hvor 'assessment' køres. Anden mulighed er at starte en timer, og teksten
     * på knappen ændres.
     */
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

        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logout();
            }
        });

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

    /**
     * Gennemgår hvert element i mainGrid, opretter et cardview, og sætter en onClickListerner til
     * hvert cardView.
     */
    private void setSingleEvent(GridLayout mainGrid) {
        for (int i = 0; i< mainGrid.getChildCount();i++){
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (finalI == 0){
                        goToSleephabits();
                    }
                    else if(finalI == 1){
                        goToPreviousSleep();
                    }
                    else if(finalI == 2){
                        goToAcceptMeeting();
                    }
                    else {
                        goToConsent();
                    }
                }
            })  ;
        }
    }

    /**
     * Håndterer skiftet til et nyt skærmbillede/avtivity.
     */
    public void goToSleephabits () {
        Intent intent = new Intent(this, SleepController.class);
        intent.putExtra("type","sleephabits");
        startActivity(intent);
        finish();
    }

    /**
     * Håndtere skriftet til et nyt skærmbillede/avtivity.
     */
    public void goToConsent () {
        Intent intent = new Intent(this, ConsentController.class);
        intent.putExtra("studentModel",studentModel);
        startActivity(intent);
        finish();
    }

    /**
     * Håndtere skriftet til et nyt skærmbillede/avtivity.
     */
    public void goToPreviousSleep () {
        Intent intent = new Intent(this, SleepController.class);
        intent.putExtra("type","previoussleep");
        intent.putExtra("studentmodel", studentModel);
        startActivity(intent);
        finish();
    }

    /**
     * Håndtere skriftet til et nyt skærmbillede/avtivity.
     */
    public void goToAcceptMeeting() {
        if(new MeetingModel().checkModel(studentModel)) {
            Intent intent = new Intent(this, MeetingController.class);
            intent.putExtra("studentModel",studentModel);
            intent.putExtra("hasMeeting",true);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Håndtere logud, ved at lukke den pågældende activity
     */
    public void logout() {
        finish();
    }

}

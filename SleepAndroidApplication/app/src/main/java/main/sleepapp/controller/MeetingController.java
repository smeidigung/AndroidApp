package main.sleepapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import main.sleepapp.R;
import main.sleepapp.model.MeetingModel;
import main.sleepapp.model.StudentModel;
import main.sleepapp.model.UserModel;

/**
 * Håndterer accept eller afvisning af et nyt møde, og giver et overblik over allerede arrangerede
 * møder.
 */
public class MeetingController extends AppCompatActivity{

    private MeetingModel meetingModel;
    private UserModel userModel;
    private StudentModel studentModel;


    /**
     * Default metoden som kaldes, når en activity oprettes.
     * Den sætter viewet til at være et af to views: et view hvor der kan accepteres eller
     * afvises et møde, og et view der viser et overblik over eventuelle møder.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean hasMeeting = false;
        studentModel = getIntent().getParcelableExtra("studentModel");
        hasMeeting = getIntent().getBooleanExtra("hasMeeting",hasMeeting);

        if (hasMeeting){
            showMeeting();
        } else {
            setContentView(R.layout.acceptmeetingview);
            Button acceptButton = (Button) findViewById(R.id.acceptButton);
            Button rejectButton = (Button) findViewById(R.id.rejectButton);
            acceptButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    handleAccept();
                }
            });
            rejectButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    handleReject();
                }
            });
        }


    }

    @Override
    public void onPause(){
        super.onPause();
        finish();
    }

    /**
     * Hånderer opsætningen af viewet meetingView, som skal give et overblik over eventuelle møder.
     * Første oprettes modellerne meetingModel og userModel, hvor meetingModel hentes fra databasen.
     * Dernæst tjekkes det, om der er accepteret en møde af sundhedskoordinatoren, og hvis ikke
     * sættes status til "venter på svar". Ellers hentes userModel fra databasen med det
     * pågældende userID, og denne information præsenteres for brugeren.
     */
    private void showMeeting(){
        setContentView(R.layout.meetingview);
        meetingModel = new MeetingModel();
        userModel = new UserModel();

        getMeetingModel().loadModel(getStudentModel());

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        TextView textDato = (TextView) findViewById(R.id.textDato);
        TextView textStatus = (TextView) findViewById(R.id.textStatus);
        TextView textHC = (TextView) findViewById(R.id.textHC);
        textDato.setText(sdfDate.format(getMeetingModel().getMeetingTime()));
        if (getMeetingModel().getParticipatingCoordinator().equals("0")){
            textStatus.setText("Venter på svar");
        } else {
            textStatus.setText("Accepteret");
            getUserModel().loadModel(getMeetingModel().getParticipatingCoordinator());
            textHC.setText(getMeetingModel().getParticipatingCoordinator());
        }

    }

    /**
     * Håndterer et accept af mødet ved at oprette et nyt møde.
     * En ny MeetingModel oprettes, og den udfyldes med oprettelsedato, og brugerID.
     * Derefter kaldes metoden updateModel, som sender indformationerne til databasen.
     */
    private void handleAccept(){
        meetingModel = new MeetingModel();
        String type = "meeting";    
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
        sdfDate.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        String currentDate = sdfDate.format(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        getMeetingModel().updateModel(type,currentDate, getStudentModel().getStudent_id());

        finish();
    }

    /**
     * Håndterer en afvisning af mødet ved at afslutte den pågældende activity.
     */
    private void handleReject(){
        finish();
    }

    /**
     * Håndterer en lukning af denne activity ved at oprette en ny instans af MainController.
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        Intent intent = new Intent(MeetingController.this, MainController.class);
        intent.putExtra("studentModel",studentModel);
        MeetingController.this.startActivity(intent);
    }

    /**
     * Returnerer attributten meetingModel af typen MeetingModel.
     */
    public MeetingModel getMeetingModel() {
        return meetingModel;
    }

    /**
     * Returnerer attributten userModel af typen UserModel.
     */
    public UserModel getUserModel() {
        return userModel;
    }

    /**
     * Returnerer attributten studentModel af typen StudentModel.
     */
    public StudentModel getStudentModel() {
        return studentModel;
    }
}

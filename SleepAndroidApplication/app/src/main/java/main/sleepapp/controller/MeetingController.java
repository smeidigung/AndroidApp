package main.sleepapp.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

public class MeetingController extends AppCompatActivity{

    private MeetingModel meetingModel;
    private UserModel userModel;
    private StudentModel studentModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
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

    private void showMeeting(){
        setContentView(R.layout.meetingview);
        meetingModel = new MeetingModel();
        userModel = new UserModel();
        meetingModel.loadModel(studentModel,userModel);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");

        TextView textDato = (TextView) findViewById(R.id.textDato);
        TextView textStatus = (TextView) findViewById(R.id.textStatus);
        TextView textHC = (TextView) findViewById(R.id.textHC);
        textDato.setText(sdfDate.format(meetingModel.getMeetingTime()));
        textHC.setText(meetingModel.getParticipatingCoordinator().getName());
        textStatus.setText("Venter på svar");
    }

    private void handleAccept(){
        meetingModel = new MeetingModel();
        String type = "meeting";    
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
        sdfDate.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        String currentDate = sdfDate.format(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        meetingModel.updateModel(type,currentDate,studentModel.getStudent_id());

        finish();
    }

    private void handleReject(){
        finish();
    }
}

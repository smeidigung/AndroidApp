package main.sleepapp.controller;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import main.sleepapp.R;
import main.sleepapp.model.MeetingModel;

public class MeetingController extends AppCompatActivity{

    private MeetingModel meetingModel;
    private String userID;
    private String studentID;
    private String type = "meeting";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceptmeetingview);
        studentID = getIntent().getStringExtra("studentID");

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

    public void handleAccept(){
        this.type = "meeting";
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
        sdfDate.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        String currentDate = sdfDate.format(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        DatabaseController databaseController = new DatabaseController();
        databaseController.execute(type,currentDate,studentID,"0","Ukendt");
    }

    public void handleReject(){
        this.type = "checkMeeting";
        DatabaseController databaseController = new DatabaseController();
        databaseController.execute(type,studentID);
    }
}

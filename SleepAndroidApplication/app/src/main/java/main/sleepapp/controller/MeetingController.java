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

public class MeetingController extends AppCompatActivity{

    private MeetingModel meetingModel;
    private String userID;
    private StudentModel studentModel;
    private String type = "meeting";
    private boolean hasMeeting;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentModel = getIntent().getParcelableExtra("studentModel");
        hasMeeting = getIntent().getBooleanExtra("hasMeeting",hasMeeting);

        if (hasMeeting){
            handleMeetingView();
        } else {
            handleNoMeetingView();
        }


    }

    public void handleNoMeetingView(){
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
    public void handleMeetingView(){
        setContentView(R.layout.meetingview);
        MeetingModel meetingModel = new MeetingModel();
        meetingModel.loadModel(studentModel);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");

        TextView textDato = (TextView) findViewById(R.id.textDato);
        TextView textStatus = (TextView) findViewById(R.id.textStatus);
        textDato.setText(sdfDate.format(meetingModel.getMeetingTime()));
        textStatus.setText("Venter på svar");
    }

    public void handleAccept(){
        this.type = "meeting";
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
        sdfDate.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        String currentDate = sdfDate.format(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        DatabaseController databaseController = new DatabaseController();
        databaseController.execute(type,currentDate,studentModel.getStudent_id(),"0","Ukendt");
        finish();
    }

    public void handleReject(){
        finish();
    }
}

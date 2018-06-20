package main.sleepapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import main.sleepapp.R;
import main.sleepapp.model.StudentModel;

/**
 * ConsentController is used to handle the ConsentView.
 * It takes a user input and set the database values accordingly.
 */
public class ConsentController extends AppCompatActivity {

    private StudentModel studentModel;

    /**
     * The default method to be called in an activity.
     * It set the layout file to be used, and instantiates the button on screen.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consentview);

        studentModel = getIntent().getParcelableExtra("studentModel");

        final Button btnAccept = (Button) findViewById(R.id.acceptButton);
        final Button btnReject = (Button) findViewById(R.id.rejectButton);

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleReject();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAccept();
            }
        });
    }

    /**
     * Is called when the user clicks the "reject" button, and sets the consent value in the
     * database to 0. Then it calls the MainController activity.
     */
    private void handleReject() {
        String type = "consent";
        studentModel.updateModel(type,"0");
        finish();
    }

    /**
     * Is called when the user clicks the "accept" button, and sets the consent value in the
     * database to 1. Then it calls the MainController activity.
     */
    private void handleAccept(){
        String type = "consent";
        studentModel.updateModel(type,"1");
        finish();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Intent intent = new Intent(ConsentController.this, MainController.class);
        intent.putExtra("studentModel",studentModel);
        ConsentController.this.startActivity(intent);
    }
}

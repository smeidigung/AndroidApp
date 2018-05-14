package main.sleepapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import main.sleepapp.R;


public class ConsentController extends AppCompatActivity {

    private String consent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consentview);
        final Button btnAccept = (Button) findViewById(R.id.acceptButton);
        final Button btnReject = (Button) findViewById(R.id.rejectButton);

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consent = "0";
                String type = "consent";
                DatabaseController dbController = new DatabaseController(ConsentController.this);
                dbController.execute(type, consent);
                Intent intent = new Intent(ConsentController.this, MainController.class);
                ConsentController.this.startActivity(intent);
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consent = "1";
                String type = "consent";
                DatabaseController dbController = new DatabaseController(ConsentController.this);
                dbController.execute(type, consent);
                Intent intent = new Intent(ConsentController.this, MainController.class);
                ConsentController.this.startActivity(intent);
            }
        });

    }
}

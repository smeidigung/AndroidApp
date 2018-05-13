package main.sleepapp.controller;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.sql.SQLOutput;

import main.sleepapp.R;
import main.sleepapp.util.DatePickerFragment;

public class SleepController extends AppCompatActivity implements DatabaseController.AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String string = getIntent().getStringExtra("type");

        if (string.equals("sleephabits")) {
            setContentView(R.layout.sleephabitsview);
            System.out.println("HERE");
        }

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"dataPicker");
    }
    public void processFinish(String string) {

    }

    public void getDate(int year, int month, int day) {

    }
}

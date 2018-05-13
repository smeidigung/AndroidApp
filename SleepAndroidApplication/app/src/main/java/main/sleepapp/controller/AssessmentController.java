package main.sleepapp.controller;

import android.icu.util.DateInterval;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AssessmentController implements DatabaseController.AsyncResponse {

    private String result;
    private MainController mainController;

    public AssessmentController(MainController mainController) {
        this.mainController = mainController;
        String type = "assessment";
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
        Date current_date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Date earliere_date = new Date(current_date.getTime() - 604800000L);
        String earliere_dateTime = sdfDate.format(earliere_date);

        DatabaseController dbController = new DatabaseController(this);
        dbController.execute(type,mainController.studentModel.getStudent_id(),earliere_dateTime);
    }

    public void processFinish(String string) {
        this.result = string;
        handleData();
    }

    private void handleData() {
        List<String> items = Arrays.asList(result.split("\\s*,\\s*"));
        ArrayList<Date> sleepTime = new ArrayList<>();
        ArrayList<Date> awokeTime = new ArrayList<>();
        ArrayList<Long> diffTime = new ArrayList<>();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long diff = 0L;
        System.out.println(items);
        for(int i = 0; i < items.size(); i = i + 2) {
            try {
                sleepTime.add(sdfDate.parse(items.get(i)));
                awokeTime.add(sdfDate.parse(items.get(i + 1)));
            } catch (ParseException e) {
                System.out.println("DATES IS WRONG");
            }
        }
        for (int i = 0; i < sleepTime.size(); i++) {
            diffTime.add((sleepTime.get(i).getTime() - awokeTime.get(i).getTime())*-1);
            diff =+ diffTime.get(i);
        }
        diff = diff/diffTime.size();
        if (diff < 28800000L) {
            System.out.println("DU SOVER FOR LIDT"); //TODO: Tilføj logic som giver en advarsel til brugeren.
        }


    }

}

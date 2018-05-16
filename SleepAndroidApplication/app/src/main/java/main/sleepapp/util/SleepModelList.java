package main.sleepapp.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import main.sleepapp.controller.DatabaseController;
import main.sleepapp.controller.MainController;
import main.sleepapp.model.SleepModel;
import main.sleepapp.model.StudentModel;

public class SleepModelList {

    public ArrayList<SleepModel> sleepModelList = new ArrayList<>();

    public SleepModelList(StudentModel studentModel) {
        String type = "assessment";
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
        Date current_date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Date earliere_date = new Date(current_date.getTime() - 604800000L);
        String earliere_dateTime = sdfDate.format(earliere_date);

        DatabaseController dbController = new DatabaseController();
        String result;
        List<String> items;
        try {
            result = dbController.execute(type, studentModel.getStudent_id(), earliere_dateTime).get();
            if (result.contains("Error:")) {
                return;
            } else {
                items = Arrays.asList(result.split("\\s*,\\s*"));
                for (int i = 0; i < items.size(); i = i + 2) {
                    sleepModelList.add(new SleepModel(studentModel.getStudent_id(), items.get(i), items.get(i + 1)));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

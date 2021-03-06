package main.sleepapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import main.sleepapp.controller.DatabaseController;
import main.sleepapp.controller.MainController;
import main.sleepapp.model.SleepModel;
import main.sleepapp.model.StudentModel;

/**
 * Denne model inderholder et array af SleepModels og beregner antal timer, som eleven har sovet.
 */
public class SleepModelList {

    public ArrayList<SleepModel> sleepModelList = new ArrayList<>();
    public int nightsSlept;


    /**
     * Default constructor som kaldes, når denne klasse oprettes.
     * Den håndterer hentning af data fra databasen, og gemmer det i et array af SleepModels.
     */
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
                this.nightsSlept = items.size();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Beregner forskellen mellem sove- og opvågningstidspunktet.
     */
    public Long diffSleepTime(List<SleepModel> sleepModelList){
        ArrayList<Date> sleepTime = new ArrayList<>();
        ArrayList<Date> awokeTime = new ArrayList<>();
        ArrayList<Long> diffTime = new ArrayList<>();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfDate.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        Long diff = 0L;
        for(SleepModel sleepModel: sleepModelList) {
            try {
                sleepTime.add(sdfDate.parse(sleepModel.getSleep_time()));
                awokeTime.add(sdfDate.parse(sleepModel.getAwoke_time()));
            } catch (ParseException e) {

            }
        }
        for (int i = 0; i < sleepTime.size(); i++) {
            if(sleepTime.get(i).getTime() < awokeTime.get(i).getTime()) {
                diffTime.add((sleepTime.get(i).getTime() - awokeTime.get(i).getTime()));
            } else {
                diffTime.add((awokeTime.get(i).getTime() - sleepTime.get(i).getTime()) + 86400000L);
            }
            diff += diffTime.get(i);
        }
        return diff/diffTime.size();
    }
}

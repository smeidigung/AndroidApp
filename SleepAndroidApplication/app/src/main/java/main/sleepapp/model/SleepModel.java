package main.sleepapp.model;


import java.util.Date;

import main.sleepapp.controller.DatabaseController;

public class SleepModel {

    private String student_id;
    private String sleep_time;
    private String awoke_time;

    public SleepModel(String student_id, String sleep_time, String awoke_time) {
        this.student_id = student_id;
        this.sleep_time = sleep_time;
        this.awoke_time = awoke_time;
    }

    public void updateModel() {
        String type = "timer";
        DatabaseController dbController = new DatabaseController();
        dbController.execute(type,sleep_time,awoke_time);
    }

    public void loadModel(){
        String type = "timer";
        DatabaseController dbController = new DatabaseController();
        dbController.execute(type,sleep_time,awoke_time);
    }

}

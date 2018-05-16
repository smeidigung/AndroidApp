package main.sleepapp.model;


import main.sleepapp.controller.DatabaseController;

public class SleepModel {

    private String student_id;
    private String sleep_time;
    private String awoke_time;

    public SleepModel(String student_id, String sleep_time, String awoke_time) {
        this.setStudent_id(student_id);
        this.setSleep_time(sleep_time);
        this.setAwoke_time(awoke_time);
    }

    public void updateModel() {
        String type = "timer";
        DatabaseController dbController = new DatabaseController();
        dbController.execute(type, getStudent_id(), getSleep_time(), getAwoke_time());
    }

    public void loadModel(){
        String type = "timer";
        DatabaseController dbController = new DatabaseController();
        dbController.execute(type, getSleep_time(), getAwoke_time());
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getSleep_time() {
        return sleep_time;
    }

    public void setSleep_time(String sleep_time) {
        this.sleep_time = sleep_time;
    }

    public String getAwoke_time() {
        return awoke_time;
    }

    public void setAwoke_time(String awoke_time) {
        this.awoke_time = awoke_time;
    }
}

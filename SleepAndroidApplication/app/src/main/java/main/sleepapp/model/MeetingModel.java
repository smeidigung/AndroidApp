package main.sleepapp.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import main.sleepapp.controller.ConsentController;
import main.sleepapp.controller.DatabaseController;
import main.sleepapp.controller.MeetingController;

public class MeetingModel{

    private String meetingLocation;
    private Date meetingTime;
    private String participatingStudent;
    private String participatingCoordinator;

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    public Date getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }

    public boolean checkModel(StudentModel studentModel){
        setParticipatingStudent(studentModel.getStudent_id());
        String type = "loadMeeting";
        DatabaseController dbController = new DatabaseController();
        try {
            String result = dbController.execute(type, studentModel.getStudent_id()).get();
            List<String> items = Arrays.asList(result.split("\\s*,\\s*"));
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(int i = 0; i < items.size(); i = i + 4) {
                try {
                    setMeetingTime(sdfDate.parse(items.get(i)));
                    setMeetingLocation(items.get(i+3));
                } catch (ParseException e) {
                    return false;
                }
            }

            return !result.isEmpty();
        } catch (ExecutionException e) {

        } catch (InterruptedException e) {

        }

         return false;
    }
    public void loadModel(StudentModel studentModel){
        setParticipatingStudent(studentModel.getStudent_id());
        String type = "loadMeeting";
        DatabaseController dbController = new DatabaseController();
        try {
            String result = dbController.execute(type, studentModel.getStudent_id()).get();
            List<String> items = Arrays.asList(result.split("\\s*,\\s*"));
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(int i = 0; i < items.size(); i = i + 4) {
                try {
                    setMeetingTime(sdfDate.parse(items.get(i)));
                    setParticipatingCoordinator(items.get(i+2));
                    setMeetingLocation(items.get(i+3));
                } catch (ParseException e) {
                }
            }
        } catch (ExecutionException e) {

        } catch (InterruptedException e) {

        }
    }

    public void updateModel(String... params){
        String type = params[0];
        String currentDate = params[1];
        String studentID = params[2];
        DatabaseController databaseController = new DatabaseController();
        databaseController.execute(type,currentDate,studentID,"0","Ukendt"); //TODO: Ændr ukendt til user_id
    }

    public String getParticipatingStudent() {
        return participatingStudent;
    }

    public void setParticipatingStudent(String participatingStudent) {
        this.participatingStudent = participatingStudent;
    }

    public String getParticipatingCoordinator() {
        return participatingCoordinator;
    }

    public void setParticipatingCoordinator(String participatingCoordinator) {
        this.participatingCoordinator = participatingCoordinator;
    }
}

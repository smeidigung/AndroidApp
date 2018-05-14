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
    private StudentModel participatingStudent;
    private UserModel participatingCoordinator;

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

    public StudentModel getParticipatingStudent() {
        return participatingStudent;
    }

    public void setParticipatingStudent(StudentModel participatingStudent) {
        this.participatingStudent = participatingStudent;
    }

    public UserModel getParticipatingCoordinator() {
        return participatingCoordinator;
    }

    public void setParticipatingCoordinator(UserModel participatingCoordinator) {
        this.participatingCoordinator = participatingCoordinator;
    }

    public boolean checkModel(StudentModel studentModel){
        setParticipatingStudent(studentModel);
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
        setParticipatingStudent(studentModel);
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

                }
            }


        } catch (ExecutionException e) {

        } catch (InterruptedException e) {

        }
    }
}

package main.sleepapp.model;

import java.util.Date;

public class MeetingModel {

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
}

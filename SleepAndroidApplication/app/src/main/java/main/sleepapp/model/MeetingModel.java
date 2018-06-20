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

/**
 * Denne model beskriver et møde, beskrevet ved attributterne mødetid, mødested, deltagende elev og
 * deltagende sundhedskoordinator.
 */
public class MeetingModel{

    private String meetingLocation;
    private Date meetingTime;
    private String participatingStudent;
    private String participatingCoordinator;

    /**
     * Returnerer attributten meetingLocation af typen String.
     */
    public String getMeetingLocation() {
        return meetingLocation;
    }

    /**
     * Sætter attributten meetingLocation af typen String til inputargumentet meetingLocation
     */
    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    /**
     * Returnerer attributten meetingTime af typen Date.
     */
    public Date getMeetingTime() {
        return meetingTime;
    }

    /**
     * Sætter attributten meetingTime af typen Date til inputargumentet meetingTime
     */
    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }

    /**
     * Metoden tjekker, om der findes et møde med en bestemt elev, og bruges når det skal
     * undersøges, om en elev allerede har et møde eller ej.
     * Modellen opdateres med elevID'et, informationer om et møde med netop det elevID, hvilket
     * hentes fra databasen.
     * Hvis der findes et møde, returneres med true - ellers returneres med false.
     */
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

    /**
     * Håndterer hentning af data fra databasen om et møde med en bestemt elev.
     * Modellen opdateres med et elevID, informationerne om et møde med netop dette elevID,
     * hvilke hentes fra databasen og udfyldes i modellen.
     */
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

    /**
     * Håndterer opdateringen af data i databasen med data fra denne model.
     */
    public void updateModel(String... params){
        String type = params[0];
        String currentDate = params[1];
        String studentID = params[2];
        DatabaseController databaseController = new DatabaseController();
        databaseController.execute(type,currentDate,studentID,"0","Ukendt");
    }

    public String getParticipatingStudent() {
        return participatingStudent;
    }

    /**
     * Sætter attributten meetingLocation af typen String til inputargumentet meetingLocation
     */
    public void setParticipatingStudent(String participatingStudent) {
        this.participatingStudent = participatingStudent;
    }

    /**
     * Returnerer attributten meetingLocation af typen String.
     */
    public String getParticipatingCoordinator() {
        return participatingCoordinator;
    }

    /**
     * Sætter attributten meetingLocation af typen String til inputargumentet meetingLocation
     */
    public void setParticipatingCoordinator(String participatingCoordinator) {
        this.participatingCoordinator = participatingCoordinator;
    }
}

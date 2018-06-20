package main.sleepapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import main.sleepapp.controller.DatabaseController;

/**
 * Denne model beskriver en SK, beskrevet ved attributterne navn, brugerID, og kodeord.
 */
public class UserModel {

    private String name;
    private String userID;
    private String password;

    /**
     * Returnere attributten name af typen String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sætter attributten name af typen String til input argumentet name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returnere attributten userID af typen String.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sætter attributten userID af typen String til input argumentet userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Returnere attributten password af typen String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sætter attributten password af typen String til input argumentet password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Håndtere hentning af data fra databasen en bestemt SK.
     * Modellen opdateres med et navn.
     */
    public void loadModel(String userID) {
        String type = "loadUserModel";
        DatabaseController dbController = new DatabaseController();
        try {
            setUserID(userID);
            String result = dbController.execute(type, getUserID()).get();
            List<String> items = Arrays.asList(result.split("\\s*,\\s*"));
            setName(items.get(0) +" "+ items.get(1));
        } catch (ExecutionException e) {

        } catch (InterruptedException e) {

        }
    }
}

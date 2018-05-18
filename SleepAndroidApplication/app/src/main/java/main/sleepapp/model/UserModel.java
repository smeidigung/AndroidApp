package main.sleepapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import main.sleepapp.controller.DatabaseController;

public class UserModel {

    private String name;
    private String userID;
    private String password;

    public UserModel(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

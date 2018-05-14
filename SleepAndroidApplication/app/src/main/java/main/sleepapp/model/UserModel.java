package main.sleepapp.model;

public class UserModel {

    private String name;
    private String userID;
    private String password;

    public UserModel(){
        this.setName("Ukendt");
        this.setUserID("Ukendt");
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
}

package main.sleepapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.concurrent.ExecutionException;

import main.sleepapp.controller.DatabaseController;
import main.sleepapp.controller.LoginController;

public class StudentModel implements Parcelable {

    private String student_id;
    private String password;
    private boolean consent;

    public StudentModel () {
    }

    public StudentModel (Parcel in) {
        this.student_id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(student_id);
    }

    public static final Parcelable.Creator<StudentModel> CREATOR = new Parcelable.Creator<StudentModel>(){
        public StudentModel createFromParcel(Parcel in) {
            return new StudentModel(in);
        }

        public StudentModel[] newArray(int size) {
            return new StudentModel[size];
        }
    };

    public void setStudent_id (String string) {
        this.student_id = string;
    }

    public String getStudent_id (){
        return(this.student_id);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String checkModel(){
        String type = "login";
        DatabaseController databaseController = new DatabaseController();

        try {
            return databaseController.execute(type, getStudent_id(), getPassword()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String updateModel(String... params){

        String type = params[0];
        if(type.equals("register")) {
            String password = params[1];
            String firstname = params[2];
            String surname = params[3];

            DatabaseController databaseController = new DatabaseController();
            try {
                return databaseController.execute(type, password, firstname, surname).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void updateModel(String type, Boolean consent){

        if (type.equals("consent")) {
            this.setConsent(consent);
            String str;
            if(consent) {
                str = "1";
            } else {
                str = "0";
            }
            DatabaseController dbController = new DatabaseController();
            dbController.execute(type, str);
        }
    }

    public boolean isConsent() {
        return consent;
    }

    public void setConsent(boolean consent) {
        this.consent = consent;
    }
}

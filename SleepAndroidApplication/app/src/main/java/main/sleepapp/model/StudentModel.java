package main.sleepapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import main.sleepapp.controller.DatabaseController;
import main.sleepapp.controller.LoginController;

public class StudentModel implements Parcelable {

    private String student_id;
    private String password;

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

    public void checkModel(DatabaseController.AsyncResponse controller){
        String type = "login";
        DatabaseController databaseController = new DatabaseController(controller);
        databaseController.execute(type, getStudent_id(), getPassword());
    }
}

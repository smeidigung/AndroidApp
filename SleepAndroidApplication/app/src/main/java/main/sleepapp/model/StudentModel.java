package main.sleepapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import android.util.Pair;
import java.util.concurrent.ExecutionException;

import main.sleepapp.controller.DatabaseController;
import main.sleepapp.controller.LoginController;

/**
 *  StudentModel is a model describing a student user of the system. It implements the interface
 *  Parcelable to enable the object to be exchanged between Android Activities.
 */
public class StudentModel implements Parcelable {

    private String student_id;
    private String password;
    private String consent;

    /**
     * The default constructor to be called when the class is instantiated. This should be used,
     * only when the calling object doesn't need it to be created from a Parcel.
     */
    public StudentModel () {

    }

    /**
     * The alternate constructor to be called if the model is to be created from a Parcel.
     * I takes a Parcel as argument.
     * @param in    The parcel to be recreated from.
     */
    public StudentModel (Parcel in) {
        this.student_id = in.readString();
        this.consent = in.readString();
    }

    /**
     * Loads the StudentModel with data from the database. The data contains information about the
     * consent status of the user.
     */
    public void loadModel(){
        String type = "getConsent";
        DatabaseController databaseController = new DatabaseController();

        try {
            String string = databaseController.execute(type, getStudent_id()).get();
            if(string.equals("0")) {
                setConsent("0");
            } else if (string.equals("1")){
                setConsent("1");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates the login credentials, by calling a specific database operation, that looks for
     * a user with the given usernam and password, then returns a String containing information
     * about the validity of the username and password combination.
     * @return  The String containing information about the validity of the username and password
     * combination.
     */
    public String validateID(){
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

    /**
     * Updates the StudentModel with new information, read from the argument params. Then calls a
     * specific database operation to update the database. This specific method is used for register
     * a new user.
     * @param params    A String array containing userdata to be inserted into the StudentModel and
     *                  i the database.
     * @return          A string containing information whether the insertion into the database was
     *                  successful or not.
     */
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

    /**
     * Updates the StudentModel with new information, read from the argument params. Then calls a
     * specific database operation to update the database. This specific method is used for updating
     * the consent status of the user.
     * @param type      The type of database operation to be used.
     * @param consent   A String containing a "1" for true or "0" for false.
     */
    public void updateModel(String type, String consent){

        if (type.equals("consent")) {
            this.setConsent(consent);
            DatabaseController dbController = new DatabaseController();
            dbController.execute(type, getStudent_id(), consent);
        }
    }

    /**
     *  This is used when creating a object from a Parcel, and is only used if a Parcelable class
     *  has any subclasses or children.
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Is used when deconstructing the object into a Parcel, by reading the attributes and writing
     * said information into a Parcel.
     * @param dest  The final Parcel containing the deconstructed object.
     * @param flags Is used to set if the final Parcel should be return or not.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(student_id);
        dest.writeString(consent);
    }

    /**
     *  A interface to be implemented when using the Parcelable interface.
     */
    public static final Parcelable.Creator<StudentModel> CREATOR = new Parcelable.Creator<StudentModel>(){
        public StudentModel createFromParcel(Parcel in) {
            return new StudentModel(in);
        }

        public StudentModel[] newArray(int size) {
            return new StudentModel[size];
        }
    };

    /**
     * Returns the string value of Consent.
     * @return  The string value of Consent.
     */
    public String getConsent() {
        return consent;
    }

    /** Set the string value of Consent.
     * @param consent   The string value to be set.
     */
    public void setConsent(String consent) {
        this.consent = consent;
    }

    /**
     * Returns the string value of student_id.
     * @return The string value of student_id.
     */
    public String getStudent_id (){
        return(this.student_id);
    }

    /**
     * Sets the string value of student_id
     * @param string    The student_id to be set.
     */
    public void setStudent_id (String string) {
        this.student_id = string;
    }

    /**
     * Returns the string value of Password.
     * @return The string value of Password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the string value of password.
     * @param password The string value to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

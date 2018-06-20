package main.sleepapp.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

import java.util.List;

import main.sleepapp.model.MeetingModel;
import main.sleepapp.model.SleepModel;
import main.sleepapp.model.StudentModel;
import main.sleepapp.util.SleepModelList;

/**
 * AssessmentController takes a StudentModel and handles whether or not an alert should be given to
 * the user. It contains methods for, checking whether or not a user has given consent about
 * sharing its user data and producing an alertDialog.
 */
public class AssessmentController {

    private Context context;
    private StudentModel studentModel;
    private MeetingModel meetingModel;
    private SleepModelList sleepModelList;

    /**
     * This is the constructor for the class, and takes the input arguments StudentModel and a
     * Context.
     * Using the StudentModel, it calls for a "has given consent"-check, and uses Context to inform
     * where to display the alertDialog.
     *
     * @param studentModel The StudentModel to be used for when checking for consent and what data
     *                     to use.
     * @param context      The Context where the AlertDialog should be displayed.
     */
    public AssessmentController(StudentModel studentModel, Context context) {
        this.context = context;
        this.setStudentModel(studentModel);
        this.setSleepModelList(new SleepModelList(studentModel));
        if (checkConsent()) {
            thresholdNotifier(sleepModelList.sleepModelList);
        } else {
            goToConsent();
        }
    }

    /**
     * Is used for displaying the "give consent"-screen, given that the user hasn't already given
     * consent.
     */
    private void goToConsent() {
        Intent intent = new Intent(context, ConsentController.class);
        intent.putExtra("studentModel", studentModel);
        context.startActivity(intent);
    }

    /**
     * Is used for checking whether or not the user has given consent, by checking the consent value
     * in StudentModel.
     *
     * @return The boolean value of whether or not the user has given consent
     */
    private boolean checkConsent() {
        try {
            if (this.studentModel.getConsent().equals("1")) {
                return true;
            } else {
                return false;
            }

        } catch (NullPointerException e) {
            return false;
        }

    }

    /**
     * Used to check if the average sleep time is less than 8 hours.
     *
     * @param sleepModelList The list of sleepModel's to be checked.
     */
    private void thresholdNotifier(List<SleepModel> sleepModelList) {
        Long diff = this.sleepModelList.diffSleepTime(sleepModelList);
        if (diff < 4800000L * this.sleepModelList.nightsSlept) {
            System.out.println(diff);
            alertStudent();
        }
    }

    /**
     * Creates a AlertDialog if the user hasn't already arranged a meeting with a HC.
     * The user is then display with a choice of "Yes, arrange a meeting, please." or "No, thanks"
     * The positive answer creates a meetingController activity.
     * The negative answer simply closes the dialog, and the user is asked again after next sleep.
     */
    private void alertStudent() {
        this.setMeetingModel(new MeetingModel());
        if (!(meetingModel.checkModel(studentModel))) {

            final AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(context);
            }
            builder.setTitle("Møde")
                    .setMessage("Du sover for lidt. Vil du have et møde?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            if (!meetingModel.checkModel(studentModel)) {
                                Intent intent = new Intent(context, MeetingController.class);
                                intent.putExtra("studentModel", studentModel);
                                context.startActivity(intent);
                            }

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    /**
     * Sets the StudentModel
     *
     * @param studentModel The StudentModel to be set.
     */
    public void setStudentModel(StudentModel studentModel) {
        this.studentModel = studentModel;
    }

    /**
     * Sets the MeetingMode
     *
     * @param meetingModel The meeting model to be set.
     */
    public void setMeetingModel(MeetingModel meetingModel) {
        this.meetingModel = meetingModel;
    }

    /**
     * Set the SleepModelList.
     *
     * @param sleepModelList The SleepModelList to be set.
     */
    public void setSleepModelList(SleepModelList sleepModelList) {
        this.sleepModelList = sleepModelList;
    }
}

package main.sleepapp.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import main.sleepapp.R;
import main.sleepapp.model.StudentModel;

/**
 * Håndtere login og registrering af eleven.
 */
public class LoginController extends AppCompatActivity {

    private EditText mUserID,mPassword,rPassword,rUserName;
    private StudentModel studentModel;

    /**
     * Default metoden som kaldes når en activity oprettes.
     * Den sætter det viewet til at være loginView, og opretter de to knapper som skal bruges.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginview);
        mUserID = (EditText) findViewById(R.id.userID);
        mPassword = (EditText) findViewById(R.id.password);

        studentModel = new StudentModel();

        Button loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });
        Button goToRegister = (Button) findViewById(R.id.btn_gotoregister);
        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleGoToRegister();
            }
        });
    }

    /**
     * Håndtere en registrering af en elev, og aktiveres af knappen registrer.
     * Første hentes den indskrevende navn og kodeord, og navn inddeles i fornavn og efternavn.
     * Så registeres brugeren med oplysningerne med metoden updateModel, i klassen StudentModel.
     * Afslutningvis oprettes en pop-up besked med det autogenererede brugernavn og præsenteres for
     * brugeren, og brugeres sendes til login-siden.
     */
    private void handleRegister() {
        String password = rPassword.getText().toString();
        String name = rUserName.getText().toString();
        String firstname, surname;

        if (name.contains(" ")) {
            firstname = name.substring(0,name.indexOf(" "));
            surname = name.substring(name.indexOf(" ")+1);
        } else {
            firstname = name;
            surname = "ukendt";
        }
        studentModel = new StudentModel();
        String string = studentModel.updateModel("register",password,firstname,surname);

        if (string.startsWith("Insert successful: Student_id")) {
            String registerSubString = "Insert successful: Student_id = ";

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder .setMessage("Brugernavn: " + string.replace(registerSubString, ""))
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            handleGoToLogin();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Håndtere et login og er aktiveret af knappen login.
     * Først hentes det indskrevende brugernavn og kodeord, og validiteten af kombinationen tjekkes
     * ved metoden validateID af klassen StudentModel. Hvis brugernavnet godkendes, oprettes en ny
     * MainController og den nye StudentModel gives videre.
     */
    private void handleLogin(){
        studentModel.setStudent_id(mUserID.getText().toString());
        studentModel.setPassword(mPassword.getText().toString());
        String string = validateID(studentModel.getStudent_id(),studentModel.getPassword());

        if (string.equalsIgnoreCase("login success")) {
            Intent intent = new Intent(this,MainController.class);
            this.studentModel.setStudent_id(mUserID.getText().toString());
            studentModel.loadModel();
            intent.putExtra("studentModel",this.studentModel);
            this.startActivity(intent);
        }
    }

    /**
     * Validere kombinationen af input argumentet studentID og studentPassword, ved at kalde
     * Studentmodel's metoden validateID, og returnere svaret herfra.
     */
    private String validateID(String studentID, String studentPassword){
        String string = studentModel.validateID();
        return string;
    }

    /**
     * Håndtere skiftet af view, fra login til register, og kaldes når der trykkes på knappen
     * register.
     * Sætter viewet til registerView, tekstfelterne til indskrivning af navn og koderord oprettes,
     * og en knap til registereing oprettes.
     */
    private void handleGoToRegister() {
        setContentView(R.layout.registerview);
        rPassword = (EditText) findViewById(R.id.RegisterPassword);
        rUserName = (EditText) findViewById(R.id.RegisterName);
        Button registerButton = (Button) findViewById(R.id.btn_register_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRegister();
            }
        });
    }

    /**
     * Opretter en ny login activity.
     */
    private void handleGoToLogin () {
        Intent Intent = new Intent(this, LoginController.class);
        startActivity(Intent);
    }
}



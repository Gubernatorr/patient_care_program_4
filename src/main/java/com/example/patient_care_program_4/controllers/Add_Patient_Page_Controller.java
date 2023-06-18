package com.example.patient_care_program_4.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.patient_care_program_4.animations.Shake;
import com.example.patient_care_program_4.configs.Database_Dispatcher;
import com.example.patient_care_program_4.models.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Add_Patient_Page_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add_Patient_To_DB_Button;

    @FXML
    private TextField add_patient_address;

    @FXML
    private TextField add_patient_dob;

    @FXML
    private TextField add_patient_first_name;

    @FXML
    private TextField add_patient_gender;

    @FXML
    private TextField add_patient_last_name;

    @FXML
    private TextField add_patient_phone_number;

    @FXML
    private ImageView decorativeImage1;

    @FXML
    private ImageView decorativeImage11;

    @FXML
    private Button go_back_Button;

    @FXML
    void add_patient_to_db_button_pressed(ActionEvent event) throws SQLException, ClassNotFoundException {
        add_patient_to_db();
    }

    @FXML
    void go_back_button_pressed(ActionEvent event) {
        go_back_Button.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/patient_care_program_4/main-page.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void add_patient_to_db() throws SQLException, ClassNotFoundException{

        Database_Dispatcher dbDispatcher = new Database_Dispatcher();

        if(add_patient_first_name.getText() != null && add_patient_last_name.getText() != null && add_patient_dob.getText() != null &&
                add_patient_gender.getText() != null && add_patient_phone_number.getText() != null &&  add_patient_address.getText() != null){

            String firstName = add_patient_first_name.getText();
            String lastName = add_patient_last_name.getText();
            String dob = add_patient_dob.getText();
            String gender = add_patient_gender.getText();
            String phone_number = add_patient_phone_number.getText();
            String address = add_patient_address.getText();

            System.out.println(firstName);

            Patient patient = new Patient(firstName, lastName, dob, gender, phone_number, address);
            System.out.println(patient);
            Main_Page_Controller.active_patient = patient;

            dbDispatcher.addPatient(patient);

            add_Patient_To_DB_Button.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/patient_care_program_4/main-page.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            Shake patientFirstNameAnim = new Shake(add_patient_first_name);
            Shake patientLastNameAnim = new Shake(add_patient_last_name);
            Shake patientDob = new Shake(add_patient_dob);
            Shake patientGender = new Shake(add_patient_gender);
            Shake patientPhoneNumber = new Shake(add_patient_phone_number);
            Shake patientAddress = new Shake(add_patient_address);

            patientFirstNameAnim.playAnim();
            patientLastNameAnim.playAnim();
            patientDob.playAnim();
            patientGender.playAnim();
            patientPhoneNumber.playAnim();
            patientAddress.playAnim();
        }

    }

    @FXML
    void initialize() {
        assert decorativeImage1 != null : "fx:id=\"decorativeImage1\" was not injected: check your FXML file 'add-patient-page.fxml'.";
        assert decorativeImage11 != null : "fx:id=\"decorativeImage11\" was not injected: check your FXML file 'add-patient-page.fxml'.";


    }

}

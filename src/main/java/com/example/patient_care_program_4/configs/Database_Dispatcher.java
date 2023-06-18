package com.example.patient_care_program_4.configs;

import com.example.patient_care_program_4.animations.Shake;
import com.example.patient_care_program_4.models.Patient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Database_Dispatcher extends Configs{

    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {

        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort +"/" +dbName + "?" + "autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void addPatient(Patient patient){
        String insert = "INSERT INTO " + Const.PATIENT_TABLE + " (" + Const.PATIENT_FIRSTNAME + "," + Const.PATIENT_LASTNAME +
                "," + Const.PATIENT_DOB + "," + Const.PATIENT_GENDER + "," + Const.PATIENT_PHONE_NUMBER + "," + Const.PATIENT_ADDRESS +
                ")" + "VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getLastName());
            preparedStatement.setString(3, patient.getDob());
            preparedStatement.setString(4, patient.getGender());
            preparedStatement.setString(5, patient.getPhoneNumber());
            preparedStatement.setString(6, patient.getAddress());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addMedicalRecords(Patient patient){
        String insert = "INSERT INTO " + Const.PATIENT_TABLE + " (" + Const.PATIENT_FIRSTNAME + "," + Const.PATIENT_LASTNAME +
                "," + Const.PATIENT_DOB + "," + Const.PATIENT_GENDER + "," + Const.PATIENT_PHONE_NUMBER + "," + Const.PATIENT_ADDRESS + ","
                + Const.PATIENT_MEDICAL_RECORDS + ")" + "VALUES(?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getLastName());
            preparedStatement.setString(3, patient.getDob());
            preparedStatement.setString(4, patient.getGender());
            preparedStatement.setString(5, patient.getPhoneNumber());
            preparedStatement.setString(7, patient.getAddress());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}

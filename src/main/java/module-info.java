module com.example.patient_care_program_4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.patient_care_program_4 to javafx.fxml;
    exports com.example.patient_care_program_4;
    exports com.example.patient_care_program_4.controllers;
    opens com.example.patient_care_program_4.controllers to javafx.fxml;
    exports com.example.patient_care_program_4.animations;
    opens com.example.patient_care_program_4.animations to javafx.fxml;
}
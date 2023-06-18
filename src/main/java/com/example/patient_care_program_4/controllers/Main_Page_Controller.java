package com.example.patient_care_program_4.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.example.patient_care_program_4.animations.Shake;
import com.example.patient_care_program_4.configs.Const;
import com.example.patient_care_program_4.configs.Database_Dispatcher;
import com.example.patient_care_program_4.models.Patient;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main_Page_Controller {

    static Patient active_patient;

    private ObservableList<Patient> patientsData = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add_Patient_Button;

    @FXML
    private ChoiceBox<String> choiseBox;

    @FXML
    private Button delete_patient_from_db;

    @FXML
    private Button save_patient_medical_record_button;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button show_info_button;

    @FXML
    private Button generate_table_from_db_button;

    @FXML
    private TableColumn<Patient, String> table_column_id;

    @FXML
    private TableColumn<Patient, String> table_column_last_name;

    @FXML
    private TableColumn<Patient, String> table_column_name;

    @FXML
    private TableColumn<Patient, String> table_column_phone_number;

    @FXML
    private TableView<Patient> table_of_patients;

    @FXML
    private Text text_address;

    @FXML
    private Text text_dob;

    @FXML
    private Text text_gender;

    @FXML
    private Text text_id;

    @FXML
    private Text text_last_name;

    @FXML
    private Text text_name;

    @FXML
    private Text text_phone;

    @FXML
    private TextArea medical_records_field;

    @FXML
    void add_patient_button_pressed(ActionEvent event) {
        add_Patient_Button.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/patient_care_program_4/add-patient-page.fxml"));

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

    @FXML
    void save_patient_medical_record_button_pressed(ActionEvent event) throws SQLException, ClassNotFoundException {
        update(Const.PATIENT_MEDICAL_RECORDS, medical_records_field);
    }

    @FXML
    void searchButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException {
        table_of_patients.getItems().clear();
        initData();

        table_column_id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdpatient()));
        table_column_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        table_column_last_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        table_column_phone_number.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));

        List<Patient> copy = new ArrayList<>();
        TableView<Patient> coppy = table_of_patients;
        copy.addAll(searchList(searchField.getText(), patientsData));
        coppy.getItems().clear();
        coppy.getItems().addAll(copy);

        table_of_patients = coppy;

    }

    private List<Patient> searchList(String searchingWords, List<Patient> patientList){
        List<String> searchWordsArray = Arrays.asList(searchingWords.trim().split(" "));

        switch (choiseBox.getValue()){
            case "Sort by Id" : patientList = patientList.stream().filter(input -> searchWordsArray.stream().allMatch(word
                    -> input.getIdpatient().toLowerCase().contains(word.toLowerCase()))).collect(Collectors.toList());break;
            case "Sort by First Name" : patientList = patientList.stream().filter(input -> searchWordsArray.stream().allMatch(word
                    -> input.getFirstName().toLowerCase().contains(word.toLowerCase()))).collect(Collectors.toList()); break;
            case "Sort by Last Name" : patientList = patientList.stream().filter(input -> searchWordsArray.stream().allMatch(word
                    -> input.getLastName().toLowerCase().contains(word.toLowerCase()))).collect(Collectors.toList()); break;
            case "Sort by Login" : patientList = patientList.stream().filter(input -> searchWordsArray.stream().allMatch(word
                    -> input.getPhoneNumber().toLowerCase().contains(word.toLowerCase()))).collect(Collectors.toList()); break;
            default: System.out.println("WRONG SWITCH CASE");
        }
        return patientList;
    }

    @FXML
    void show_info_button_pressed(ActionEvent event) throws SQLException, ClassNotFoundException {

        if(!table_of_patients.getSelectionModel().isEmpty()){
            show_selected_patient_info();
        }

    }

    public void getStringsForChoiseBox(ActionEvent event){
        String myChoiseBoxStrings = choiseBox.getValue();
    }

    public void show_selected_patient_info() throws SQLException, ClassNotFoundException {

        String index = table_of_patients.getItems().get(table_of_patients.getSelectionModel().getSelectedIndex()).getIdpatient();

        Patient selectedPatient = getPatientById(index);
        active_patient = selectedPatient;

        text_id.setText(active_patient.getIdpatient());
        text_name.setText(active_patient.getFirstName());
        text_last_name.setText(active_patient.getLastName());
        text_dob.setText(active_patient.getDob());
        text_id.setText(active_patient.getIdpatient());
        text_gender.setText(active_patient.getGender());
        text_phone.setText(active_patient.getPhoneNumber());
        text_address.setText(active_patient.getAddress());
        medical_records_field.setText(active_patient.getMedical_records());

    }
    @FXML
    void generate_table_from_db_button_pressed(ActionEvent event) throws SQLException, ClassNotFoundException {
        generateTableOfPatients();
    }

    @FXML
    void generateTableOfPatientsButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException {
        generateTableOfPatients();
    }

    @FXML
    void update_patient_info_button_pressed(ActionEvent event) {
        modalWindow();
    }
    @FXML
    private Button update_patient_info_button;

    private void modalWindow() {
        Stage window = new Stage();
        window.setTitle("Modal Window");

        window.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox(10);

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField dobField = new TextField();
        TextField genderField = new TextField();
        TextField phoneNumberField = new TextField();
        TextField addressField = new TextField();

        firstNameField.setText(text_name.getText());
        lastNameField.setText(text_last_name.getText());
        dobField.setText(text_dob.getText());
        genderField.setText(text_gender.getText());
        phoneNumberField.setText(text_phone.getText());
        addressField.setText(text_address.getText());

        vbox.getChildren().addAll(
                new Label("First Name"), firstNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Date Of Birth"), dobField,
                new Label("Gender"), genderField,
                new Label("Phone Number"), phoneNumberField,
                new Label("Address"), addressField
        );

        HBox buttonBox = new HBox(2);
        Button closeButton = new Button("Close");
        Button saveButton = new Button("Save Changes");
        buttonBox.getChildren().addAll(closeButton, saveButton);

        closeButton.setLayoutX(125);
        closeButton.setLayoutY(100);

        saveButton.setLayoutX(225);
        saveButton.setLayoutY(100);

        closeButton.setOnAction(event -> window.close());

        saveButton.setOnAction(event -> {
            if(firstNameField.getText() != null && lastNameField.getText() != null && dobField.getText() != null &&
                    genderField.getText() != null && phoneNumberField.getText() != null &&  addressField.getText() != null){

                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String dob = dobField.getText();
                String gender = genderField.getText();
                String phoneNumber = phoneNumberField.getText();
                String address = addressField.getText();

                List<String> data = new ArrayList<>();
                data.add(firstName); data.add(lastName); data.add(dob); data.add(gender); data.add(phoneNumber); data.add(address);

                try {
                    fullUpdate(data);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                active_patient.setFirstName(firstName);
                active_patient.setLastName(lastName);
                active_patient.setDob(dob);
                active_patient.setGender(gender);
                active_patient.setPhoneNumber(phoneNumber);
                active_patient.setAddress(address);

                text_id.setText(active_patient.getIdpatient());
                text_name.setText(active_patient.getFirstName());
                text_last_name.setText(active_patient.getLastName());
                text_dob.setText(active_patient.getDob());
                text_id.setText(active_patient.getIdpatient());
                text_gender.setText(active_patient.getGender());
                text_phone.setText(active_patient.getPhoneNumber());
                text_address.setText(active_patient.getAddress());
                medical_records_field.setText(active_patient.getMedical_records());

                try {
                    generateTableOfPatients();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                window.close();
            } else {
                Shake patientFirstNameAnim = new Shake(firstNameField);
                Shake patientLastNameAnim = new Shake(lastNameField);
                Shake patientDob = new Shake(dobField);
                Shake patientGender = new Shake(genderField);
                Shake patientPhoneNumber = new Shake(phoneNumberField);
                Shake patientAddress = new Shake(addressField);

                patientFirstNameAnim.playAnim();
                patientLastNameAnim.playAnim();
                patientDob.playAnim();
                patientGender.playAnim();
                patientPhoneNumber.playAnim();
                patientAddress.playAnim();
            }

        });

        vbox.getChildren().addAll(buttonBox);
        buttonBox.setSpacing(50);
        buttonBox.setPadding(new Insets(15, 0, 0, 70));

        Scene scene = new Scene(vbox, 325, 450);
        window.setScene(scene);
        window.showAndWait();
    }

    void fullUpdate(List<String> data) throws SQLException, ClassNotFoundException {
        Database_Dispatcher db = new Database_Dispatcher();

        String update = "UPDATE " + Const.PATIENT_TABLE + " SET " + Const.PATIENT_FIRSTNAME + "=?, " + Const.PATIENT_LASTNAME
                + "=?, " + Const.PATIENT_DOB + "=?, " + Const.PATIENT_GENDER + "=?, " + Const.PATIENT_PHONE_NUMBER + "=?, " +
                Const.PATIENT_ADDRESS + "=? WHERE " + Const.PATIENT_ID + "= " + active_patient.getIdpatient();

        PreparedStatement preparedStatement = db.getDbConnection().prepareStatement(update);
        preparedStatement.setString(1, data.get(0));
        preparedStatement.setString(2, data.get(1));
        preparedStatement.setString(3, data.get(2));
        preparedStatement.setString(4, data.get(3));
        preparedStatement.setString(5, data.get(4));
        preparedStatement.setString(6, data.get(5));

        preparedStatement.executeUpdate();
    }

    private void generateTableOfPatients() throws SQLException, ClassNotFoundException {
        table_of_patients.getItems().clear();
        initData();

        table_column_id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdpatient()));
        table_column_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        table_column_last_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        table_column_phone_number.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));

        table_of_patients.setItems(patientsData);
    }

    private void initData() throws SQLException, ClassNotFoundException {
        List<Patient> patientList = createListFromDB();
        for(Patient p : patientList){
            patientsData.add(p);
        }
    }

    public List<Patient> createListFromDB() throws SQLException, ClassNotFoundException {
        Database_Dispatcher db = new Database_Dispatcher();
        String select = "SELECT * FROM " + Const.PATIENT_TABLE;

        Statement pstmnt = db.getDbConnection().createStatement();
        ResultSet rs = pstmnt.executeQuery(select);
        List<Patient> list = new ArrayList<>();
        while (rs.next()) {
            Patient patient = new Patient();
            patient.setIdpatient(rs.getString(1));
            patient.setFirstName(rs.getString(2));
            patient.setLastName(rs.getString(3));
            patient.setPhoneNumber(rs.getString(6));
            list.add(patient);
        }
        pstmnt.close();
        return list;
    }

    public Patient getFirstPatientFromDB() throws SQLException, ClassNotFoundException {
        Database_Dispatcher db = new Database_Dispatcher();
        String select = "SELECT * FROM " + Const.PATIENT_TABLE + " LIMIT 1";

        Statement pstmnt = db.getDbConnection().createStatement();
        ResultSet rs = pstmnt.executeQuery(select);
        if (rs.next()) {
            Patient patient = new Patient();
            patient.setIdpatient(rs.getString("idpatient"));
            patient.setFirstName(rs.getString("firstName"));
            patient.setLastName(rs.getString("lastName"));
            patient.setDob(rs.getString("dob"));
            patient.setGender(rs.getString("gender"));
            patient.setPhoneNumber(rs.getString("phoneNumber"));
            patient.setAddress(rs.getString("address"));
            patient.setMedical_records(rs.getString("medicalRecords"));
            return patient;
        }
        return null;
    }

    void update(String constant, TextArea textArea) throws SQLException, ClassNotFoundException {
        Database_Dispatcher db = new Database_Dispatcher();

        String update = "UPDATE " + Const.PATIENT_TABLE + " SET " + constant +
                "=? WHERE " + Const.PATIENT_ID + "= " + active_patient.getIdpatient();

        PreparedStatement preparedStatement = db.getDbConnection().prepareStatement(update);
        preparedStatement.setString(1, textArea.getText());

        preparedStatement.executeUpdate();
    }

    public Patient getPatientById(String patientId) throws SQLException, ClassNotFoundException {
        Database_Dispatcher db = new Database_Dispatcher();
        String selectQuery = "SELECT * FROM " + Const.PATIENT_TABLE + " WHERE " + Const.PATIENT_ID + " = ?";

        PreparedStatement preparedStatement = db.getDbConnection().prepareStatement(selectQuery);
        preparedStatement.setString(1, patientId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Patient patient = new Patient();
            patient.setIdpatient(resultSet.getString(Const.PATIENT_ID));
            patient.setFirstName(resultSet.getString(Const.PATIENT_FIRSTNAME));
            patient.setLastName(resultSet.getString(Const.PATIENT_LASTNAME));
            patient.setDob(resultSet.getString(Const.PATIENT_DOB));
            patient.setGender(resultSet.getString(Const.PATIENT_GENDER));
            patient.setPhoneNumber(resultSet.getString(Const.PATIENT_PHONE_NUMBER));
            patient.setAddress(resultSet.getString(Const.PATIENT_ADDRESS));
            patient.setMedical_records(resultSet.getString(Const.PATIENT_MEDICAL_RECORDS));

            return patient;
        }

        return null;
    }


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        medical_records_field.setWrapText(true);
        active_patient = getFirstPatientFromDB();
        generateTableOfPatients();

        text_id.setText(active_patient.getIdpatient());
        text_name.setText(active_patient.getFirstName());
        text_last_name.setText(active_patient.getLastName());
        text_dob.setText(active_patient.getDob());
        text_id.setText(active_patient.getIdpatient());
        text_gender.setText(active_patient.getGender());
        text_phone.setText(active_patient.getPhoneNumber());
        text_address.setText(active_patient.getAddress());
        medical_records_field.setText(active_patient.getMedical_records());

        table_of_patients.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                try {
                    show_selected_patient_info();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        String[] choiseBoxStrings = {"Sort by Id", "Sort by First Name", "Sort by Last Name", "Sort by Phone"};
        choiseBox.getItems().addAll(choiseBoxStrings);
        choiseBox.setValue(choiseBoxStrings[0]);
        choiseBox.setOnAction(this::getStringsForChoiseBox);

    }

}




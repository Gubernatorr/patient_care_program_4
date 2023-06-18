package com.example.patient_care_program_4.models;

import java.util.Objects;

public class Patient {

    private String idpatient;
    private String firstName;
    private String lastName;
    private String dob;
    private String gender;
    private String phoneNumber;
    private String address;
    private String medical_records;

    public Patient(String idpatient, String firstName, String lastName, String dob, String gender, String phoneNumber, String address, String medical_records) {
        this.idpatient = idpatient;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.medical_records = medical_records;
    }

    public Patient(String idpatient, String firstName, String lastName, String phoneNumber) {
        this.idpatient = idpatient;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public Patient(String firstName, String lastName, String dob, String gender, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }


    public Patient(){}

    public String getIdpatient() {
        return idpatient;
    }

    public void setIdpatient(String idpatient) {
        this.idpatient = idpatient;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMedical_records() {
        return medical_records;
    }

    public void setMedical_records(String medical_records) {
        this.medical_records = medical_records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(idpatient, patient.idpatient) && Objects.equals(firstName, patient.firstName) && Objects.equals(lastName, patient.lastName) && Objects.equals(dob, patient.dob) && Objects.equals(gender, patient.gender) && Objects.equals(phoneNumber, patient.phoneNumber) && Objects.equals(address, patient.address) && Objects.equals(medical_records, patient.medical_records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idpatient, firstName, lastName, dob, gender, phoneNumber, address, medical_records);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + idpatient + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", medical_records='" + medical_records + '\'' +
                '}';
    }
}

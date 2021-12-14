package org.but.feec.airport.api;

import java.util.Arrays;

public class PersonCreateView {

    private String contactValue;
    private String givenName;
    private String userName;
    private String familyName;
    private String dateOfBirth;
    private String diet;
    private char[] pwd;

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public char[] getPwd() {
        return pwd;
    }

    public void setPwd(char[] pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "PersonCreateView{" +
                "email='" + contactValue + '\'' +
                ", given_name='" + givenName + '\'' +
                ", username='" + userName + '\'' +
                ", family_name='" + familyName + '\'' +
                ", pwd=" + Arrays.toString(pwd) +
                '}';
    }
}

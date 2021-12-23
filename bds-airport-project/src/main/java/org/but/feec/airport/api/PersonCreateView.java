package org.but.feec.airport.api;

import java.util.Arrays;

public class PersonCreateView {

    private String givenName;
    private String familyName;
    private String dateOfBirth;
    private String diet;
    private String contactValue;
    private String country;
    private String city;
    private String street;
    private String zipCode;
    private String houseNumber;
    private String userName;
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
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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
                "given_name='" + givenName + '\'' +
                ", family_name='" + familyName + '\'' +
                ", date_of_birth='" + dateOfBirth + '\'' +
                ", diet='" + diet + '\'' +
                ", username='" + userName + '\'' +
                ", password=" + Arrays.toString(pwd) +
                ", contact_value='" + contactValue + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zip_code='" + zipCode + '\'' +
                ", house_number='" + houseNumber + '\'' +
                '}';
    }
}

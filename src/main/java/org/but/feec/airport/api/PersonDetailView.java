package org.but.feec.airport.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonDetailView {
    private LongProperty id = new SimpleLongProperty();
    private StringProperty givenName = new SimpleStringProperty();
    private StringProperty familyName = new SimpleStringProperty();
    private StringProperty dateOfBirth = new SimpleStringProperty();
    private StringProperty diet = new SimpleStringProperty();
    private StringProperty contactValue = new SimpleStringProperty();
    private StringProperty country = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty street = new SimpleStringProperty();
    private StringProperty houseNumber = new SimpleStringProperty();
    private StringProperty zipCode = new SimpleStringProperty();

    public Long getId() {
        return idProperty().get();
    }

    public void setId(Long id) {
        this.idProperty().setValue(id);
    }

    public String getDiet() {
        return dietProperty().get();
    }

    public void setDiet(String diet) {
        this.dietProperty().setValue(diet);
    }

    public String getGivenName() {
        return givenNameProperty().get();
    }

    public void setGivenName(String givenName) {
        this.givenNameProperty().setValue(givenName);
    }

    public String getFamilyName() {
        return familyNameProperty().get();
    }

    public void setFamilyName(String familyName) {
        this.familyNameProperty().setValue(familyName);
    }

    public String getDateOfBirth() {
        return dateOfBirthProperty().get();
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirthProperty().set(dateOfBirth);
    }

    public String getContactValue() {
        return contactValueProperty().get();
    }

    public void setContactValue(String contactValue) {
        this.contactValueProperty().setValue(contactValue);
    }

    public String getCountry() {
        return countryProperty().get();
    }

    public void setCountry(String country) {
        this.countryProperty().setValue(country);
    }

    public String getCity() {
        return cityProperty().get();
    }

    public void setCity(String city) {
        this.cityProperty().setValue(city);
    }

    public String getHouseNumber() {
        return houseNumberProperty().get();
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumberProperty().setValue(houseNumber);
    }

    public String getStreet() {
        return streetProperty().get();
    }

    public void setStreet(String street) {
        this.streetProperty().setValue(street);
    }

    public String getZipCode() {
        return zipCodeProperty().get();
    }

    public void setZipCode(String zipCode) {
        this.zipCodeProperty().setValue(zipCode);
    }

    public LongProperty idProperty() {
        return id;
    }

    public StringProperty dietProperty() {
        return diet;
    }

    public StringProperty givenNameProperty() {
        return givenName;
    }

    public StringProperty familyNameProperty() {
        return familyName;
    }

    public StringProperty dateOfBirthProperty() {
        return dateOfBirth;
    }

    public StringProperty contactValueProperty() {
        return contactValue;
    }

    public StringProperty countryProperty() {
        return country;
    }

    public StringProperty cityProperty() {
        return city;
    }

    public StringProperty houseNumberProperty() {
        return houseNumber;
    }

    public StringProperty streetProperty() {
        return street;
    }

    public StringProperty zipCodeProperty() {
        return zipCode;
    }

}
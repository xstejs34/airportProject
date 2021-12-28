package org.but.feec.airport.api;

public class PersonEditView {

    private Integer id;
    private String givenName;
    private String familyName;
    private String contactValue;
    private String country;
    private String diet;
    private String city;
    private String street;
    private String houseNumber;
    private String zipCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
    @Override
    public String toString() {
        return "PersonEditView{" +
                "id_customer='" + id + '\'' +
                ", given_name='" + givenName + '\'' +
                ", family_name='" + familyName + '\'' +
                ", diet='" + diet + '\'' +
                ", contact_value='" + contactValue + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house_number='" + houseNumber + '\'' +
                ", zip_code='" + zipCode + '\'' +
                '}';
    }
}
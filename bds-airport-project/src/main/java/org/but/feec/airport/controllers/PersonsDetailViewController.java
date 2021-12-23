package org.but.feec.airport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.but.feec.airport.api.PersonDetailView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonsDetailViewController {

    private static final Logger logger = LoggerFactory.getLogger(PersonsDetailViewController.class);

    @FXML
    private TextField idTextField;

    @FXML
    private TextField dietTextField;

    @FXML
    private TextField givenNameTextField;

    @FXML
    private TextField familyNameTextField;

    @FXML
    private TextField dateOfBirthTextField;

    @FXML
    private TextField contactValueTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField houseNumberTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField zipCodeTextField;

    // used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        idTextField.setEditable(false);
        dietTextField.setEditable(false);
        givenNameTextField.setEditable(false);
        familyNameTextField.setEditable(false);
        dateOfBirthTextField.setEditable(false);
        contactValueTextField.setEditable(false);
        countryTextField.setEditable(false);
        cityTextField.setEditable(false);
        houseNumberTextField.setEditable(false);
        streetTextField.setEditable(false);
        zipCodeTextField.setEditable(false);

        loadPersonsData();

        logger.info("PersonsDetailViewController initialized");
    }

    private void loadPersonsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof PersonDetailView) {
            PersonDetailView personBasicView = (PersonDetailView) stage.getUserData();
            idTextField.setText(String.valueOf(personBasicView.getId()));
            givenNameTextField.setText(personBasicView.getGivenName());
            familyNameTextField.setText(personBasicView.getFamilyName());
            dietTextField.setText(personBasicView.getDiet());
            dateOfBirthTextField.setText(personBasicView.getDateOfBirth());
            contactValueTextField.setText(personBasicView.getContactValue());
            countryTextField.setText(personBasicView.getCountry());
            cityTextField.setText(personBasicView.getCity());
            houseNumberTextField.setText(personBasicView.getHouseNumber());
            streetTextField.setText(personBasicView.getStreet());
            zipCodeTextField.setText(personBasicView.getZipCode());
        }
    }

}

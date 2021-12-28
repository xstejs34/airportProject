package org.but.feec.airport.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.but.feec.airport.api.PersonCreateView;
import org.but.feec.airport.data.PersonRepository;
import org.but.feec.airport.service.PersonService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class PersonCreateController {

    private static final Logger logger = LoggerFactory.getLogger(PersonCreateController.class);

    @FXML
    public Button newPersonCreatePerson;
    @FXML
    private TextField newPersonFirstName;
    @FXML
    private TextField newPersonLastName;
    @FXML
    private TextField newPersonDateOfBirth;
    @FXML
    private TextField newPersonDiet;
    @FXML
    private TextField newPersonContactValue;
    @FXML
    private TextField newPersonCountry;
    @FXML
    private TextField newPersonCity;
    @FXML
    private TextField newPersonStreet;
    @FXML
    private TextField newPersonZipCode;
    @FXML
    private TextField newPersonHouseNumber;
    @FXML
    private TextField newPersonUserName;
    @FXML
    private TextField newPersonPwd;


    private PersonService personService;
    private PersonRepository personRepository;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
        personRepository = new PersonRepository();
        personService = new PersonService(personRepository);

        validation = new ValidationSupport();
        validation.registerValidator(newPersonContactValue, Validator.createEmptyValidator("The contact must not be empty."));
        validation.registerValidator(newPersonFirstName, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(newPersonLastName, Validator.createEmptyValidator("The last name must not be empty."));
        validation.registerValidator(newPersonUserName, Validator.createEmptyValidator("The username must not be empty."));
        validation.registerValidator(newPersonPwd, Validator.createEmptyValidator("The password must not be empty."));
        validation.registerValidator(newPersonDateOfBirth, Validator.createEmptyValidator("The date of birth must not be empty."));
        validation.registerValidator(newPersonCountry, Validator.createEmptyValidator("The country must not be empty."));
        validation.registerValidator(newPersonCity, Validator.createEmptyValidator("The city must not be empty."));
        validation.registerValidator(newPersonStreet, Validator.createEmptyValidator("The street must not be empty."));
        validation.registerValidator(newPersonZipCode, Validator.createEmptyValidator("The zip code must not be empty."));
        validation.registerValidator(newPersonHouseNumber, Validator.createEmptyValidator("The house number must not be empty."));


        newPersonCreatePerson.disableProperty().bind(validation.invalidProperty());

        logger.info("PersonCreateController initialized");
    }

    @FXML
    void handleCreateNewPerson(ActionEvent event) {
        // can be written easier, its just for better explanation here on so many lines
        String givenName = newPersonFirstName.getText();
        String familyName = newPersonLastName.getText();
        String dateOfBirth = newPersonDateOfBirth.getText();
        String diet = newPersonDiet.getText();
        String contactValue = newPersonContactValue.getText();
        String country = newPersonCountry.getText();
        String city = newPersonCity.getText();
        String street = newPersonStreet.getText();
        String zipCode = newPersonZipCode.getText();
        String houseNumber = newPersonHouseNumber.getText();
        String userName = newPersonUserName.getText();
        String password = newPersonPwd.getText();



        PersonCreateView personCreateView = new PersonCreateView();
        personCreateView.setPwd(password.toCharArray());
        personCreateView.setGivenName(givenName);
        personCreateView.setFamilyName(familyName);
        personCreateView.setDateOfBirth(dateOfBirth);
        personCreateView.setDiet(diet);
        personCreateView.setContactValue(contactValue);
        personCreateView.setCountry(country);
        personCreateView.setCity(city);
        personCreateView.setStreet(street);
        personCreateView.setZipCode(zipCode);
        personCreateView.setHouseNumber(houseNumber);
        personCreateView.setUserName(userName);
        personService.createPerson(personCreateView);

        personCreatedConfirmationDialog();
    }

    private void personCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Customer Created Confirmation");
        alert.setHeaderText("Your customer was successfully created.");

        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idlestage.setCycleCount(1);
        idlestage.play();
        Optional<ButtonType> result = alert.showAndWait();
    }

}
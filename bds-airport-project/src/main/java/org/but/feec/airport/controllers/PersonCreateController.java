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
    private TextField newPersonContactValue;

    @FXML
    private TextField newPersonFirstName;

    @FXML
    private TextField newPersonLastName;

    @FXML
    private TextField newPersonNickname;

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
        validation.registerValidator(newPersonContactValue, Validator.createEmptyValidator("The email must not be empty."));
        validation.registerValidator(newPersonFirstName, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(newPersonLastName, Validator.createEmptyValidator("The last name must not be empty."));
        validation.registerValidator(newPersonNickname, Validator.createEmptyValidator("The nickname must not be empty."));
        validation.registerValidator(newPersonPwd, Validator.createEmptyValidator("The password must not be empty."));

        newPersonCreatePerson.disableProperty().bind(validation.invalidProperty());

        logger.info("PersonCreateController initialized");
    }

    @FXML
    void handleCreateNewPerson(ActionEvent event) {
        // can be written easier, its just for better explanation here on so many lines
        String contactValue = newPersonContactValue.getText();
        String givenName = newPersonFirstName.getText();
        String familyName = newPersonLastName.getText();
        String userName = newPersonNickname.getText();
        String password = newPersonPwd.getText();

        PersonCreateView personCreateView = new PersonCreateView();
        personCreateView.setPwd(password.toCharArray());
        personCreateView.setContactValue(contactValue);
        personCreateView.setGivenName(givenName);
        personCreateView.setFamilyName(familyName);
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
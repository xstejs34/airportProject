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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.but.feec.airport.api.PersonBasicView;
import org.but.feec.airport.api.PersonDetailView;
import org.but.feec.airport.api.PersonEditView;
import org.but.feec.airport.data.PersonRepository;
import org.but.feec.airport.service.PersonService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class PersonsEditController {

    private static final Logger logger = LoggerFactory.getLogger(PersonsEditController.class);

    @FXML
    public Button editPersonButton;
    @FXML
    public TextField idTextField;
    @FXML
    private TextField contactValueTextField;
    @FXML
    private TextField givenNameTextField;
    @FXML
    private TextField familyNameTextField;
    @FXML
    private TextField countryTextField;
    @FXML
    private TextField dietTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField houseNumberTextField;
    @FXML
    private TextField zipCodeTextField;


    private PersonService personService;
    private PersonRepository personRepository;
    private ValidationSupport validation;

    // used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        personRepository = new PersonRepository();
        personService = new PersonService(personRepository);

        validation = new ValidationSupport();
        validation.registerValidator(idTextField, Validator.createEmptyValidator("The id must not be empty."));
        idTextField.setEditable(false);
        validation.registerValidator(contactValueTextField, Validator.createEmptyValidator("The contact must not be empty."));
        validation.registerValidator(givenNameTextField, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(familyNameTextField, Validator.createEmptyValidator("The last name must not be empty."));
        validation.registerValidator(countryTextField, Validator.createEmptyValidator("The country must not be empty."));
        validation.registerValidator(cityTextField, Validator.createEmptyValidator("The city must not be empty."));
        validation.registerValidator(streetTextField, Validator.createEmptyValidator("The street must not be empty."));
        validation.registerValidator(zipCodeTextField, Validator.createEmptyValidator("The zip code must not be empty."));
        validation.registerValidator(houseNumberTextField, Validator.createEmptyValidator("The house number must not be empty."));
        editPersonButton.disableProperty().bind(validation.invalidProperty());

        loadPersonsData();

        logger.info("PersonsEditController initialized");
    }

    /**
     * Load passed data from Persons controller. Check this tutorial explaining how to pass the data between controllers: https://dev.to/devtony101/javafx-3-ways-of-passing-information-between-scenes-1bm8
     */
    private void loadPersonsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof PersonDetailView) {

            PersonDetailView personDetailView = (PersonDetailView) stage.getUserData();
            idTextField.setText(String.valueOf(personDetailView.getId()));
            contactValueTextField.setText(personDetailView.getContactValue());
            givenNameTextField.setText(personDetailView.getGivenName());
            familyNameTextField.setText(personDetailView.getFamilyName());
            countryTextField.setText(personDetailView.getCountry());
            dietTextField.setText(personDetailView.getDiet());
            cityTextField.setText(personDetailView.getCity());
            streetTextField.setText(personDetailView.getStreet());
            houseNumberTextField.setText(personDetailView.getHouseNumber());
            zipCodeTextField.setText(personDetailView.getZipCode());
        }
    }

    @FXML
    public void handleEditPersonButton(ActionEvent event) {
        // can be written easier, its just for better explanation here on so many lines
        Integer id = Integer.valueOf(idTextField.getText());
        String contactValue = contactValueTextField.getText();
        String givenName = givenNameTextField.getText();
        String familyName = familyNameTextField.getText();
        String diet= dietTextField.getText();
        String country= countryTextField.getText();
        String city= cityTextField.getText();
        String street= streetTextField.getText();
        String houseNumber= houseNumberTextField.getText();
        String zipCode= zipCodeTextField.getText();

        PersonEditView personEditView = new PersonEditView();
        personEditView.setId(id);
        personEditView.setContactValue(contactValue);
        personEditView.setGivenName(givenName);
        personEditView.setFamilyName(familyName);
        personEditView.setDiet(diet);
        personEditView.setCountry(country);
        personEditView.setCity(city);
        personEditView.setStreet(street);
        personEditView.setHouseNumber(houseNumber);
        personEditView.setZipCode(zipCode);

        personService.editPerson(personEditView);

        personEditedConfirmationDialog();
    }

    private void personEditedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Person Edited Confirmation");
        alert.setHeaderText("Your person was successfully edited.");

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

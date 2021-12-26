package org.but.feec.airport.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.but.feec.airport.api.SQLInjectionView;
import org.but.feec.airport.config.DataSourceConfig;
import org.but.feec.airport.exceptions.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLInjectionController {

    private static final Logger logger = LoggerFactory.getLogger(SQLInjectionController.class);

    @FXML
    public Button runQueryButton;
    @FXML
    public Button refreshButton;
    @FXML
    public Button recreateButton;
    @FXML
    private TableColumn<SQLInjectionView, String> column1;
    @FXML
    private TableColumn<SQLInjectionView, String> column2;
    @FXML
    private TableColumn<SQLInjectionView, String> column3;
    @FXML
    private TextField SQLInjectionTextField;
    @FXML
    private TableView<SQLInjectionView> SQLInjectionTableView;


    @FXML
    private void initialize() {

        column1.setCellValueFactory(new PropertyValueFactory<SQLInjectionView, String>("column1"));
        column2.setCellValueFactory(new PropertyValueFactory<SQLInjectionView, String>("column2"));
        column3.setCellValueFactory(new PropertyValueFactory<SQLInjectionView, String>("column3"));

        logger.info("SqlInjectionController initialized");
    }

    private SQLInjectionView mapToSQLInjectionView(ResultSet rs) throws SQLException {
        SQLInjectionView sqlView = new SQLInjectionView();
        sqlView.setColumn1(rs.getString("column1"));
        sqlView.setColumn2(rs.getString("column2"));
        sqlView.setColumn3(rs.getString("column3"));

        return sqlView;
    }
    public List<SQLInjectionView> getSQLInjectionView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT column1, column2, column3" +
                             " FROM airport_sys.dummy");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<SQLInjectionView> sqlView = new ArrayList<>();
            while (resultSet.next()) {
                sqlView.add(mapToSQLInjectionView(resultSet));
            }
            return sqlView;
        } catch (SQLException e) {
            throw new DataAccessException("SQL basic view could not be loaded.", e);
        }

    }
    private ObservableList<SQLInjectionView> initializeSqlData() {
        List<SQLInjectionView> rows = getSQLInjectionView();
        return FXCollections.observableArrayList(rows);
    }

    public void handleRecreateButton(ActionEvent actionEvent) {

        String sqlQuery = "CREATE TABLE IF NOT EXISTS airport_sys.dummy" +
                " (id SERIAL NOT NULL PRIMARY KEY, column1 VARCHAR NOT NULL, column2 VARCHAR NOT NULL, column3 VARCHAR NOT NULL);";
        try (Connection connection = DataSourceConfig.getConnection()){
            connection.createStatement().executeUpdate(sqlQuery);
            handleSqlInsert();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void handleSqlInsert() throws SQLException {
        String sqlQuery = "INSERT INTO airport_sys.dummy (column1, column2, column3) " +
                "VALUES ('SQL', 'DUMMY', 'TABLE'), ('SQL', 'DUMMY', 'TABLE'), ('SQL', 'DUMMY', 'TABLE');";
        try (Connection connection = DataSourceConfig.getConnection()){
            connection.createStatement().executeUpdate(sqlQuery);
            tableCreatedConfirmationDialog();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void tableCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Table Created Confirmation");
        alert.setHeaderText("Dummy table was successfully created.");

        Timeline idleStage = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idleStage.setCycleCount(1);
        idleStage.play();
        Optional<ButtonType> result = alert.showAndWait();
    }

    public void handleRunInjectionButton(ActionEvent actionEvent) {
        String parameter = SQLInjectionTextField.getText();

        String sql_query = "SELECT column1, column2, column3 FROM airport_sys.dummy WHERE column1 = '"+parameter+"';";
        try (Connection connection = DataSourceConfig.getConnection()){

            List<SQLInjectionView> sqlView = new ArrayList();
            ResultSet rs = connection.createStatement().executeQuery(sql_query);

            while (rs.next()) {
                sqlView.add(mapToSQLInjectionView(rs));
            }

            SQLInjectionTableView.setItems(FXCollections.observableArrayList(sqlView));
            //return sqlView;
        } catch (SQLException e) {
            throw new DataAccessException("SQL query could not be performed.", e);
        }
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        SQLInjectionTableView.setItems(null);
        SQLInjectionTableView.refresh();
        SQLInjectionTableView.sort();
    }
}

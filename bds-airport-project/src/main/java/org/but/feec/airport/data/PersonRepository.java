package org.but.feec.airport.data;

import org.but.feec.airport.api.*;
import org.but.feec.airport.config.DataSourceConfig;
import org.but.feec.airport.exceptions.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {

    public PersonAuthView findPersonByUserName(String username) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT username, password" +
                             " FROM airport_sys.customer p" +
                             " WHERE p.username = ?")
        ) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonAuth(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find customer by ID with addresses failed.", e);
        }
        return null;
    }

    public PersonDetailView findPersonDetailedView(Long id_customer) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT p.id_customer, given_name, family_name, date_of_birth, diet, contact_value, city, house_number, street" +
                             " FROM airport_sys.customer p LEFT JOIN airport_sys.customer_has_address a ON p.id_customer= a.id_customer" +
                             " LEFT JOIN airport_sys.address k ON a.id_address= k.id_address" +
                             " LEFT JOIN airport_sys.contact t ON p.id_customer= t.id_customer")
        ) {
            preparedStatement.setLong(1, id_customer);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonDetailView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find customer by ID with addresses failed.", e);
        }
        return null;
    }

    /**
     * What will happen if we do not use LEFT JOIN? What persons will be returned? Ask your self and repeat JOIN from the presentations
     *
     * @return list of persons
     */
    public List<PersonBasicView> getPersonsBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id_customer, given_name, family_name, date_of_birth" +
                             " FROM airport_sys.customer");
             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<PersonBasicView> personBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                personBasicViews.add(mapToPersonBasicView(resultSet));
            }
            return personBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Persons basic view could not be loaded.", e);
        }
    }

    public void createPerson(PersonCreateView personCreateView) {
        String insertPersonSQL = "INSERT INTO airport_sys.customer (given_name,family_name,date_of_birth,diet,username, password) VALUES (?,?,?,?,?,?)";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, personCreateView.getGivenName());
            preparedStatement.setString(2, personCreateView.getFamilyName());
            preparedStatement.setString(3, personCreateView.getDateOfBirth());
            preparedStatement.setString(4, personCreateView.getDiet());
            preparedStatement.setString(5, personCreateView.getUserName());
            preparedStatement.setString(6, String.valueOf(personCreateView.getPwd()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating customer failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating customer failed operation on the database failed.");
        }
    }

    public void editPerson(PersonEditView personEditView) {
        String insertPersonSQL = "UPDATE airport_sys.customer p SET given_name = ?, family_name = ?, date_of_birth= ?,diet= ?, username = ? WHERE p.id_customer = ?";
        String checkIfExists = "SELECT given_name, family_name FROM airport_sys.customer p WHERE p.id_customer = ?";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, personEditView.getGivenName());
            preparedStatement.setString(2, personEditView.getFamilyName());
            preparedStatement.setString(3, personEditView.getDateOfBirth());
            preparedStatement.setString(4, personEditView.getDiet());
            preparedStatement.setString(5, personEditView.getUserName());
            preparedStatement.setLong(6, personEditView.getId());

            try {
                // TODO set connection autocommit to false
                /* HERE */
                connection.setAutoCommit(false);
                try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, personEditView.getId());
                    ps.execute();
                } catch (SQLException e) {
                    throw new DataAccessException("This person for edit do not exists.");
                }

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DataAccessException("Creating person failed, no rows affected.");
                }
                // TODO commit the transaction (both queries were performed)
                /* HERE */
                connection.commit();
            } catch (SQLException e) {
                // TODO rollback the transaction if something wrong occurs
                /* HERE */
                connection.rollback();

            } finally {
                // TODO set connection autocommit back to true
                /* HERE */
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }


    /**
     * <p>
     * Note: In practice reflection or other mapping frameworks can be used (e.g., MapStruct)
     * </p>
     */
    private PersonAuthView mapToPersonAuth(ResultSet rs) throws SQLException {
        PersonAuthView customer = new PersonAuthView();
        customer.setUserName(rs.getString("username"));
        customer.setPassword(rs.getString("password"));
        return customer;
    }

    private PersonBasicView mapToPersonBasicView(ResultSet rs) throws SQLException {
        PersonBasicView personBasicView = new PersonBasicView();
        personBasicView.setId(rs.getLong("id_person"));
        personBasicView.setGivenName(rs.getString("given_name"));
        personBasicView.setFamilyName(rs.getString("family_name"));
        personBasicView.setUserName(rs.getString("username"));
        personBasicView.setCity(rs.getString("city"));
        return personBasicView;
    }

    private PersonDetailView mapToPersonDetailView(ResultSet rs) throws SQLException {
        PersonDetailView personDetailView = new PersonDetailView();
        personDetailView.setId(rs.getLong("id_customer"));
        personDetailView.setGivenName(rs.getString("given_name"));
        personDetailView.setFamilyName(rs.getString("family_name"));
        personDetailView.setUserName(rs.getString("username"));
        personDetailView.setContactValue(rs.getString("contact_value"));
        personDetailView.setCity(rs.getString("city"));
        personDetailView.setHouseNumber(rs.getString("house_number"));
        personDetailView.setStreet(rs.getString("street"));
        return personDetailView;
    }

}

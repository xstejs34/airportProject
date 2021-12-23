package org.but.feec.airport.data;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
                     "SELECT p.id_customer, given_name, family_name, date_of_birth,contact_value,country, diet, city, house_number, street,zip_code" +
                             " FROM airport_sys.customer p  LEFT JOIN airport_sys.customer_has_address a ON p.id_customer= a.id_customer" +
                             " LEFT JOIN airport_sys.address k ON a.id_address= k.id_address" +
                             " LEFT JOIN airport_sys.country c ON k.id_country=c.id_country" +
                             " LEFT JOIN airport_sys.contact t ON p.id_customer= t.id_customer" +
                             " WHERE p.id_customer = ?")
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
                     "SELECT p.id_customer,contact_value, given_name, family_name, city, username" +
                             " FROM airport_sys.customer p LEFT JOIN airport_sys.contact c ON p.id_customer= c.id_customer" +
                             " LEFT JOIN airport_sys.customer_has_address a ON p.id_customer=a.id_customer" +
                             " LEFT JOIN airport_sys.address t ON a.id_address=t.id_address WHERE p.id_customer != '0';");
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
        String insertPersonSQL =
                "WITH ins1 AS ( INSERT INTO airport_sys.customer (given_name,family_name,date_of_birth,diet,username, password) " +
                        "VALUES(?,?,?,?,?,?) RETURNING id_customer )," +
                        " ins2 AS ( INSERT INTO airport_sys.contact (contact_type,contact_value,id_customer) VALUES ('contact',?, (select id_customer from ins1)) )," +
                        " ins3 AS ( INSERT INTO airport_sys.country (country) VALUES (?) RETURNING id_country )," +
                        " ins4 AS ( INSERT INTO airport_sys.address (city,street,zip_code,house_number,id_country) VALUES (?,?,?,?,(select id_country from ins3)) RETURNING id_address ) " +
                        "INSERT INTO airport_sys.customer_has_address (id_customer, id_address, address_type) VALUES ((select id_customer from ins1),(select id_address from ins4),'Correspondential')";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, personCreateView.getGivenName());
            preparedStatement.setString(2, personCreateView.getFamilyName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(personCreateView.getDateOfBirth()));
            preparedStatement.setString(4, personCreateView.getDiet());
            preparedStatement.setString(5, personCreateView.getUserName());
            preparedStatement.setString(6, String.valueOf(personCreateView.getPwd()));
            preparedStatement.setString(7, personCreateView.getContactValue());;
            preparedStatement.setString(8, personCreateView.getCountry());
            preparedStatement.setString(9, personCreateView.getCity());
            preparedStatement.setString(10, personCreateView.getStreet());
            preparedStatement.setString(11, personCreateView.getZipCode());
            preparedStatement.setString(12, personCreateView.getHouseNumber());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating customer failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating customer failed operation on the database failed.");
        }

    }

    public void editPerson(PersonEditView personEditView) {
        String insertPersonSQL = "WITH upd1 AS ( UPDATE airport_sys.customer SET given_name=?,family_name=?,diet=? WHERE id_customer=? )," +
                " upd2 AS ( UPDATE airport_sys.contact SET contact_value=? WHERE id_customer=? )," +
                " upd3 AS ( SELECT id_address FROM airport_sys.customer_has_address WHERE id_customer =? )," +
                " UPDATE airport_sys.address SET city=?,street=?, zip_code=?, house_number=?, id_country=(select id_country FROM airport_sys.country WHERE country=?) WHERE id_address=(select id_address from upd3)";
        String checkIfExists = "SELECT username FROM airport_sys.customer e WHERE e.id_customer =?";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, personEditView.getGivenName());
            preparedStatement.setString(2, personEditView.getFamilyName());
            preparedStatement.setString(3, personEditView.getDiet());
            preparedStatement.setInt(4, personEditView.getId());
            preparedStatement.setString(5, personEditView.getContactValue());
            preparedStatement.setInt(6, personEditView.getId());
            preparedStatement.setInt(7, personEditView.getId());
            preparedStatement.setString(8, personEditView.getCity());
            preparedStatement.setString(9, personEditView.getStreet());
            preparedStatement.setString(10, personEditView.getZipCode());
            preparedStatement.setString(11, personEditView.getHouseNumber());
            preparedStatement.setString(12, personEditView.getCountry());
            try {
                connection.setAutoCommit(false);
                try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, personEditView.getId());
                    ps.execute();
                } catch (SQLException e) {
                    throw new DataAccessException("This person for edit does not exist.");
                }

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new DataAccessException("Creating person failed, no rows affected.");
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            } finally {
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
        personBasicView.setId(rs.getLong("id_customer"));
        personBasicView.setContactValue(rs.getString("contact_value"));
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
        personDetailView.setDateOfBirth(rs.getString("date_of_birth"));
        personDetailView.setContactValue(rs.getString("contact_value"));
        personDetailView.setCountry(rs.getString("country"));
        personDetailView.setDiet(rs.getString("diet"));
        personDetailView.setCity(rs.getString("city"));
        personDetailView.setHouseNumber(rs.getString("house_number"));
        personDetailView.setStreet(rs.getString("street"));
        personDetailView.setZipCode(rs.getString("zip_code"));
        return personDetailView;
    }

}

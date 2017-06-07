package com.panaceum.dao;

import com.panaceum.model.Prescription;
import com.panaceum.model.User;
import com.panaceum.util.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.core.Response;

public class PrescriptionDao {

    private static DatabaseConnection connection = new DatabaseConnection();
    private UserDao userDao = new UserDao();

    public Response add(User user, Prescription prescription) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT addPrescription('" + user.getLogin() + "', '" + user.getToken()
                    + "', '" + prescription.getDosage() + "', '" + prescription.getExpiryDate() + "', '" + prescription.getMedicineName()
                    + "', " + prescription.getDoctorid() + ", " + prescription.getPatientId() + ")");

            while (resultSet.next()) {
                prescription.setId(resultSet.getInt(1));
                if (prescription.getId() == 0) {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }

        connection.closeConnection();
        return Response.ok("{\"presriptionId\":" + prescription.getId() + "}").build();
    }
    
    public Response delete(User user, int id) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }
        if (!userDao.checkPrivileges(user.getLogin()).equals("doctor")) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        Statement statement;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            statement.executeQuery("DELETE FROM prescription WHERE id = " + id);
        } catch (SQLException ex) {
            if (!ex.toString().contains("Zapytanie nie zwróciło żadnych wyników")) {
                System.out.println(ex.toString());
                connection.closeConnection();
                return Response.serverError().build();
            }
        }

        connection.closeConnection();
        return Response.ok().build();
    }

}

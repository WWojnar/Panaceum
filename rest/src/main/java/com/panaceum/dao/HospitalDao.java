package com.panaceum.dao;

import com.google.gson.Gson;
import com.panaceum.model.Hospital;
import com.panaceum.model.User;
import com.panaceum.util.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.core.Response;

public class HospitalDao {
    
    private static DatabaseConnection connection = new DatabaseConnection();
    
    private static UserDao userDao = new UserDao();
    
    public Response getById(int id) {
        Hospital hospital = new Hospital();
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM hospitalView WHERE hospitalId = " + id);

            while (resultSet.next()) {
                hospital.setId(resultSet.getInt("hospitalId"));
                hospital.setName(resultSet.getString("name"));
                hospital.setAddressId(resultSet.getInt("addressId"));
                hospital.setCity(resultSet.getString("city"));
                hospital.setStreet(resultSet.getString("street"));
                hospital.setBuildingNumber(resultSet.getString("buildingNumber"));
                hospital.setFlatNumber(resultSet.getString("flatNumber"));
                hospital.setZipCode(resultSet.getString("zipCode"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }
        connection.closeConnection();

        if (hospital.getId() == 0) {
            return Response.status(404).entity("No such hospital found").build();
        }

        Gson gson = new Gson();
        return Response.ok(gson.toJson(hospital)).build();
    }
    
    public Response add(User user, Hospital hospital) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }
        if (!userDao.checkPrivileges(user.getLogin()).equals("admin")) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT addHospital('" + hospital.getName() + "', '" + hospital.getRegon()
                    + "', '" + hospital.getPhone() + "', '" + hospital.getCity() + "', '" + hospital.getStreet() + "', '" + hospital.getBuildingNumber()
                    + "', '" + hospital.getFlatNumber() + "', '" + hospital.getZipCode() + "')");

            while (resultSet.next()) {
                hospital.setId(resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }

        connection.closeConnection();

        if (hospital.getId() == 0) {
            return Response.status(406).entity("Hospital already exist in DB").build();
        }
System.err.println("to");
        return Response.ok("{\"hospitalId\":" + hospital.getId() + "}").build();
    }
    
    public Response update(User user, Hospital hospital) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }
        if (!userDao.checkPrivileges(user.getLogin()).equals("admin")) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT updateHospital(" + hospital.getId() + ", '" + hospital.getName() + "', '" + hospital.getRegon()
                    + "', '" + hospital.getPhone() + "', '" + hospital.getCity() + "', '" + hospital.getStreet() + "', '" + hospital.getBuildingNumber()
                    + "', '" + hospital.getFlatNumber() + "', '" + hospital.getZipCode() + "')");

            while (resultSet.next()) {
                hospital.setId(resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }

        connection.closeConnection();

        if (hospital.getId() == 0) {
            return Response.status(404).entity("No such hospital").build();
        }

        return Response.ok().build();
    }
    
    public Response delete(User user, int id) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }
        if (!userDao.checkPrivileges(user.getLogin()).equals("admin")) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        Statement statement;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            statement.executeQuery("DELETE FROM hospital WHERE id = " + id);
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

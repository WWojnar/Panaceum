package com.panaceum.dao;

import com.google.gson.Gson;
import com.panaceum.model.Patient;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import com.panaceum.model.User;
import com.panaceum.util.DatabaseConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientDao {

    private static DatabaseConnection connection = new DatabaseConnection();

    private static UserDao userDao = new UserDao();

    public Response getById(User user, int id) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        Patient patient = new Patient();
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM patientView WHERE patientId = " + id);

            while (resultSet.next()) {
                patient.setId(resultSet.getInt("patientId"));
                patient.setSex(resultSet.getString("sex"));
                patient.setAge(resultSet.getInt("age"));
                patient.setBloodType(resultSet.getString("bloodType"));
                patient.setPesel(resultSet.getString("pesel"));
                patient.setFirstName(resultSet.getString("firstName"));
                patient.setLastName(resultSet.getString("lastName"));
                patient.setPhone(resultSet.getString("phone"));
                patient.setEmail(resultSet.getString("email"));
                patient.setAddressId(resultSet.getInt("addressId"));
                patient.setCity(resultSet.getString("city"));
                patient.setStreet(resultSet.getString("street"));
                patient.setBuildingNumber(resultSet.getString("buildingNumber"));
                patient.setFlatNumber(resultSet.getString("flatNumber"));
                patient.setZipCode(resultSet.getString("zipCode"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }
        connection.closeConnection();

        if (patient.getId() == 0) {
            return Response.status(404).entity("No such patient found").build();
        }

        Gson gson = new Gson();
        return Response.ok(gson.toJson(patient)).build();
    }

    public Response getAll(User user) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        List<Patient> patients = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;    //zapytanie do BD moze do tego wsadzic odpowiedz z BD

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM patientView"); //SELECT zwrocil dane i wsadzil w resultSet

            while (resultSet.next()) {
                Patient patient = new Patient();

                patient.setId(resultSet.getInt("patientId"));
                patient.setPesel(resultSet.getString("pesel"));
                patient.setFirstName(resultSet.getString("firstName"));
                patient.setLastName(resultSet.getString("lastName"));

                patients.add(patient);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }
        connection.closeConnection();

        Gson gson = new Gson();
        return Response.ok(gson.toJson(patients)).build();
    }

    public Response getByPesel(User user, String pesel) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        Patient patient = new Patient();
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM patientView WHERE pesel = '" + pesel + "'");

            while (resultSet.next()) {
                patient.setId(resultSet.getInt("patientId"));
                patient.setSex(resultSet.getString("sex"));
                patient.setAge(resultSet.getInt("age"));
                patient.setBloodType(resultSet.getString("bloodType"));
                patient.setPesel(resultSet.getString("pesel"));
                patient.setFirstName(resultSet.getString("firstName"));
                patient.setLastName(resultSet.getString("lastName"));
                patient.setPhone(resultSet.getString("phone"));
                patient.setEmail(resultSet.getString("email"));
                patient.setAddressId(resultSet.getInt("addressId"));
                patient.setCity(resultSet.getString("city"));
                patient.setStreet(resultSet.getString("street"));
                patient.setBuildingNumber(resultSet.getString("buildingNumber"));
                patient.setFlatNumber(resultSet.getString("flatNumber"));
                patient.setZipCode(resultSet.getString("zipCode"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }
        connection.closeConnection();

        if (patient.getId() == 0) {
            return Response.status(404).entity("No such patient found").build();
        }

        Gson gson = new Gson();
        return Response.ok(gson.toJson(patient)).build();
    }
    
    public Response add(User user, Patient patient) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }
        if (!userDao.checkPrivileges(user.getLogin()).equals("doctor")) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        Statement statement;
        ResultSet resultSet;
        /*System.err.println("SELECT addPatient('" + patient.getSex() + "', " + patient.getAge()
                    + ", '" + patient.getBloodType() + "', '" + patient.getPesel() + "', '" + patient.getFirstName()
                    + "', '" + patient.getLastName() + "', '" + patient.getPhone() + "', '" + patient.getEmail()
                    + "', '" + patient.getCity() + "', '" + patient.getStreet() + "', '" + patient.getBuildingNumber()
                    + "', '" + patient.getFlatNumber() + "', '" + patient.getZipCode() + "')");*/
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT addPatient('" + patient.getSex() + "', " + patient.getAge()
                    + ", '" + patient.getBloodType() + "', '" + patient.getPesel() + "', '" + patient.getFirstName()
                    + "', '" + patient.getLastName() + "', '" + patient.getPhone() + "', '" + patient.getEmail()
                    + "', '" + patient.getCity() + "', '" + patient.getStreet() + "', '" + patient.getBuildingNumber()
                    + "', '" + patient.getFlatNumber() + "', '" + patient.getZipCode() + "')");
            
            while (resultSet.next()) {
                patient.setId(resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }
        
        connection.closeConnection();
        
        if (patient.getId() == 0) {
            return Response.status(406).entity("Patient already exist in DB").build();
        }

        return Response.ok("{\"patientId\":" + patient.getId() + "}").build();
    }

}

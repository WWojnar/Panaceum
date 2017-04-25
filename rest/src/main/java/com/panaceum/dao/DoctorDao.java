package com.panaceum.dao;

import com.google.gson.Gson;
import com.panaceum.model.Doctor;
import com.panaceum.model.Medicine;
import com.panaceum.model.User;
import com.panaceum.util.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;


public class DoctorDao {
    
    private static DatabaseConnection connection = new DatabaseConnection();
    private UserDao userDao = new UserDao();

    public Response getAll(User user) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        List<Doctor> doctors = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT doctorId, pesel, firstName, lastName FROM doctorView");

            while (resultSet.next()) {
                Doctor doctor = new Doctor();

                doctor.setId(resultSet.getInt("doctorId"));
                doctor.setPesel(resultSet.getString("pesel"));
                doctor.setFirstName(resultSet.getString("firstName"));
                doctor.setLastName(resultSet.getString("lastName"));

                doctors.add(doctor);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }
        connection.closeConnection();

        Gson gson = new Gson();
        return Response.ok(gson.toJson(doctors)).build();
    }
    
    public Response getById(User user, int id) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        Doctor doctor = new Doctor();
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM doctorView WHERE doctorId = " + id);

            while (resultSet.next()) {
                doctor.setId(resultSet.getInt("doctorId"));
                doctor.setSpeciality(resultSet.getString("speciality"));
                doctor.setLicenceNumber(resultSet.getString("licenceNumber"));
                doctor.setPesel(resultSet.getString("pesel"));
                doctor.setFirstName(resultSet.getString("firstName"));
                doctor.setLastName(resultSet.getString("lastName"));
                doctor.setPhone(resultSet.getString("phone"));
                doctor.setEmail(resultSet.getString("email"));
                doctor.setAddressId(resultSet.getInt("addressId"));
                doctor.setCity(resultSet.getString("city"));
                doctor.setStreet(resultSet.getString("street"));
                doctor.setBuildingNumber(resultSet.getString("buildingNumber"));
                doctor.setFlatNumber(resultSet.getString("flatNumber"));
                doctor.setZipCode(resultSet.getString("zipCode"));
                doctor.setUserId(resultSet.getInt("userId"));
                doctor.setLogin(resultSet.getString("login"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }
        connection.closeConnection();

        if (doctor.getId() == 0) return Response.status(404).entity("No such doctor found").build();
        
        Gson gson = new Gson();
        return Response.ok(gson.toJson(doctor)).build();
    }
    
}

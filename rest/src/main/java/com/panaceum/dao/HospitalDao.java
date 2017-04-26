package com.panaceum.dao;

import com.google.gson.Gson;
import com.panaceum.model.Hospital;
import com.panaceum.util.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ws.rs.core.Response;

public class HospitalDao {
    
    private static DatabaseConnection connection = new DatabaseConnection();
    
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
    
}

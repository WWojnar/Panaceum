package com.panaceum.dao;

import com.google.gson.Gson;
import com.panaceum.model.Medicine;
import com.panaceum.model.User;
import com.panaceum.util.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

public class MedicineDao {
    
    private static DatabaseConnection connection = new DatabaseConnection();
    private UserDao userDao = new UserDao();

    public Response getAll(User user) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        List<Medicine> medicines = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT id, name FROM medicine");

            while (resultSet.next()) {
                Medicine medicine = new Medicine();

                medicine.setId(resultSet.getInt("id"));
                medicine.setName(resultSet.getString("name"));

                medicines.add(medicine);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }
        connection.closeConnection();

        Gson gson = new Gson();
        return Response.ok(gson.toJson(medicines)).build();
    }
    
    public Response getById(User user, int id) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        Medicine medicine = new Medicine();
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM medicine WHERE id = " + id);

            while (resultSet.next()) {
                medicine.setId(resultSet.getInt("id"));
                medicine.setName(resultSet.getString("name"));
                medicine.setActiveSubstance(resultSet.getString("activeSubstance"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }
        connection.closeConnection();
        
        if (medicine.getId() == 0) {
            return Response.status(404).entity("No such medicine found").build();
        }

        Gson gson = new Gson();
        return Response.ok(gson.toJson(medicine)).build();
    }

}

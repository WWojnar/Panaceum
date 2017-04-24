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

public class PatientDao {

    private static DatabaseConnection connection = new DatabaseConnection();
    
    private static UserDao userDao = new UserDao();
    
    public Response getById(User user, int id){
        if (!userDao.validate(user)) return Response.status(403).entity("User doesn't have necessary permissions").build();
            
            List<Patient> patients = new ArrayList<>();
            Statement statement;
            ResultSet resultSet;    //zapytanie do BD moze do tego wsadzic odpowiedz z BD
            
            try {
                connection.establishConnection();
                statement = connection.getConnection().createStatement();
                resultSet = statement.executeQuery("SELECT * FROM patientView WHERE patientid = " + id); //SELECT zwrocil dane i wsadzil w resultSet
                
                while (resultSet.next()) {  //petla w ktorej odczytuje dane, dla pojedynczych rekordow dziala tak samo dobrze
                    Patient patient = new Patient();
                    
                    patient.setId(resultSet.getInt("patientId"));
                    patient.setSex(resultSet.getString("sex"));
                    patient.setAge(resultSet.getInt("age"));
                    patient.setBloodType(resultSet.getString("bloodType"));
                    patient.setPesel(resultSet.getString("pesel"));
                    //pobieranie stringa z komorki z kolumny email
                    //dla innego typu zwracanego z kolumny inna metoda oczywiscie
                    //kolumne mozna precyzowac jej nazwa lub numerem w kolejnosci
                    //zaczawszy od 1
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
    public Response getAll(User user) {
//            if (!userDao.validate(user)) return Response.status(403).entity("User doesn't have necessary permissions").build();
//            
            List<Patient> patients = new ArrayList<>();
            Statement statement;
            ResultSet resultSet;    //zapytanie do BD moze do tego wsadzic odpowiedz z BD
            
            try {
                connection.establishConnection();
                statement = connection.getConnection().createStatement();
                resultSet = statement.executeQuery("SELECT * FROM patientView"); //SELECT zwrocil dane i wsadzil w resultSet
                
                while (resultSet.next()) {  //petla w ktorej odczytuje dane, dla pojedynczych rekordow dziala tak samo dobrze
                    Patient patient = new Patient();
                    
                    patient.setId(resultSet.getInt("patientId"));
                    patient.setSex(resultSet.getString("sex"));
                    patient.setAge(resultSet.getInt("age"));
                    patient.setBloodType(resultSet.getString("bloodType"));
                    patient.setPesel(resultSet.getString("pesel"));
                    //pobieranie stringa z komorki z kolumny email
                    //dla innego typu zwracanego z kolumny inna metoda oczywiscie
                    //kolumne mozna precyzowac jej nazwa lub numerem w kolejnosci
                    //zaczawszy od 1
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
}

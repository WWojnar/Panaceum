package com.panaceum.dao;

import com.google.gson.Gson;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import com.panaceum.model.User;
import com.panaceum.util.DatabaseConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static DatabaseConnection connection = new DatabaseConnection();
    
    /* Sprawdza prawa użytkownika do wykonania akcji,
       porównuje token użytkownika z tokenem w BD u użytkowniak o określonym loginie,
       robi to za pomocą funkcji w BD */
    public boolean validate(User user) {
        Statement statement;
        ResultSet resultSet;
        boolean result = false;
        
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT validate('" + user.getLogin() + "', '" + user.getToken() + "')");
            
            while (resultSet.next()) {
                result = resultSet.getBoolean(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        connection.closeConnection();
        return result;
    }

    public Response getAll(User user) {
            if (!this.validate(user)) return Response.status(403).entity("User doesn't have necessary permissions").build();
            
            List<User> users = new ArrayList<>();
            Statement statement;
            ResultSet resultSet;    //zapytanie do BD moze do tego wsadzic odpowiedz z BD
            
            try {
                connection.establishConnection();
                statement = connection.getConnection().createStatement();
                resultSet = statement.executeQuery("SELECT * FROM tUser"); //SELECT zwrocil dane i wsadzil w resultSet
                
                while (resultSet.next()) {  //petla w ktorej odczytuje dane, dla pojedynczych rekordow dziala tak samo dobrze
                    user = new User();
                    
                    user.setId(resultSet.getInt("id"));
                    user.setLogin(resultSet.getString("login"));  //pobieranie stringa z komorki z kolumny email
                    //dla innego typu zwracanego z kolumny inna metoda oczywiscie
                    //kolumne mozna precyzowac jej nazwa lub numerem w kolejnosci
                    //zaczawszy od 1
                    users.add(user);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                connection.closeConnection();
                return Response.serverError().build();
            }
            connection.closeConnection();

            Gson gson = new Gson();
            return Response.ok(gson.toJson(users)).build();
    }

    public Response register(User user) {
        Statement statement;    //zapytanie, mozna jeszcze uzywac PreparedStatement - szybsze w dzialaniu
        ResultSet resultSet;    //do tego sie wsadza to co zwroci zapytanie do BD

        try {
            connection.establishConnection();   //otwieramy polaczenie z baza danych, domyslnie ustawiona na BD z serwera
                                                //dla waszej prywatnej bazy danych musicie w metodzie zmienic ustawienia polaczenia
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT register('" + user.getLogin() + "','" + user.getPassword() + "')");  //zapytanie do BD
                                                                                //tutaj wywoluje metode ktora napisalem w BD, ktora wszystko odpowiednio zrobi
                                                                                //ale mozna sie bawic i bez tego
        } catch (Exception e) {
            System.err.println(e.toString());
            connection.closeConnection();

            if (e.toString().contains("login")) {   //przy powtorzonym adresie cos tam o mailu bedzie pisalo w wyjatku
                return Response.status(406).entity("Login already exist in DB").build();
            }

            return Response.serverError().build();  //a tu cholera wie co sie stalo
        }

        connection.closeConnection();   //pamietac by zamykac polaczenie, rowniez w obsludze wyjatku
        return Response.ok().build();   //odpowiedz OK, czyli kod 200
    }
    
    public Response login(User user) {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT login('" + user.getLogin()
                    + "', '" + user.getPassword() + "')");

            while (resultSet.next()) {
                user.setToken(resultSet.getString(1));
            }
            //do poprawy jak się zachce
            resultSet = statement.executeQuery("SELECT id FROM tUser WHERE login = '" + user.getLogin() + "'");
            
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            connection.closeConnection();

            return Response.serverError().build();
        }
System.err.println("tu");
        connection.closeConnection();
        if (user.getToken() == null) {
            System.err.println("No such user");
            return Response.status(404).entity("No such user").build();
        } else {
            return Response.ok("{\"id\":" + user.getId() + ",\"token\":\"" + user.getToken() + "\"}").build();
        }
    }

}

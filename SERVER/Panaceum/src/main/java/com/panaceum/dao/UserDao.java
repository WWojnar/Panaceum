package com.panaceum.dao;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import com.panaceum.model.User;
import com.panaceum.util.DatabaseConnection;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static DatabaseConnection connection = new DatabaseConnection();

    public List<User> getAll() {    //metoda nieuzywana przez nic w restcie, maly pokaz jak sie odbiera informacje z BD
        List<User> users = new ArrayList<>();
        User user;
        Statement statement;
        ResultSet resultSet;    //zapytanie do BD moze do tego wsadzic odpowiedz z BD

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM tuser"); //SELECT zwrocil dane i wsadzil w resultSet

            while (resultSet.next()) {  //petla w ktorej odczytuje dane, dla pojedynczych rekordow dziala tak samo dobrze
                user = new User();

                user.setEmail((resultSet.getString("email")));  //pobieranie stringa z komorki z kolumny email 
                                                                //dla innego typu zwracanego z kolumny inna metoda oczywiscie
                                                                //kolumne mozna precyzowac jej nazwa lub numerem w kolejnosci
                                                                //zaczawszy od 1
                user.setPassword(resultSet.getString("password"));
                user.setToken(resultSet.getString("token"));

                users.add(user);
            }
        } catch (Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return users;
    }

    public Response register(User user) {
        Statement statement;    //zapytanie, mozna jeszcze uzywac PreparedStatement - szybsze w dzialaniu
        ResultSet resultSet;    //do tego sie wsadza to co zwroci zapytanie do BD

        try {
            connection.establishConnection();   //otwieramy polaczenie z baza danych, domyslnie ustawiona na BD z serwera
                                                //dla waszej prywatnej bazy danych musicie w metodzie zmienic ustawienia polaczenia
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT register('" + user.getEmail() + "','" + user.getPassword() + "')");  //zapytanie do BD
                                                                                //tutaj wywoluje metode ktora napisalem w BD, ktora wszystko odpowiednio zrobi
                                                                                //ale mozna sie bawic i bez tego
        } catch (Exception e) {
            System.err.println(e.toString());
            connection.closeConnection();

            if (e.toString().contains("email")) {   //przy powtorzonym adresie cos tam o mailu bedzie pisalo w wyjatku
                return Response.status(406).entity("Email already exist in DB").build();
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
            resultSet = statement.executeQuery("SELECT login('" + user.getEmail()
                    + "', '" + user.getPassword() + "')");

            while (resultSet.next()) {
                user.setToken(resultSet.getString(1));
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            connection.closeConnection();

            return Response.serverError().build();
        }

        connection.closeConnection();
        if (user.getToken() == null) {
            return Response.status(404).entity("Nie ma takiego uzytkownika").build();
        } else {
            return Response.ok("{\"token\":\"" + user.getToken() + "\"}").build();
        }
    }

}

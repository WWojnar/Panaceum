package com.panaceum.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.panaceum.dao.UserDao;
import com.panaceum.model.User;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;

//sciezka do klasy serwisu encji User
@Path("/user")
public class UserService {

    private UserDao userDao = new UserDao();
    
    @GET
    @Path("/test")
    public String test() {
        return "It just works";
    }

    @POST
    @Path("/register")  //sciezka do metody
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(String incomingData) { //incomingData to odbierany JSON, by sie nie pierdolic slemy po stringu
        User user = new User();

        String email;
        String passwd;

        try {
            JSONObject json = new JSONObject(incomingData); //tworzymy obiekt json by latwo parsowac
                                                            //UWAGA: wymaga to by odbierany json mial dobry format
                                                            //dla tego przykladu nasz json wyglada tak:
                                                            //{"user":{
                                                            //  "email":"chuje wuje",
                                                            //  "password":"dzikie weze"}}
            json = json.getJSONObject("user");  //wchodzimy do sekcji user JSONA

            email = json.getString("email");    //biezemy email i password z jsona
            passwd = json.getString("password");
        } catch (JSONException e) { //dolary przeciw orzechą ze JSON jest zle napisany
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) { //cholera wie co sie spieprzylo
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }

        user.setEmail(email);   //modelujemy obiekt
        user.setPassword(passwd);

        return userDao.register(user);  //wywolujemy odpowiednia metode dostepu do bazu danych
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String incomingData) {
        User user = new User();

        String email;
        String passwd;

        try {
            JSONObject json = new JSONObject(incomingData);

            json = json.getJSONObject("user");

            email = json.getString("email");
            passwd = json.getString("password");
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }

        user.setEmail(email);
        user.setPassword(passwd);

        return userDao.login(user);
    }

}

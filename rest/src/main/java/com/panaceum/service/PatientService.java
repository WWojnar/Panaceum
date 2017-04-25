package com.panaceum.service;

import com.panaceum.dao.HistoryDao;
import com.panaceum.dao.PatientDao;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.panaceum.model.User;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

//sciezka do klasy serwisu encji User
@Path("/patient")
public class PatientService {

    private PatientDao patientDao = new PatientDao();
    private HistoryDao historyDao = new HistoryDao();
    
    @GET
    @Path("/getAll/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
        
        return patientDao.getAll(user);
    }
    
    @GET
    @Path("/getById/{id}/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id, @PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
        
        return patientDao.getById(user, id);
    }
    
    @GET
    @Path("/getByPesel/{pesel}/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByPesel(@PathParam("pesel") String pesel, @PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
        
        return patientDao.getByPesel(user, pesel);
    }
    
    @GET
    @Path("/getHistory/{pesel}/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistory (@PathParam("pesel") String pesel, @PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
System.err.println("to");
        return historyDao.getAll(user, pesel);
    }
    
}

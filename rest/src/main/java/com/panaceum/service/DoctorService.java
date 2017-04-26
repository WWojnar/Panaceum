package com.panaceum.service;

import com.panaceum.dao.DoctorDao;
import com.panaceum.model.User;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/doctor")
public class DoctorService {

    private DoctorDao doctorDao = new DoctorDao();

    @GET
    @Path("/getAll/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);

        return doctorDao.getAll(user);
    }

    @GET
    @Path("/getById/{id}/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id, @PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);

        return doctorDao.getById(user, id);
    }

    @GET
    @Path("/getPrescriptions/{doctorId}/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrescriptions(@PathParam("doctorId") int doctorId, @PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);

        return doctorDao.getPrescriptions(user, doctorId);
    }

}

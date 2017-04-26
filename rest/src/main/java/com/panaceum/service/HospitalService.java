package com.panaceum.service;

import com.panaceum.dao.HospitalDao;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hospital")
public class HospitalService {

    private HospitalDao hospitalDao = new HospitalDao();
    
    @GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        return hospitalDao.getById(id);
    }

}

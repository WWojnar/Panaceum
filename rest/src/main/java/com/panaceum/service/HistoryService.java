package com.panaceum.service;

import com.panaceum.dao.HistoryDao;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/history")
public class HistoryService {
    
    private HistoryDao historyDao = new HistoryDao();
    
    @POST
    @Path("/test")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(String incomingData) {
        return Response.ok().entity("[{\"id\":1,\"age\":0,\"pesel\":\"55555555555\",\"firstName\":\"Pacjent\",\"lastName\":\"NumerJeden\",\"addressId\":0},{\"id\":2,\"age\":0,\"pesel\":\"123231322\",\"firstName\":\"Pacjent\",\"lastName\":\"NumerDwa\",\"addressId\":0}]").build();
    }
    
}

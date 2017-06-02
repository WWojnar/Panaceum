package com.panaceum.service;

import com.panaceum.dao.HistoryDao;
import com.panaceum.dao.TherapyPlanDao;
import com.panaceum.model.TherapyPlan;
import com.panaceum.model.User;
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
    
    private TherapyPlanDao therapyPlanDao = new TherapyPlanDao();
    
    @GET
    @Path("/getTherapyPlans/{historyId}/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("historyId") int historyId, @PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
        
        return therapyPlanDao.getAll(user, historyId);
    }
    
}

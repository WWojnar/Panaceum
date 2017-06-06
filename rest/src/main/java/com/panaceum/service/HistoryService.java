package com.panaceum.service;

import com.panaceum.dao.ExcerptDao;
import com.panaceum.dao.HistoryDao;
import com.panaceum.dao.TherapyPlanDao;
import com.panaceum.model.Excerpt;
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
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/history")
public class HistoryService {
    
    private HistoryDao historyDao = new HistoryDao();
    private TherapyPlanDao therapyPlanDao = new TherapyPlanDao();
    private ExcerptDao excerptDao = new ExcerptDao();
    
    @GET
    @Path("/getTherapyPlans/{historyId}/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTherapyPlans(@PathParam("historyId") int historyId, @PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
        
        return therapyPlanDao.getAllByHistoryId(user, historyId);
    }
    
    @GET
    @Path("/getExcerpt/{historyId}/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExcerpt(@PathParam("historyId") int historyId, @PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
        
        return excerptDao.getByHistoryId(user, historyId);
    }
    
    @POST
    @Path("/addTherapyPlan")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTherapyPlan(String incomingData) {
        User user = new User();
        TherapyPlan therapyPlan = new TherapyPlan();
        
        String login,
                token,
                examinations,
                orders;
        int historyId;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            login = json.getString("login");
            token = json.getString("token");
            examinations = json.getString("examination");
            orders = json.getString("orders");
            historyId = json.getInt("historyId");
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }
        
        user.setLogin(login);
        user.setToken(token);
        therapyPlan.setExaminations(examinations);
        therapyPlan.setOrders(orders);
        therapyPlan.setHistoryId(historyId);
        
        return therapyPlanDao.add(user, therapyPlan);
    }
    
    @POST
    @Path("/addExcerpt")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addExcerpt(String incomingData) {
        User user = new User();
        Excerpt excerpt = new Excerpt();
        
        String login,
                token,
                recognition,
                recomendations,
                epicrisis;
        int historyId;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            login = json.getString("login");
            token = json.getString("token");
            recognition = json.getString("recognition");
            recomendations = json.getString("recomendations");
            epicrisis = json.getString("epicrisis");
            historyId = json.getInt("historyId");
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }
        
        user.setLogin(login);
        user.setToken(token);
        excerpt.setRecognition(recognition);
        excerpt.setRecomendations(recomendations);
        excerpt.setEpicrisis(epicrisis);
        
        return excerptDao.add(user, excerpt, historyId);
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(String incomingData) {
        User user = new User();
        Excerpt excerpt = new Excerpt();
        
        String  recognition,
                recomendations,
                epicrisis;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            recognition = json.getString("recognition");
            recomendations = json.getString("recomendations");
            epicrisis = json.getString("epicrisis");
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }
        
        excerpt.setRecognition(recognition);
        excerpt.setRecomendations(recomendations);
        excerpt.setEpicrisis(epicrisis);
        
        return excerptDao.update(user, excerpt);
    }
    
    @POST
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(String incomingData, @PathParam("id") int id) {
        User user = new User();

        String login,
                token;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            login = json.getString("login");
            token = json.getString("token");
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }
        
        user.setLogin(login);
        user.setToken(token);
        
        return historyDao.delete(user, id);
    }
    
}

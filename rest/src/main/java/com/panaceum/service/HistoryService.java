package com.panaceum.service;

import com.panaceum.dao.ExcerptDao;
import com.panaceum.dao.HistoryDao;
import com.panaceum.dao.TherapyPlanDao;
import com.panaceum.model.Excerpt;
import com.panaceum.model.History;
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
        History history = new History();
        
        String login,
                token;
        int historyId;
        String nurseCard,
                finalCard,
                pressure,
                pulse;
        float temperature,
                mass,
                height;
        String content,
                idc10;
        boolean firstIllnes;
        String symptoms,
                interviewRecognition,
                treatment;
        boolean[] factor = new boolean[30];
        String factor5Note,
                factor6Note,
                factor7Note,
                notepad;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            login = json.getString("login");
            token = json.getString("token");
            historyId = json.getInt("historyId");
            nurseCard = json.getString("nurseCard");
            finalCard = json.getString("finalCard");
            pressure = json.getString("pressure");
            pulse = json.getString("pulse");
            temperature = (float)json.getDouble("temperature");
            mass = (float)json.getDouble("mass");
            height = (float)json.getDouble("height");
            content = json.getString("content");
            idc10 = json.getString("idc10");
            firstIllnes = json.getBoolean("firstIllnes");
            symptoms = json.getString("symptoms");
            interviewRecognition = json.getString("interviewRecognition");
            treatment = json.getString("treatment");
            for (int i = 1; i < 6; i++) factor[i - 1] = json.getBoolean("factor" + i);
            factor5Note = json.getString("factor5Note");
            factor[5] = json.getBoolean("factor6");
            factor6Note = json.getString("factor6Note");
            factor[6] = json.getBoolean("factor7");
            factor7Note = json.getString("factor7Note");
            for (int i = 8; i < 31; i++) factor[i - 1] = json.getBoolean("factor" + i);
            notepad = json.getString("notepad");
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }
        
        user.setLogin(login);
        user.setToken(token);
        history.setId(historyId);
        history.setNurseCard(nurseCard);
        history.setFinalCard(finalCard);
        history.setPressure(pressure);
        history.setPulse(pulse);
        history.setTemperature(temperature);
        history.setMass(mass);
        history.setHeight(height);
        history.setContent(content);
        history.setIdc10(idc10);
        history.setFirstIllnes(firstIllnes);
        history.setSymptoms(symptoms);
        history.setInterviewRecognition(interviewRecognition);
        history.setTreatment(treatment);System.err.println(history.getTreatment());
        history.setFactor1(factor[0]);
        history.setFactor2(factor[1]);
        history.setFactor3(factor[2]);
        history.setFactor4(factor[3]);
        history.setFactor5(factor[4]);
        history.setFactor5Note(factor5Note);
        history.setFactor6(factor[5]);
        history.setFactor6Note(factor6Note);
        history.setFactor7(factor[6]);
        history.setFactor7Note(factor7Note);
        history.setFactor8(factor[7]);
        history.setFactor9(factor[8]);
        history.setFactor10(factor[9]);
        history.setFactor11(factor[10]);
        history.setFactor12(factor[11]);
        history.setFactor13(factor[12]);
        history.setFactor14(factor[13]);
        history.setFactor15(factor[14]);
        history.setFactor16(factor[15]);
        history.setFactor17(factor[16]);
        history.setFactor18(factor[17]);
        history.setFactor19(factor[18]);
        history.setFactor20(factor[19]);
        history.setFactor21(factor[20]);
        history.setFactor22(factor[21]);
        history.setFactor23(factor[22]);
        history.setFactor24(factor[23]);
        history.setFactor25(factor[24]);
        history.setFactor26(factor[25]);
        history.setFactor27(factor[26]);
        history.setFactor28(factor[27]);
        history.setFactor29(factor[28]);
        history.setFactor30(factor[29]);
        history.setNotepad(notepad);
        
        return historyDao.update(user, history);
    }
    
    @POST
    @Path("/updateTherapyPlan")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTherapyPlan(String incomingData) {
        User user = new User();
        TherapyPlan therapyPlan = new TherapyPlan();
        
        String login,
                token;
        int therapyPlanId;
        String examinations,
                orders;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            login = json.getString("login");
            token = json.getString("token");
            therapyPlanId = json.getInt("therapyPlanId");
            examinations = json.getString("examination");
            orders = json.getString("orders");
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }
        
        user.setLogin(login);
        user.setToken(token);
        therapyPlan.setId(therapyPlanId);
        therapyPlan.setExaminations(examinations);
        therapyPlan.setOrders(orders);
        
        return therapyPlanDao.update(user, therapyPlan);
    }
    
    @POST
    @Path("/updateExcerpt")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateExcerpt(String incomingData) {
        User user = new User();
        Excerpt excerpt = new Excerpt();
        
        String login,
                token;
        int excerptId;
        String recognition,
                recomendations,
                epicrisis;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            login = json.getString("login");
            token = json.getString("token");
            excerptId = json.getInt("excerptId");
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
        
        user.setLogin(login);
        user.setToken(token);
        excerpt.setId(excerptId);
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
    
    @POST
    @Path("/deleteTherapyPlan/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteTherapyPlan(String incomingData, @PathParam("id") int id) {
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
        
        return therapyPlanDao.delete(user, id);
    }
    
    @POST
    @Path("/deleteExcerpt/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteExcerpt(String incomingData, @PathParam("id") int id) {
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
        
        return excerptDao.delete(user, id);
    }
    
}

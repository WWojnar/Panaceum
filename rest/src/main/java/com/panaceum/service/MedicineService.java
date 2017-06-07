package com.panaceum.service;

import com.panaceum.dao.MedicineDao;
import com.panaceum.model.Medicine;
import com.panaceum.model.User;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/medicine")
public class MedicineService {
    
    private MedicineDao medicineDao = new MedicineDao();
    
    @GET
    @Path("/getAll/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
        
        return medicineDao.getAll(user);
    }
    
    @GET
    @Path("/getById/{id}/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id, @PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
        
        return medicineDao.getById(user, id);
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String incomingData) {
        User user = new User();
        Medicine medicine = new Medicine();
        
        String login,
                token,
                name,
                activeSubstance;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            login = json.getString("login");
            token = json.getString("token");
            name = json.getString("name");
            activeSubstance = json.getString("activeSubstance");
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }
        
        user.setLogin(login);
        user.setToken(token);
        medicine.setName(name);
        medicine.setActiveSubstance(activeSubstance);
        
        return medicineDao.add(user, medicine);
    }
 
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(String incomingData) {
        User user = new User();
        Medicine medicine = new Medicine();
        
        String login,
                token;
        int medicineId;
        String name,
                activeSubstance;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            login = json.getString("login");
            token = json.getString("token");
            medicineId = json.getInt("medicineId");
            name = json.getString("name");
            activeSubstance = json.getString("activeSubstance");
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }
        
        user.setLogin(login);
        user.setToken(token);
        medicine.setId(medicineId);
        medicine.setName(name);
        medicine.setActiveSubstance(activeSubstance);
        
        return medicineDao.update(user, medicine);
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
        
        return medicineDao.delete(user, id);
    }
    
}

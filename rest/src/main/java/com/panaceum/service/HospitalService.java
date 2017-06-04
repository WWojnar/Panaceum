package com.panaceum.service;

import com.panaceum.dao.HospitalDao;
import com.panaceum.model.Hospital;
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

@Path("/hospital")
public class HospitalService {

    private HospitalDao hospitalDao = new HospitalDao();
    
    @GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        return hospitalDao.getById(id);
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String incomingData) {
        User user = new User();
        Hospital hospital = new Hospital();
        
        String login,
                token,
                name,
                regon,
                phone,
                city,
                street,
                buildingNumber,
                flatNumber,
                zipCode;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            login = json.getString("login");
            token = json.getString("token");
            name = json.getString("name");
            regon = json.getString("REGON");
            phone = json.getString("phone");
            city = json.getString("city");
            street = json.getString("street");
            buildingNumber = json.getString("buildingNumber");
            flatNumber = json.getString("flatNumber");
            zipCode = json.getString("zipCode");
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }
        
        user.setLogin(login);
        user.setToken(token);
        hospital.setName(name);
        hospital.setRegon(regon);
        hospital.setPhone(phone);
        hospital.setCity(city);
        hospital.setStreet(street);
        hospital.setBuildingNumber(buildingNumber);
        hospital.setFlatNumber(flatNumber);
        hospital.setZipCode(zipCode);
        
        return hospitalDao.add(user, hospital);
    }
    
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(String incomingData) {
        User user = new User();
        Hospital hospital = new Hospital();
        
        String login,
                token;
        int hospitalId;
        String name,
                regon,
                phone,
                city,
                street,
                buildingNumber,
                flatNumber,
                zipCode;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            login = json.getString("login");
            token = json.getString("token");
            hospitalId = json.getInt("hospitalId");
            name = json.getString("name");
            regon = json.getString("REGON");
            phone = json.getString("phone");
            city = json.getString("city");
            street = json.getString("street");
            buildingNumber = json.getString("buildingNumber");
            flatNumber = json.getString("flatNumber");
            zipCode = json.getString("zipCode");
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }
        
        user.setLogin(login);
        user.setToken(token);
        hospital.setId(hospitalId);
        hospital.setName(name);
        hospital.setRegon(regon);
        hospital.setPhone(phone);
        hospital.setCity(city);
        hospital.setStreet(street);
        hospital.setBuildingNumber(buildingNumber);
        hospital.setFlatNumber(flatNumber);
        hospital.setZipCode(zipCode);
        
        return hospitalDao.update(user, hospital);
    }

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(String incomingData) {
        User user = new User();
        Hospital hospital = new Hospital();
        
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
        
        return hospitalDao.delete(user, hospital);
    }
    
}

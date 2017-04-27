package com.panaceum.service;

import com.panaceum.dao.PrescriptionDao;
import com.panaceum.model.Prescription;
import com.panaceum.model.User;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/prescription")
public class PrescriptionService {
    
    private PrescriptionDao prescriptionDao = new PrescriptionDao();
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String incomingData) {
        User user = new User();
        Prescription prescription = new Prescription();
System.err.println("tu");
        String login;
        String token;
        String dosage;
        String expiryDate;
        String medicineName;
        int doctorId;
        int patientId;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            login = json.getString("login");
            token = json.getString("token");
            dosage = json.getString("dosage");
            expiryDate = json.getString("expiryDate");
            medicineName = json.getString("medicineName");
            doctorId = json.getInt("doctorId");
            patientId = json.getInt("patientId");
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }
        
        user.setLogin(login);
        user.setToken(token);
        prescription.setDosage(dosage);
        prescription.setExpiryDate(expiryDate);
        prescription.setMedicineName(medicineName);
        prescription.setDoctorid(doctorId);
        prescription.setPatientId(patientId);
        
        return prescriptionDao.add(user, prescription);
    }
    
}

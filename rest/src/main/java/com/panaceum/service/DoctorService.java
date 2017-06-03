package com.panaceum.service;

import com.panaceum.dao.DoctorDao;
import com.panaceum.model.Doctor;
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
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String incomingData) {
        User user = new User();
        Doctor doctor = new Doctor();
        
        String login,
                token,
                speciality,
                licenceNumber,
                pesel,
                firstName,
                lastName,
                phone,
                email,
                city,
                street,
                buildingNumber,
                flatNumber,
                zipCode,
                doctorLogin;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            login = json.getString("login");
            token = json.getString("token");
            speciality = json.getString("speciality");
            licenceNumber = json.getString("licenceNumber");
            pesel = json.getString("pesel");
            firstName = json.getString("firstName");
            lastName = json.getString("lastName");
            phone = json.getString("phone");
            email = json.getString("email");
            city = json.getString("city");
            street = json.getString("street");
            buildingNumber = json.getString("buildingNumber");
            flatNumber = json.getString("flatNumber");
            zipCode = json.getString("zipCode");
            doctorLogin = json.getString("doctorLogin");
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }
        
        user.setLogin(login);
        user.setToken(token);
        doctor.setSpeciality(speciality);
        doctor.setLicenceNumber(licenceNumber);
        doctor.setPesel(pesel);
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setPhone(phone);
        doctor.setEmail(email);
        doctor.setCity(city);
        doctor.setStreet(street);
        doctor.setBuildingNumber(buildingNumber);
        doctor.setFlatNumber(flatNumber);
        doctor.setZipCode(zipCode);
        doctor.setLogin(doctorLogin);
        
        return doctorDao.add(user, doctor);
    }
    
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(String incomingData) {
        User user = new User();
        Doctor doctor = new Doctor();
        
        String login,
                token,
                phone,
                email,
                city,
                street,
                buildingNumber,
                flatNumber,
                zipCode;
        
        try {
            JSONObject json = new JSONObject(incomingData);
            
            login = json.getString("login");
            token = json.getString("token");
            phone = json.getString("phone");
            email = json.getString("email");
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
        doctor.setPhone(phone);
        doctor.setEmail(email);
        doctor.setCity(city);
        doctor.setStreet(street);
        doctor.setBuildingNumber(buildingNumber);
        doctor.setFlatNumber(flatNumber);
        doctor.setZipCode(zipCode);
        
        return doctorDao.update(user, doctor);
    }

}

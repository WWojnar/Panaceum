package com.panaceum.service;

import com.panaceum.dao.HistoryDao;
import com.panaceum.dao.PatientDao;
import com.panaceum.model.History;
import com.panaceum.model.Patient;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.panaceum.model.User;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

//sciezka do klasy serwisu encji User
@Path("/patient")
public class PatientService {

    private PatientDao patientDao = new PatientDao();
    private HistoryDao historyDao = new HistoryDao();
    
    @GET
    @Path("/getAll/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
        
        return patientDao.getAll(user);
    }
    
    @GET
    @Path("/getById/{id}/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id, @PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
        
        return patientDao.getById(user, id);
    }
    
    @GET
    @Path("/getByPesel/{pesel}/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByPesel(@PathParam("pesel") String pesel, @PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);
        
        return patientDao.getByPesel(user, pesel);
    }
    
    @GET
    @Path("/getHistory/{pesel}/{login}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistory (@PathParam("pesel") String pesel, @PathParam("login") String login, @PathParam("token") String token) {
        User user = new User();
        user.setLogin(login);
        user.setToken(token);

        return historyDao.getAll(user, pesel);
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String incomingData) {
        User user = new User();
        Patient patient = new Patient();
        
        String login,
                token,
                sex;
        int age;
        String bloodType,
                pesel,
                firstName,
                lastName,
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
            sex = json.getString("sex");
            age = json.getInt("age");
            bloodType = json.getString("bloodType");
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
        } catch (JSONException e) {
            System.err.println(e.toString());
            return Response.status(415).entity("Invalid JSON format").build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.serverError().entity("Unkown error").build();
        }
        
        user.setLogin(login);
        user.setToken(token);
        patient.setSex(sex);
        patient.setAge(age);
        patient.setBloodType(bloodType);
        patient.setPesel(pesel);
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setPhone(phone);
        patient.setEmail(email);
        patient.setCity(city);
        patient.setStreet(street);
        patient.setBuildingNumber(buildingNumber);
        patient.setFlatNumber(flatNumber);
        patient.setZipCode(zipCode);
        
        return patientDao.add(user, patient);
    }
    
    @POST
    @Path("/addHistory")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addHistory(String incomingData) {
        User user = new User();
        History history = new History();
        
        String login,
                token;
        int patientId,
                doctorId,
                hospitalId;
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
            patientId = json.getInt("patientId");
            doctorId = json.getInt("doctorId");
            hospitalId = json.getInt("hospitalId");
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
        history.setPatientId(patientId);
        history.setDoctorId(doctorId);
        history.setHospitalId(hospitalId);
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
        
        return historyDao.add(user, history);
    }
    
}

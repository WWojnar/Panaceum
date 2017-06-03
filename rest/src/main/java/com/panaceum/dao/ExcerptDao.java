package com.panaceum.dao;

import com.google.gson.Gson;
import com.panaceum.model.Excerpt;
import com.panaceum.model.Prescription;
import com.panaceum.model.User;
import com.panaceum.util.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ws.rs.core.Response;

public class ExcerptDao {
    
    private static DatabaseConnection connection = new DatabaseConnection();
    private UserDao userDao = new UserDao();
    
    public Response getByHistoryId(User user, int historyId) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }
        if (!userDao.checkPrivileges(user.getLogin()).equals("doctor")) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        Excerpt excerpt = new Excerpt();
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM excerptView WHERE historyId = " + historyId);

            while (resultSet.next()) {
                excerpt.setId(resultSet.getInt("id"));
                excerpt.setExcerptDate(resultSet.getString("excerptDate"));
                excerpt.setRecognition(resultSet.getString("recognition"));
                excerpt.setRecomendations(resultSet.getString("recomendations"));
                excerpt.setEpicrisis(resultSet.getString("epicrisis"));
                
                resultSet = statement.executeQuery("SELECT * FROM prescriptionView WHERE excerptId = " + excerpt.getId());
                while (resultSet.next()) {
                    Prescription prescription = new Prescription();
                    
                    prescription.setId(resultSet.getInt("prescriptionId"));
                    prescription.setDosage(resultSet.getString("dosage"));
                    prescription.setPrescriptionDate(resultSet.getString("prescriptionDate"));
                    prescription.setExpiryDate(resultSet.getString("expiryDate"));
                    prescription.setMedicineId(resultSet.getInt("medicineId"));
                    prescription.setMedicineName(resultSet.getString("medicineName"));
                    prescription.setActiveSubstance(resultSet.getString("activeSubstance"));
                    prescription.setTherapyPlanId(resultSet.getInt("therapyPlanId"));
                    prescription.setExcerptId(resultSet.getInt("excerptId"));
                    prescription.setDoctorid(resultSet.getInt("doctorId"));
                    prescription.setPatientId(resultSet.getInt("patientId"));
                    prescription.setPatientPesel(resultSet.getString("patientPesel"));
                    prescription.setPatientFirstName(resultSet.getString("patientFirstname"));
                    prescription.setPatientLastName(resultSet.getString("patientLastName"));
                    
                    excerpt.addPrescription(prescription);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }
        connection.closeConnection();

        Gson gson = new Gson();
        return Response.ok(gson.toJson(excerpt)).build();
    }
    
}

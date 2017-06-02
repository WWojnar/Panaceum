package com.panaceum.dao;

import com.google.gson.Gson;
import com.panaceum.model.Prescription;
import com.panaceum.model.TherapyPlan;
import com.panaceum.model.User;
import com.panaceum.util.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

public class TherapyPlanDao {
    
    private static DatabaseConnection connection = new DatabaseConnection();
    private UserDao userDao = new UserDao();
    
    public Response getAll(User user, int historyId) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }
        if (!userDao.checkPrivileges(user.getLogin()).equals("doctor")) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        List<TherapyPlan> therapyPlans = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM therapyPlan WHERE historyId = " + historyId);

            while (resultSet.next()) {
                TherapyPlan therapyPlan = new TherapyPlan();

                therapyPlan.setId(resultSet.getInt("id"));
                therapyPlan.setExaminations(resultSet.getString("examination"));
                therapyPlan.setOrders(resultSet.getString("orders"));
                therapyPlan.setHistoryId(resultSet.getInt("historyId"));
                
                resultSet = statement.executeQuery("SELECT * FROM prescriptionView WHERE therapyPlanId = " + therapyPlan.getId());
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
                    
                    therapyPlan.addPrescription(prescription);
                }

                therapyPlans.add(therapyPlan);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }
        connection.closeConnection();

        Gson gson = new Gson();
        return Response.ok(gson.toJson(therapyPlans)).build();
    }
    
}

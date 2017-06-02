package com.panaceum.dao;

import com.google.gson.Gson;
import com.panaceum.model.History;
import com.panaceum.model.Patient;
import com.panaceum.model.User;
import com.panaceum.util.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

public class HistoryDao {
    
    private static DatabaseConnection connection = new DatabaseConnection();
    private static UserDao userDao = new UserDao();
    
    public Response getAll(User user, String pesel) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        List<History> histories = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM historyView WHERE pesel = '" + pesel + "'");

            while (resultSet.next()) {
                History history = new History();

                history.setId(resultSet.getInt("historyId"));
                history.setNurseCard(resultSet.getString("nurseCard"));
                history.setFinalCard(resultSet.getString("finalCard"));
                history.setPatientId(resultSet.getInt("patientId"));
                history.setPesel(resultSet.getString("pesel"));
                history.setHospitalId(resultSet.getInt("hospitalId"));
                history.setInterviewId(resultSet.getInt("interviewId"));
                history.setInterviewDate(resultSet.getString("interviewDate"));
                history.setIdc10(resultSet.getString("idc10"));
                history.setFirstIllnes(resultSet.getBoolean("firstIllnes"));
                history.setSymptoms(resultSet.getString("symptoms"));
                history.setInterviewRecognition(resultSet.getString("interviewRecognition"));
                history.setTreatment(resultSet.getString("treatment"));
                history.setFirstExaminationId(resultSet.getInt("firstExaminationId"));
                history.setPressure(resultSet.getString("pressure"));
                history.setPulse(resultSet.getString("pulse"));
                history.setTemperature(resultSet.getFloat("temperature"));
                history.setMass(resultSet.getFloat("mass"));
                history.setHeight(resultSet.getFloat("height"));
                history.setContent(resultSet.getString("content"));
                history.setExcerptId(resultSet.getInt("excerptId"));
                history.setExcerptDate(resultSet.getString("excerptDate"));
                history.setExcerptRecognition(resultSet.getString("excerptRecognition"));
                history.setRecomendations(resultSet.getString("recomendations"));
                history.setEpicrisis(resultSet.getString("epicrisis"));
                history.setInfectionCardId(resultSet.getInt("infectionCardId"));
                history.setFactor1(resultSet.getBoolean("factor1"));
                history.setFactor2(resultSet.getBoolean("factor2"));
                history.setFactor3(resultSet.getBoolean("factor3"));
                history.setFactor4(resultSet.getBoolean("factor4"));
                history.setFactor5(resultSet.getBoolean("factor5"));
                history.setFactor5Note(resultSet.getString("factor5Note"));
                history.setFactor6(resultSet.getBoolean("factor6"));
                history.setFactor6Note(resultSet.getString("factor6Note"));
                history.setFactor7(resultSet.getBoolean("factor7"));
                history.setFactor7Note(resultSet.getString("factor7Note"));
                history.setFactor8(resultSet.getBoolean("factor8"));
                history.setFactor9(resultSet.getBoolean("factor9"));
                history.setFactor10(resultSet.getBoolean("factor10"));
                history.setFactor11(resultSet.getBoolean("factor11"));
                history.setFactor12(resultSet.getBoolean("factor12"));
                history.setFactor13(resultSet.getBoolean("factor13"));
                history.setFactor14(resultSet.getBoolean("factor14"));
                history.setFactor15(resultSet.getBoolean("factor15"));
                history.setFactor16(resultSet.getBoolean("factor16"));
                history.setFactor17(resultSet.getBoolean("factor17"));
                history.setFactor18(resultSet.getBoolean("factor18"));
                history.setFactor19(resultSet.getBoolean("factor19"));
                history.setFactor20(resultSet.getBoolean("factor20"));
                history.setFactor21(resultSet.getBoolean("factor21"));
                history.setFactor22(resultSet.getBoolean("factor22"));
                history.setFactor23(resultSet.getBoolean("factor23"));
                history.setFactor24(resultSet.getBoolean("factor24"));
                history.setFactor25(resultSet.getBoolean("factor25"));
                history.setFactor26(resultSet.getBoolean("factor26"));
                history.setFactor27(resultSet.getBoolean("factor27"));
                history.setFactor28(resultSet.getBoolean("factor28"));
                history.setFactor29(resultSet.getBoolean("factor29"));
                history.setFactor30(resultSet.getBoolean("factor30"));
                history.setNotepad(resultSet.getString("notepad"));

                histories.add(history);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }
        connection.closeConnection();
        
        if (histories.get(0).getId() == 0) {
            return Response.status(404).entity("No medical histories found").build();
        }

        Gson gson = new Gson();
        return Response.ok(gson.toJson(histories)).build();
    }
    
    public Response add(User user, History history) {
        if (!userDao.validate(user)) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }
        if (!userDao.checkPrivileges(user.getLogin()).equals("doctor")) {
            return Response.status(403).entity("User doesn't have necessary permissions").build();
        }

        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT addHistory(" + history.getPatientId() + ", " + history.getDoctorId()
                    + ", " + history.getHospitalId() + ", '" + history.getNurseCard() + "', '" + history.getFinalCard()
                    + "', '" + history.getPressure() + "', '" + history.getPulse() + "', " + history.getTemperature()
                    + ", " + history.getMass() + ", " + history.getHeight() + ", '" + history.getContent()
                    + "', '" + history.getIdc10() + "', " + history.isFirstIllnes() + ", '" + history.getSymptoms()
                    + "', '" + history.getInterviewRecognition() + "', '" + history.getTreatment() + "', " + history.isFactor1()
                    + ", " + history.isFactor2() + ", " + history.isFactor3() + ", " + history.isFactor4() + ", " + history.isFactor5()
                    + ", '" + history.getFactor5Note() + "', " + history.isFactor6() + ", '" + history.getFactor6Note()
                    + "', " + history.isFactor7() + ", '" + history.getFactor7Note() + "', " + history.isFactor8()
                    + ", " + history.isFactor9() + ", " + history.isFactor10() + ", " + history.isFactor11() + ", " + history.isFactor12()
                    + ", " + history.isFactor13() + ", " + history.isFactor14() + ", " + history.isFactor15() + ", " + history.isFactor16()
                    + ", " + history.isFactor17() + ", " + history.isFactor18() + ", " + history.isFactor19() + ", " + history.isFactor20()
                    + ", " + history.isFactor21() + ", " + history.isFactor22() + ", " + history.isFactor23() + ", " + history.isFactor24()
                    + ", " + history.isFactor25() + ", " + history.isFactor26() + ", " + history.isFactor27() + ", " + history.isFactor28()
                    + ", " + history.isFactor29() + ", " + history.isFactor30() + ", '" + history.getNotepad() + "')");

            while (resultSet.next()) {
                history.setId(resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            connection.closeConnection();
            return Response.serverError().build();
        }

        connection.closeConnection();

        return Response.ok("{\"historyId\":" + history.getId() + "}").build();
    }
    
}

package com.panaceum.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;

public class Excerpt {
    
    @Id
    private int id;
    private String excerptDate;
    private String recognition;
    private String recomendations;
    private String epicrisis;
    private List<Prescription> prescriptions = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExcerptDate() {
        return excerptDate;
    }

    public void setExcerptDate(String excerptDate) {
        this.excerptDate = excerptDate;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

    public String getRecomendations() {
        return recomendations;
    }

    public void setRecomendations(String recomendations) {
        this.recomendations = recomendations;
    }

    public String getEpicrisis() {
        return epicrisis;
    }

    public void setEpicrisis(String epicrisis) {
        this.epicrisis = epicrisis;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
    
    public void addPrescription(Prescription prescription) {
        this.prescriptions.add(prescription);
    }
    
}

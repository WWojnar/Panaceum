package com.panaceum.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;

public class TherapyPlan {
    
    @Id
    private int id;
    private String examinations;
    private String orders;
    private int historyId;
    private List<Prescription> prescriptions = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExaminations() {
        return examinations;
    }

    public void setExaminations(String examinations) {
        this.examinations = examinations;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
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

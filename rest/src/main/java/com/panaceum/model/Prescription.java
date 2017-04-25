
package com.panaceum.model;


import javax.persistence.Id;

public class Prescription {
    
    @Id
    private int id;
    private String dosage;
    private int medicineId;
    private int therapyPlanId;
    private int excerptId;
    private int doctorid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getTherapyPlanId() {
        return therapyPlanId;
    }

    public void setTherapyPlanId(int therapyPlanId) {
        this.therapyPlanId = therapyPlanId;
    }

    public int getExcerptId() {
        return excerptId;
    }

    public void setExcerptId(int excerptid) {
        this.excerptId = excerptid;
    }

    public int getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(int doctorid) {
        this.doctorid = doctorid;
    }

    
}

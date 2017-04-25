package com.panaceum.model;

import javax.persistence.Id;

public class History {

    @Id
    private int id;
    private String nurseCard;
    private String finalCard;
    private int patientId;
    private String pesel;
    private int doctorId;
    private int hospitalId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNurseCard() {
        return nurseCard;
    }

    public void setNurseCard(String nurseCard) {
        this.nurseCard = nurseCard;
    }

    public String getFinalCard() {
        return finalCard;
    }

    public void setFinalCard(String finalCard) {
        this.finalCard = finalCard;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
    
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    //public class Interview {

        private int interviewId;
        private String interviewDate;
        private String idc10;
        private boolean firstIllnes;
        private String symptoms;
        private String interviewRecognition;
        private String treatment;

        public int getInterviewId() {
            return interviewId;
        }

        public void setInterviewId(int interviewId) {
            this.interviewId = interviewId;
        }

        public String getInterviewDate() {
            return interviewDate;
        }

        public void setInterviewDate(String interviewDate) {
            this.interviewDate = interviewDate;
        }

        public String getIdc10() {
            return idc10;
        }

        public void setIdc10(String idc10) {
            this.idc10 = idc10;
        }

        public boolean isFirstIllnes() {
            return firstIllnes;
        }

        public void setFirstIllnes(boolean firstIllnes) {
            this.firstIllnes = firstIllnes;
        }

        public String getSymptoms() {
            return symptoms;
        }

        public void setSymptoms(String symptoms) {
            this.symptoms = symptoms;
        }

        public String getInterviewRecognition() {
            return interviewRecognition;
        }

        public void setInterviewRecognition(String recognition) {
            this.interviewRecognition = recognition;
        }

        public String getTreatment() {
            return treatment;
        }

        public void setTreatment(String treatment) {
            this.treatment = treatment;
        }

    //}

    //private class FirstExamination {

        private int firstExaminationId;
        private String pressure;
        private String pulse;
        private float temperature;
        private float mass;
        private float height;
        private String content;

        public int getFirstExaminationId() {
            return firstExaminationId;
        }

        public void setFirstExaminationId(int firstExaminationId) {
            this.firstExaminationId = firstExaminationId;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getPulse() {
            return pulse;
        }

        public void setPulse(String pulse) {
            this.pulse = pulse;
        }

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }

        public float getMass() {
            return mass;
        }

        public void setMass(float mass) {
            this.mass = mass;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    //}

    //private class Excerpt {

        private int excerptId;
        private String excerptDate;
        private String excerptRecognition;
        private String recomendations;
        private String epicrisis;

        public int getExcerptId() {
            return excerptId;
        }

        public void setExcerptId(int excerptId) {
            this.excerptId = excerptId;
        }

        public String getExcerptDate() {
            return excerptDate;
        }

        public void setExcerptDate(String excerptDate) {
            this.excerptDate = excerptDate;
        }

        public String getExcerptRecognition() {
            return excerptRecognition;
        }

        public void setExcerptRecognition(String recognition) {
            this.excerptRecognition = recognition;
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

    //}
    
    //private class InfectionCard {
        
        private int infectionCardId;
        private boolean factor1;
        private boolean factor2;
        private boolean factor3;
        private boolean factor4;
        private boolean factor5;
        private String factor5Note;
        private boolean factor6;
        private String factor6Note;
        private boolean factor7;
        private String factor7Note;
        private boolean factor8;
        private boolean factor9;
        private boolean factor10;
        private boolean factor11;
        private boolean factor12;
        private boolean factor13;
        private boolean factor14;
        private boolean factor15;
        private boolean factor16;
        private boolean factor17;
        private boolean factor18;
        private boolean factor19;
        private boolean factor20;
        private boolean factor21;
        private boolean factor22;
        private boolean factor23;
        private boolean factor24;
        private boolean factor25;
        private boolean factor26;
        private boolean factor27;
        private boolean factor28;
        private boolean factor29;
        private boolean factor30;
        private String notepad;

        public int getInfectionCardId() {
            return infectionCardId;
        }

        public void setInfectionCardId(int infectionCardId) {
            this.infectionCardId = infectionCardId;
        }

        public boolean isFactor1() {
            return factor1;
        }

        public void setFactor1(boolean factor1) {
            this.factor1 = factor1;
        }

        public boolean isFactor2() {
            return factor2;
        }

        public void setFactor2(boolean factor2) {
            this.factor2 = factor2;
        }

        public boolean isFactor3() {
            return factor3;
        }

        public void setFactor3(boolean factor3) {
            this.factor3 = factor3;
        }

        public boolean isFactor4() {
            return factor4;
        }

        public void setFactor4(boolean factor4) {
            this.factor4 = factor4;
        }

        public boolean isFactor5() {
            return factor5;
        }

        public void setFactor5(boolean factor5) {
            this.factor5 = factor5;
        }

        public String getFactor5Note() {
            return factor5Note;
        }

        public void setFactor5Note(String factor5Note) {
            this.factor5Note = factor5Note;
        }

        public boolean isFactor6() {
            return factor6;
        }

        public void setFactor6(boolean factor6) {
            this.factor6 = factor6;
        }

        public String getFactor6Note() {
            return factor6Note;
        }

        public void setFactor6Note(String factor6Note) {
            this.factor6Note = factor6Note;
        }

        public boolean isFactor7() {
            return factor7;
        }

        public void setFactor7(boolean factor7) {
            this.factor7 = factor7;
        }

        public String getFactor7Note() {
            return factor7Note;
        }

        public void setFactor7Note(String factor7Note) {
            this.factor7Note = factor7Note;
        }

        public boolean isFactor8() {
            return factor8;
        }

        public void setFactor8(boolean factor8) {
            this.factor8 = factor8;
        }

        public boolean isFactor9() {
            return factor9;
        }

        public void setFactor9(boolean factor9) {
            this.factor9 = factor9;
        }

        public boolean isFactor10() {
            return factor10;
        }

        public void setFactor10(boolean factor10) {
            this.factor10 = factor10;
        }

        public boolean isFactor11() {
            return factor11;
        }

        public void setFactor11(boolean factor11) {
            this.factor11 = factor11;
        }

        public boolean isFactor12() {
            return factor12;
        }

        public void setFactor12(boolean factor12) {
            this.factor12 = factor12;
        }

        public boolean isFactor13() {
            return factor13;
        }

        public void setFactor13(boolean factor13) {
            this.factor13 = factor13;
        }

        public boolean isFactor14() {
            return factor14;
        }

        public void setFactor14(boolean factor14) {
            this.factor14 = factor14;
        }

        public boolean isFactor15() {
            return factor15;
        }

        public void setFactor15(boolean factor15) {
            this.factor15 = factor15;
        }

        public boolean isFactor16() {
            return factor16;
        }

        public void setFactor16(boolean factor16) {
            this.factor16 = factor16;
        }

        public boolean isFactor17() {
            return factor17;
        }

        public void setFactor17(boolean factor17) {
            this.factor17 = factor17;
        }

        public boolean isFactor18() {
            return factor18;
        }

        public void setFactor18(boolean factor18) {
            this.factor18 = factor18;
        }

        public boolean isFactor19() {
            return factor19;
        }

        public void setFactor19(boolean factor19) {
            this.factor19 = factor19;
        }

        public boolean isFactor20() {
            return factor20;
        }

        public void setFactor20(boolean factor20) {
            this.factor20 = factor20;
        }

        public boolean isFactor21() {
            return factor21;
        }

        public void setFactor21(boolean factor21) {
            this.factor21 = factor21;
        }

        public boolean isFactor22() {
            return factor22;
        }

        public void setFactor22(boolean factor22) {
            this.factor22 = factor22;
        }

        public boolean isFactor23() {
            return factor23;
        }

        public void setFactor23(boolean factor23) {
            this.factor23 = factor23;
        }

        public boolean isFactor24() {
            return factor24;
        }

        public void setFactor24(boolean factor24) {
            this.factor24 = factor24;
        }

        public boolean isFactor25() {
            return factor25;
        }

        public void setFactor25(boolean factor25) {
            this.factor25 = factor25;
        }

        public boolean isFactor26() {
            return factor26;
        }

        public void setFactor26(boolean factor26) {
            this.factor26 = factor26;
        }

        public boolean isFactor27() {
            return factor27;
        }

        public void setFactor27(boolean factor27) {
            this.factor27 = factor27;
        }

        public boolean isFactor28() {
            return factor28;
        }

        public void setFactor28(boolean factor28) {
            this.factor28 = factor28;
        }

        public boolean isFactor29() {
            return factor29;
        }

        public void setFactor29(boolean factor29) {
            this.factor29 = factor29;
        }

        public boolean isFactor30() {
            return factor30;
        }

        public void setFactor30(boolean factor30) {
            this.factor30 = factor30;
        }

        public String getNotepad() {
            return notepad;
        }

        public void setNotepad(String notepad) {
            this.notepad = notepad;
        }
        
    //}

}

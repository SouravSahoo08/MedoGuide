package com.example.medoguide.Model;

/*
* map.put("medicineName",mName);
            map.put("doseType", doseType);
            map.put("time",mTime);
            map.put("no_of_doses", mDoseno);
            map.put("interval", "everyday");
* */

public class MedicineDataFB {
    private String medicineName;
    private String doseType;
    private String time;
    private String no_of_doses;
    private String interval;

    public MedicineDataFB() {
    }

    public MedicineDataFB(String medicineName, String doseType, String time, String no_of_doses, String interval) {
        this.medicineName = medicineName;
        this.doseType = doseType;
        this.time = time;
        this.no_of_doses = no_of_doses;
        this.interval = interval;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDoseType() {
        return doseType;
    }

    public void setDoseType(String doseType) {
        this.doseType = doseType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNo_of_doses() {
        return no_of_doses;
    }

    public void setNo_of_doses(String no_of_doses) {
        this.no_of_doses = no_of_doses;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}

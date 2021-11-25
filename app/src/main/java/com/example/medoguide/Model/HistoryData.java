package com.example.medoguide.Model;
/*
* map.put("consumeDate",currentDate);
           map.put("consumeTime",currentTime);
           map.put("medicine",data.getMedicineName());
           map.put("no_of_dose",data.getNo_of_doses());
 */
public class HistoryData {

    private String consumeDate;
    private String consumeTime;
    private String medicine;
    private String no_of_dose;
    private String doseType;

    public HistoryData() {
    }

    public HistoryData(String consumeDate, String consumeTime, String medicine, String no_of_dose, String doseType) {
        this.consumeDate = consumeDate;
        this.consumeTime = consumeTime;
        this.medicine = medicine;
        this.no_of_dose = no_of_dose;
        this.doseType = doseType;
    }

    public String getDoseType() {
        return doseType;
    }

    public void setDoseType(String doseType) {
        this.doseType = doseType;
    }

    public String getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(String consumeDate) {
        this.consumeDate = consumeDate;
    }

    public String getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(String consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getNo_of_dose() {
        return no_of_dose;
    }

    public void setNo_of_dose(String no_of_dose) {
        this.no_of_dose = no_of_dose;
    }
}

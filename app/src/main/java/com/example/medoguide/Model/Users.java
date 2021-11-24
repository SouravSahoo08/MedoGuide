package com.example.medoguide.Model;
/*
* map.put("phoneNo", phoneNo);
*  map.put("name", patientName.getText().toString());
                        map.put("age", patientAge.getText().toString());
                        map.put("gender", patientGender.getText().toString());
                        map.put("bloodGrp", patientBldGrp.getText().toString());
* */
public class Users {
    private String name;
    private String phoneNo;
    private String age;
    private String gender;
    private String bloodGrp;

    public Users() {
    }

    public Users(String name, String phoneNo, String age, String gender, String bloodGrp) {
        //this.type = type;
        this.name = name;
        this.phoneNo = phoneNo;
        this.age = age;
        this.gender = gender;
        this.bloodGrp = bloodGrp;
    }

    /*public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGrp() {
        return bloodGrp;
    }

    public void setBloodGrp(String bloodGrp) {
        this.bloodGrp = bloodGrp;
    }
}

package com.example.medoguide.Model;

/*
* map.put("phoneNo", phoneNo);
*  map.put("name", patientName.getText().toString());
                        map.put("age", patientAge.getText().toString());
                        map.put("gender", patientGender.getText().toString());
                        map.put("bloodGrp", patientBldGrp.getText().toString());
* */
public class Doctors {
    private String name;
    private String phoneNo;
    private String age;
    private String gender;
    private String bloodGrp;
    private String speciality;
    private String experience;

    public Doctors() {
    }

    public Doctors(String name, String phoneNo, String age, String gender, String bloodGrp, String speciality, String experience) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.age = age;
        this.gender = gender;
        this.bloodGrp = bloodGrp;
        this.speciality = speciality;
        this.experience = experience;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
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

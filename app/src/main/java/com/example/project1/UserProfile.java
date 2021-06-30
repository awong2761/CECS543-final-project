package com.example.project1;

public class UserProfile {

    public String feetNum;
    public String inchNum;
    public String curWeight;
    public String goalWeight;
    public String aLevel;
    public String gender;
    public String caloriesLeft;
    public String age;
    public String profilePic;

    public UserProfile() {

    }

    public UserProfile(String feetNum, String inchNum, String curWeight, String gWeight, String aLevel, String gender, String caloriesLeft, String age, String profilePic){
        this.feetNum = feetNum;
        this.inchNum = inchNum;
        this.curWeight = curWeight;
        this.aLevel = aLevel;
        this.gender = gender;
        this.goalWeight = gWeight;
        this.caloriesLeft = caloriesLeft;
        this.age = age;
        this.profilePic = profilePic;
    }
    public String getProfilePic(){ return profilePic; }

    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }

    public String  getAge(){ return age; }

    public void setAge(String age) { this.age = age;}

    public String getCaloriesLeft(){ return caloriesLeft;}

    public void setCaloriesLeft(String caloriesLeft){ this.caloriesLeft = caloriesLeft; }

    public String getFeetNum() {
        return feetNum;
    }

    public void setFeetNum(String feetNum) {
        this.feetNum = feetNum;
    }

    public String getInchNum() {
        return inchNum;
    }

    public void setInchNum(String inchNum) {
        this.inchNum = inchNum;
    }

    public String getCurWeight() {
        return curWeight;
    }

    public void setCurWeight(String curWeight) {
        this.curWeight = curWeight;
    }

    public String getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(String goalWeight) {
        this.goalWeight = goalWeight;
    }

    public String getaLevel() {
        return aLevel;
    }

    public void setaLevel(String aLevel) {
        this.aLevel = aLevel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

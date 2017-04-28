package com.samir.andrew.orchestra.Data;

/**
 * Created by andre on 15-Apr-17.
 */

public class UnitDataSingleton {

    private static UnitDataSingleton mInstance = null;

    private String englishName,arabicName,id,mobile,mail,projectName,reservationDate,unitCode,unitTotalPrice,unitType;

    private UnitDataSingleton(){
    }

    public static UnitDataSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new UnitDataSingleton();
        }
        return mInstance;
    }

    public static UnitDataSingleton getmInstance() {
        return mInstance;
    }

    public static void setmInstance(UnitDataSingleton mInstance) {
        UnitDataSingleton.mInstance = mInstance;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitTotalPrice() {
        return unitTotalPrice;
    }

    public void setUnitTotalPrice(String unitTotalPrice) {
        this.unitTotalPrice = unitTotalPrice;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
}

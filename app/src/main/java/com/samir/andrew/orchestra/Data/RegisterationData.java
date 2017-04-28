package com.samir.andrew.orchestra.Data;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by andre on 31-Mar-17.
 */

@IgnoreExtraProperties
public class RegisterationData {

    public String displayName;
    public String email;
    public String mobileNumber;
    public String birthDate;


    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


    public RegisterationData() {
    }

    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public void setMail(String mail) {
        this.email = mail;
    }

    public void setMobile(String mobile) {
        this.mobileNumber = mobile;
    }

}

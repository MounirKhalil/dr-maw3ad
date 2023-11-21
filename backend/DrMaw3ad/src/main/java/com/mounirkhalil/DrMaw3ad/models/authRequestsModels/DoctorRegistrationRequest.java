package com.mounirkhalil.DrMaw3ad.models.authRequestsModels;


public class DoctorRegistrationRequest extends UserRegistrationRequest{

    private String specialty;

    private Integer genAppDur;

    private String genWhpd;



    public String getSpecialty() {
        return specialty;
    }


    public Integer getGenAppDur() {
        return genAppDur;
    }

    public String getGenWhpd() {
        return genWhpd;
    }


}
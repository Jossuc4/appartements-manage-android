package com.app.appli.Entity;

import androidx.annotation.NonNull;

public class Appartement {

    private String numApp;
    private String designation;
    private Double loyer;
    public Appartement(){}
    public Appartement(String num, String design, Double rent){
        numApp = num;
        designation = design;
        loyer = rent;
    }


    public String getNumApp() {
        return numApp;
    }

    public void setNumApp(String numApp) {
        this.numApp = numApp;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getLoyer() {
        return loyer;
    }

    public void setLoyer(Double loyer) {
        this.loyer = loyer;
    }

    @NonNull
    @Override
    public String toString(){
        return "{\"numApp\":\""+getNumApp()+"\",\"designation\":\""+getDesignation()+"\",\"loyer\":\""+getLoyer()+"\"}";
    }
}

package com.amit.appointmentBooking.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiseaseDetailsModel implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("short_description")
    @Expose
    private String short_description;

    @SerializedName("long_description")
    @Expose
    private String long_description;

    @SerializedName("institutions")
    @Expose
    private List<Hospital> hospitals = null;

    private List<Symptoms> symptoms = null;

    private ArrayList<Solutions> solutions = null;

    public String getTitle() {
        return title;
    }

    public String getShort_description() {
        return short_description;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public String getLong_description() {
        return long_description;
    }

    public List<Symptoms> getSymptoms() {
        return symptoms;
    }

    public ArrayList<Solutions> getSolutions() {
        return solutions;
    }

}

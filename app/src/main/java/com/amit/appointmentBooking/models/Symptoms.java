package com.amit.appointmentBooking.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Symptoms implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("symptom")
    @Expose
    private String symptom;

    public String getSymptom() {
        return symptom;
    }

}

package com.amit.appointmentBooking.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recommendation {

    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("recommendservice")
    @Expose
    private List<Recommendservice> recommendservice = null;

    public String getLocation() {
        return location;
    }

    public List<Recommendservice> getRecommendservice() {
        return recommendservice;
    }

}

package com.amit.appointmentBooking.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Solutions implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("solution")
    @Expose
    private String solution;

    public String getSolution() {
        return solution;
    }

}

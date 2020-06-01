package com.amit.appointmentBooking.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Option implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private long price;
    @SerializedName("description")
    @Expose
    private String description;

    public String getTitle() {
        return title;
    }

    public long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

}

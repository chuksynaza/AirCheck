package com.example.sammybobo.aircheck.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationModel {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("temperature")
    @Expose
    private Double temperature;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The temperature
     */
    public Double getTemperature() {
        return temperature;
    }

    /**
     *
     * @param temperature
     * The temperature
     */
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    /**
     *
     * @return
     * The humidity
     */
    public Integer getHumidity() {
        return humidity;
    }

    /**
     *
     * @param humidity
     * The humidity
     */
    public void setHumidity(Integer humidity)
    {
        this.humidity = humidity;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
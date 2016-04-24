package com.example.sammybobo.aircheck.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Agbede on 23/04/2016.
 */
public class CitySearch {

    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("tempmax")
    @Expose
    private Double tempmax;
    @SerializedName("tempmin")
    @Expose
    private Double tempmin;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("description")
    @Expose
    private String description;

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
     * The tempmax
     */
    public Double getTempmax() {
        return tempmax;
    }

    /**
     *
     * @param tempmax
     * The tempmax
     */
    public void setTempmax(Double tempmax) {
        this.tempmax = tempmax;
    }

    /**
     *
     * @return
     * The tempmin
     */
    public Double getTempmin() {
        return tempmin;
    }

    /**
     *
     * @param tempmin
     * The tempmin
     */
    public void setTempmin(Double tempmin) {
        this.tempmin = tempmin;
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
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
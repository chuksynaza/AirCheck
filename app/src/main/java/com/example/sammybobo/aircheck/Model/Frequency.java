package com.example.sammybobo.aircheck.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Agbede on 23/04/2016.
 */

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Frequency {

    @SerializedName("cough_intensity")
    @Expose
    private String coughIntensity;
    @SerializedName("wheezing")
    @Expose
    private String wheezing;
    @SerializedName("sneezing")
    @Expose
    private String sneezing;
    @SerializedName("itching_eyes")
    @Expose
    private String itchingEyes;
    @SerializedName("shortness_of_breath_intensity")
    @Expose
    private String shortnessOfBreathIntensity;
    @SerializedName("nasal_obstruction")
    @Expose
    private String nasalObstruction;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("FREQUENCY")
    @Expose
    private String FREQUENCY;

    /**
     *
     * @return
     * The coughIntensity
     */
    public String getCoughIntensity() {
        return coughIntensity;
    }

    /**
     *
     * @param coughIntensity
     * The cough_intensity
     */
    public void setCoughIntensity(String coughIntensity) {
        this.coughIntensity = coughIntensity;
    }

    /**
     *
     * @return
     * The wheezing
     */
    public String getWheezing() {
        return wheezing;
    }

    /**
     *
     * @param wheezing
     * The wheezing
     */
    public void setWheezing(String wheezing) {
        this.wheezing = wheezing;
    }

    /**
     *
     * @return
     * The sneezing
     */
    public String getSneezing() {
        return sneezing;
    }

    /**
     *
     * @param sneezing
     * The sneezing
     */
    public void setSneezing(String sneezing) {
        this.sneezing = sneezing;
    }

    /**
     *
     * @return
     * The itchingEyes
     */
    public String getItchingEyes() {
        return itchingEyes;
    }

    /**
     *
     * @param itchingEyes
     * The itching_eyes
     */
    public void setItchingEyes(String itchingEyes) {
        this.itchingEyes = itchingEyes;
    }

    /**
     *
     * @return
     * The shortnessOfBreathIntensity
     */
    public String getShortnessOfBreathIntensity() {
        return shortnessOfBreathIntensity;
    }

    /**
     *
     * @param shortnessOfBreathIntensity
     * The shortness_of_breath_intensity
     */
    public void setShortnessOfBreathIntensity(String shortnessOfBreathIntensity) {
        this.shortnessOfBreathIntensity = shortnessOfBreathIntensity;
    }

    /**
     *
     * @return
     * The nasalObstruction
     */
    public String getNasalObstruction() {
        return nasalObstruction;
    }

    /**
     *
     * @param nasalObstruction
     * The nasal_obstruction
     */
    public void setNasalObstruction(String nasalObstruction) {
        this.nasalObstruction = nasalObstruction;
    }

    /**
     *
     * @return
     * The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The FREQUENCY
     */
    public String getFREQUENCY() {
        return FREQUENCY;
    }

    /**
     *
     * @param FREQUENCY
     * The FREQUENCY
     */
    public void setFREQUENCY(String FREQUENCY) {
        this.FREQUENCY = FREQUENCY;
    }

}



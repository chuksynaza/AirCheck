package com.example.sammybobo.aircheck.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnedPushCrowdsource {

    @SerializedName("answer")
    @Expose
    private String answer;

    /**
     *
     * @return
     * The answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     *
     * @param answer
     * The answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
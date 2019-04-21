package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrandMotorList
{

    @SerializedName("data")
    @Expose
    private List<BrandMotor> data;

    /**
     * No args constructor for use in serialization
     *
     */
    public BrandMotorList() {
    }

    /**
     *
     * @param data
     */
    public BrandMotorList(List<BrandMotor> data) {
        super();
        this.data = data;
    }

    public List<BrandMotor> getData() {
        return data;
    }

    public void setData(List<BrandMotor> data) {
        this.data = data;
    }

}
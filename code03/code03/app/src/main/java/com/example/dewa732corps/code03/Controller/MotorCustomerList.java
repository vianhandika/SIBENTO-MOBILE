package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MotorCustomerList
{

    @SerializedName("data")
    @Expose
    private List<MotorCustomer> data;

    /**
     * No args constructor for use in serialization
     *
     */
    public MotorCustomerList() {
    }

    /**
     *
     * @param data
     */
    public MotorCustomerList(List<MotorCustomer> data) {
        super();
        this.data = data;
    }

    public List<MotorCustomer> getData() {
        return data;
    }

    public void setData(List<MotorCustomer> data) {
        this.data = data;
    }

}
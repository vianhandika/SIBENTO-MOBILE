package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.List;

public class TypeMotorList
{

    @SerializedName("data")
    @Expose
    private List<TypeMotor> data;

    /**
     * No args constructor for use in serialization
     *
     */
    public TypeMotorList() {
    }

    /**
     *
     * @param data
     */
    public TypeMotorList(List<TypeMotor> data) {
        super();
        this.data = data;
    }

    public List<TypeMotor> getData() {
        return data;
    }

    public void setData(List<TypeMotor> data) {
        this.data = data;
    }

}
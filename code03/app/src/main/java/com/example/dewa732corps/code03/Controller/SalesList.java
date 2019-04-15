package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalesList
{

    @SerializedName("data")
    @Expose
    private List<Sales> data;

    /**
     * No args constructor for use in serialization
     *
     */
    public SalesList() {
    }

    /**
     *
     * @param data
     */
    public SalesList(List<Sales> data) {
        super();
        this.data = data;
    }

    public List<Sales> getData() {
        return data;
    }

    public void setData(List<Sales> data) {
        this.data = data;
    }

}
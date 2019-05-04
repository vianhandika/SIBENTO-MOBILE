package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SparepartProcurementList
{

    @SerializedName("data")
    @Expose
    private List<SparepartProcurement> data;

    /**
     * No args constructor for use in serialization
     *
     */
    public SparepartProcurementList() {
    }

    /**
     *
     * @param data
     */
    public SparepartProcurementList(List<SparepartProcurement> data) {
        super();
        this.data = data;
    }

    public List<SparepartProcurement> getData() {
        return data;
    }

    public void setData(List<SparepartProcurement> data) {
        this.data = data;
    }

}
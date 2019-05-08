package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SparepartProcurementData {
    @SerializedName("data")
    @Expose
    private SparepartProcurement data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public SparepartProcurementData() {
    }

    /**
     *
     * @param data
     */
    public SparepartProcurementData(SparepartProcurement data) {
        super();
        this.data = data;
    }

    public SparepartProcurement getData() {
        return data;
    }

    public void setData(SparepartProcurement data) {
        this.data = data;
    }
}

package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SparepartProcurementDetailList {
    @SerializedName("data")
    @Expose
    private List<SparepartProcurementDetail> data;

    /**
     * No args constructor for use in serialization
     *
     */
    public SparepartProcurementDetailList() {
    }

    /**
     *
     * @param data
     */
    public SparepartProcurementDetailList(List<SparepartProcurementDetail> data) {
        super();
        this.data = data;
    }

    public List<SparepartProcurementDetail> getData() {
        return data;
    }

    public void setData(List<SparepartProcurementDetail> data) {
        this.data = data;
    }
}

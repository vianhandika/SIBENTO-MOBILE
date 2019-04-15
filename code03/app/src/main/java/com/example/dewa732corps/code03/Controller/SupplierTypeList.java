package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupplierTypeList {

    @SerializedName("data")
    @Expose
    private List<SupplierType> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public SupplierTypeList() {
    }

    /**
     *
     * @param data
     */
    public SupplierTypeList(List<SupplierType> data) {
        super();
        this.data = data;
    }

    public List<SupplierType> getData() {
        return data;
    }

    public void setData(List<SupplierType> data) {
        this.data = data;
    }

}
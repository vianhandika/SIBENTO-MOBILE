package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupplierList
{

    @SerializedName("data")
    @Expose
    private List<Supplier> data;

    /**
     * No args constructor for use in serialization
     *
     */
    public SupplierList() {
    }

    /**
     *
     * @param data
     */
    public SupplierList(List<Supplier> data) {
        super();
        this.data = data;
    }

    public List<Supplier> getData() {
        return data;
    }

    public void setData(List<Supplier> data) {
        this.data = data;
    }

}
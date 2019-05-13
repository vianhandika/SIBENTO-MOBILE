package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceList {
    @SerializedName("data")
    @Expose
    private List<Service> data = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     *
     */
    public TransactionServiceList() {
    }

    /**
     *
     * @param data
     */
    public TransactionServiceList(List<Service> data) {
        super();
        this.data = data;
    }

    public List<Service> getData() {
        return data;
    }

    public void setData(List<Service> data) {
        this.data = data;
    }
}

package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceList {
    @SerializedName("data")
    @Expose
    private List<TransactionService> data = new ArrayList<>();

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
    public TransactionServiceList(List<TransactionService> data) {
        super();
        this.data = data;
    }

    public List<TransactionService> getData() {
        return data;
    }

    public void setData(List<TransactionService> data) {
        this.data = data;
    }
}

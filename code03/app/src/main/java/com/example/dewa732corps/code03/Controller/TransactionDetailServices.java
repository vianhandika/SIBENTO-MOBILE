package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionDetailServices {
    @SerializedName("data")
    @Expose
    private List<TransactionService> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public TransactionDetailServices() {
    }

    /**
     *
     * @param data
     */
    public TransactionDetailServices(List<TransactionService> data) {
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


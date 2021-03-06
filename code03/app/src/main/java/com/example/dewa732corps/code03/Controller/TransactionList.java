package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TransactionList {
    @SerializedName("data")
    @Expose
    private List<Transaction> data = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     *
     */
    public TransactionList() {
    }

    /**
     *
     * @param data
     */
    public TransactionList(List<Transaction> data) {
        super();
        this.data = data;
    }

    public List<Transaction> getData() {
        return data;
    }

    public void setData(List<Transaction> data) {
        this.data = data;
    }
}

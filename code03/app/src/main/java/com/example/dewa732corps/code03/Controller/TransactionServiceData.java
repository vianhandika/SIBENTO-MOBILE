package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionServiceData {
    @SerializedName("data")
    @Expose
    private TransactionService data;

    /**
     * No args constructor for use in serialization
     *
     */
    public TransactionServiceData() {
    }

    /**
     *
     * @param data
     */
    public TransactionServiceData(TransactionService data) {
        super();
        this.data = data;
    }

    public TransactionService getData() {
        return data;
    }

    public void setData(TransactionService data) {
        this.data = data;
    }
}

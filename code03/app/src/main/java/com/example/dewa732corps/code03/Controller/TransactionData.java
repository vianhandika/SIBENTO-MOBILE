package com.example.dewa732corps.code03.Controller;

import android.widget.TableRow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionData {
    @SerializedName("data")
    @Expose
    private Transaction data;

    /**
     * No args constructor for use in serialization
     *
     */
    public TransactionData() {
    }

    /**
     *
     * @param data
     */
    public TransactionData(Transaction data) {
        super();
        this.data = data;
    }

    public Transaction getData() {
        return data;
    }

    public void setData(Transaction data) {
        this.data = data;
    }
}

package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionDetailSparepart {
    @SerializedName("data")
    @Expose
    private List<TransactionSparepart> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public TransactionDetailSparepart() {
    }

    /**
     *
     * @param data
     */
    public TransactionDetailSparepart(List<TransactionSparepart> data) {
        super();
        this.data = data;
    }

    public List<TransactionSparepart> getData() {
        return data;
    }

    public void setData(List<TransactionSparepart> data) {
        this.data = data;
    }
}

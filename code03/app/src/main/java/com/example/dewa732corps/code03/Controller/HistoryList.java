package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryList {
    @SerializedName("data")
    @Expose
    private List<History> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public HistoryList() {
    }

    /**
     *
     * @param data
     */
    public HistoryList(List<History> data) {
        super();
        this.data = data;
    }

    public List<History> getData() {
        return data;
    }

    public void setData(List<History> data) {
        this.data = data;
    }
}

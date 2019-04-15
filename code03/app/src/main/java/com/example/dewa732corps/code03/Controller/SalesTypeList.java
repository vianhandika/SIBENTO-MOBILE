package com.example.dewa732corps.code03.Controller;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesTypeList {

    @SerializedName("data")
    @Expose
    private List<SalesType> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public SalesTypeList() {
    }

    /**
     *
     * @param data
     */
    public SalesTypeList(List<SalesType> data) {
        super();
        this.data = data;
    }

    public List<SalesType> getData() {
        return data;
    }

    public void setData(List<SalesType> data) {
        this.data = data;
    }

}
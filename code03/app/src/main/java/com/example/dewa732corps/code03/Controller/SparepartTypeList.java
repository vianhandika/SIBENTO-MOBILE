package com.example.dewa732corps.code03.Controller;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SparepartTypeList {

    @SerializedName("data")
    @Expose
    private List<SparepartType> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public SparepartTypeList() {
    }

    /**
     *
     * @param data
     */
    public SparepartTypeList(List<SparepartType> data) {
        super();
        this.data = data;
    }

    public List<SparepartType> getData() {
        return data;
    }

    public void setData(List<SparepartType> data) {
        this.data = data;
    }

}
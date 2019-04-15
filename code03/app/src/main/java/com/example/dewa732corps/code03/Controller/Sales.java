package com.example.dewa732corps.code03.Controller;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sales {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("supplier")
    @Expose
    private List<Object> supplier = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Sales() {
    }

    /**
     *
     * @param id
     * @param phoneNumber
     * @param name
     * @param supplier
     */
    public Sales(Integer id, String name, String phoneNumber, List<Object> supplier) {
        super();
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.supplier = supplier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Object> getSupplier() {
        return supplier;
    }

    public void setSupplier(List<Object> supplier) {
        this.supplier = supplier;
    }
}

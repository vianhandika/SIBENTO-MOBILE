package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandMotor {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("brand")
    @Expose
    private BrandMotor brand;

    /**
     * No args constructor for use in serialization
     */
    public BrandMotor() {
    }

    /**
     * @param id
     * @param name
     * @param brand
     */
    public BrandMotor(Integer id, String name, BrandMotor brand) {
        super();
        this.id = id;
        this.name = name;
        this.brand = brand;
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

    public BrandMotor getBrand() {
        return brand;
    }

    public void setBrand(BrandMotor brand) {
        this.brand = brand;
    }
}
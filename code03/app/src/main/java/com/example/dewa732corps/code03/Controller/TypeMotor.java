package com.example.dewa732corps.code03.Controller;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypeMotor {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id_brand")
    @Expose
    private Integer idBrand;
    @SerializedName("brand")
    @Expose
    private Object brand;

    /**
     * No args constructor for use in serialization
     */
    public TypeMotor() {
    }

    /**
     * @param id
     * @param idBrand
     * @param name
     * @param brand
     */
    public TypeMotor(Integer id, String name, Integer idBrand, Object brand) {
        super();
        this.id = id;
        this.name = name;
        this.idBrand = idBrand;
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

    public Integer getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(Integer idBrand) {
        this.idBrand = idBrand;
    }

    public Object getBrand() {
        return brand;
    }

    public void setBrand(Object brand) {
        this.brand = brand;
    }
}
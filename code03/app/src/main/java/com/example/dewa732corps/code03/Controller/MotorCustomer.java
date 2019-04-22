package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MotorCustomer {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("plate")
    @Expose
    private String plate;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id_type")
    @Expose
    private Integer idType;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("id_brand")
    @Expose
    private Integer idBrand;
    @SerializedName("customer")
    @Expose
    private Customer customer;

    /**
     * No args constructor for use in serialization
     *
     */
    public MotorCustomer() {
    }

    /**
     *
     * @param id
     * @param idBrand
     * @param idType
     * @param plate
     * @param brand
     * @param customer
     * @param type
     */
    public MotorCustomer(Integer id, String plate, String type, Integer idType, String brand, Integer idBrand, Customer customer) {
        super();
        this.id = id;
        this.plate = plate;
        this.type = type;
        this.idType = idType;
        this.brand = brand;
        this.idBrand = idBrand;
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(Integer idBrand) {
        this.idBrand = idBrand;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

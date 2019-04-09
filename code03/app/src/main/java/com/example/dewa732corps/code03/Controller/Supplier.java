package com.example.dewa732corps.code03.Controller;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Supplier {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("sales")
    @Expose
    private List<Object> sales = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Supplier() {
    }

    /**
     *
     * @param id
     * @param phoneNumber
     * @param address
     * @param sales
     * @param name
     */
    public Supplier(Integer id, String name, String phoneNumber, String address, List<Object> sales) {
        super();
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.sales = sales;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Object> getSales() {
        return sales;
    }

    public void setSales(List<Object> sales) {
        this.sales = sales;
    }

}

package com.example.dewa732corps.code03.Controller;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {

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
    @SerializedName("motorcycle")
    @Expose
    private Object motorcycle = null;

//    @SerializedName("motorcustomer")
//    @Expose
//    private MotorCustomer motorcustomer;

    /**
     * No args constructor for use in serialization
     *
     */
    public Customer() {
    }

    /**
     *
     * @param id
     * @param phoneNumber
     * @param address
     * @param name
     * @param motorcycle
//     * @param motorcustomer
     */
    public Customer(Integer id, String name, String phoneNumber, String address, Object motorcycle) {
        super();
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.motorcycle = motorcycle;
//        this.motorcustomer = motorcustomer;
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

    public Object getMotorcycleCustomer() {
        return motorcycle;
    }

//    public MotorCustomer getMotorcustomer() {
//        return motorcustomer;
//    }

    public void setMotorcycle(Object motorcycle) {
        this.motorcycle = motorcycle;
    }

//    public void setMotorcycle(MotorCustomer motorcustomer) {
//        this.motorcustomer = motorcustomer;
//    }

}
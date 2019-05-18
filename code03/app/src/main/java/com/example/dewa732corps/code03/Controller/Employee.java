package com.example.dewa732corps.code03.Controller;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {

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
    @SerializedName("salary")
    @Expose
    private String salary;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("branch")
    @Expose
    private String branch;

    /**
     * No args constructor for use in serialization
     *
     */
    public Employee() {
    }

    /**
     *
     * @param id
     * @param phoneNumber
     * @param address
     * @param name
     * @param branch
     * @param role
     * @param salary
     */
    public Employee(Integer id, String name, String phoneNumber, String address, String salary, String role, String branch) {
        super();
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.salary = salary;
        this.role = role;
        this.branch = branch;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}
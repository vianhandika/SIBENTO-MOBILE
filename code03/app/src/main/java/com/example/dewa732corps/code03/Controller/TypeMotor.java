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
    @SerializedName("typelist")
    @Expose
    private TypeMotor type;

    /**
     * No args constructor for use in serialization
     *
     */
    public TypeMotor() {
    }

    /**
     *
     * @param id
     * @param name
     * @param type
     */
    public TypeMotor(Integer id, String name, TypeMotor type) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
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

    public TypeMotor getType() {
        return type;
    }

    public void setType(TypeMotor type) {
        this.type = type;
    }

}
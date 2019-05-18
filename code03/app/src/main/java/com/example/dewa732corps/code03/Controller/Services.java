package com.example.dewa732corps.code03.Controller;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Services {

    @SerializedName("id_service")
    @Expose
    private Integer idService;
    @SerializedName("name_service")
    @Expose
    private String nameService;
    @SerializedName("price_service")
    @Expose
    private String priceService;

    /**
     * No args constructor for use in serialization
     *
     */
    public Services() {
    }

    /**
     *
     * @param priceService
     * @param nameService
     * @param idService
     */
    public Services(Integer idService, String nameService, String priceService) {
        super();
        this.idService = idService;
        this.nameService = nameService;
        this.priceService = priceService;
    }

    public Integer getIdService() {
        return idService;
    }

    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public String getPriceService() {
        return priceService;
    }

    public void setPriceService(String priceService) {
        this.priceService = priceService;
    }

}
package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SparepartProcurementDetail {

    @SerializedName("id_procurement_detail")
    @Expose
    private Integer idProcurementDetail;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("sell_price")
    @Expose
    private String sellPrice;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("subtotal")
    @Expose
    private Integer subtotal;
    @SerializedName("id_procurement")
    @Expose
    private Integer idProcurement;
    @SerializedName("id_sparepart")
    @Expose
    private String idSparepart;
    @SerializedName("name_sparepart")
    @Expose
    private String nameSparepart;

    /**
     * No args constructor for use in serialization
     *
     */
    public SparepartProcurementDetail() {
    }

    /**
     *
     * @param amount
     * @param sellPrice
     * @param idSparepart
     * @param price
     * @param idProcurementDetail
     * @param idProcurement
     * @param subtotal
     * @param nameSparepart
     */
    public SparepartProcurementDetail(Integer idProcurementDetail, Integer price, String sellPrice, Integer amount, Integer subtotal, Integer idProcurement, String idSparepart, String nameSparepart) {
        super();
        this.idProcurementDetail = idProcurementDetail;
        this.price = price;
        this.sellPrice = sellPrice;
        this.amount = amount;
        this.subtotal = subtotal;
        this.idProcurement = idProcurement;
        this.idSparepart = idSparepart;
        this.nameSparepart = nameSparepart;
    }

    public Integer getIdProcurementDetail() {
        return idProcurementDetail;
    }

    public void setIdProcurementDetail(Integer idProcurementDetail) {
        this.idProcurementDetail = idProcurementDetail;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getIdProcurement() {
        return idProcurement;
    }

    public void setIdProcurement(Integer idProcurement) {
        this.idProcurement = idProcurement;
    }

    public String getIdSparepart() {
        return idSparepart;
    }

    public void setIdSparepart(String idSparepart) {
        this.idSparepart = idSparepart;
    }

    public String getNameSparepart() {
        return nameSparepart;
    }

    public void setNameSparepart(String nameSparepart) {
        this.nameSparepart = nameSparepart;
    }

}
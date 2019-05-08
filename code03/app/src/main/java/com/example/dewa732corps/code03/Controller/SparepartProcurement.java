package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SparepartProcurement {

    @SerializedName("id_procurement")
    @Expose
    private Integer idProcurement;
    @SerializedName("date_procurement")
    @Expose
    private String dateProcurement;
    @SerializedName("status_procurement")
    @Expose
    private String statusProcurement;
    @SerializedName("id_sales")
    @Expose
    private String idSales;
    @SerializedName("id_supplier")
    @Expose
    private Integer idSupplier;
    @SerializedName("name_sales")
    @Expose
    private String nameSales;
    @SerializedName("name_supplier")
    @Expose
    private String nameSupplier;
    @SerializedName("detail")
    @Expose
    private Object detail;

    /**
     * No args constructor for use in serialization
     *
     */
    public SparepartProcurement() {
    }

    /**
     *
     * @param detail
     * @param nameSupplier
     * @param idSupplier
     * @param statusProcurement
     * @param nameSales
     * @param dateProcurement
     * @param idProcurement
     * @param idSales
     */
    public SparepartProcurement(Integer idProcurement, String dateProcurement, String statusProcurement, String idSales, Integer idSupplier, String nameSales, String nameSupplier, Object detail) {
        super();
        this.idProcurement = idProcurement;
        this.dateProcurement = dateProcurement;
        this.statusProcurement = statusProcurement;
        this.idSales = idSales;
        this.idSupplier = idSupplier;
        this.nameSales = nameSales;
        this.nameSupplier = nameSupplier;
        this.detail = detail;
    }

    public Integer getIdProcurement() {
        return idProcurement;
    }

    public void setIdProcurement(Integer idProcurement) {
        this.idProcurement = idProcurement;
    }

    public String getDateProcurement() {
        return dateProcurement;
    }

    public void setDateProcurement(String dateProcurement) {
        this.dateProcurement = dateProcurement;
    }

    public String getStatusProcurement() {
        return statusProcurement;
    }

    public void setStatusProcurement(String statusProcurement) {
        this.statusProcurement = statusProcurement;
    }

    public String getIdSales() {
        return idSales;
    }

    public void setIdSales(String idSales) {
        this.idSales = idSales;
    }

    public Integer getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(Integer idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getNameSales() {
        return nameSales;
    }

    public void setNameSales(String nameSales) {
        this.nameSales = nameSales;
    }

    public String getNameSupplier() {
        return nameSupplier;
    }

    public void setNameSupplier(String nameSupplier) {
        this.nameSupplier = nameSupplier;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

}

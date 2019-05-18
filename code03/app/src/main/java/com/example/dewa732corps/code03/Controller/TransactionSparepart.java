package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;


public class TransactionSparepart {

    @SerializedName("id_detail_sparepart")
    @Expose
    private Integer idDetailSparepart;
    @SerializedName("amount_transaction_sparepart")
    @Expose
    private Integer amountTransactionSparepart;
    @SerializedName("price_transaction_sparepart")
    @Expose
    private Integer priceTransactionSparepart;
    @SerializedName("subtotal_transaction_sparepart")
    @Expose
    private Integer subtotalTransactionSparepart;
    @SerializedName("id_transaction")
    @Expose
    private String idTransaction;
    @SerializedName("id_sparepart")
    @Expose
    private String idSparepart;
    @SerializedName("type_sparepart")
    @Expose
    private String typeSparepart;
    @SerializedName("name_sparepart")
    @Expose
    private String nameSparepart;
    @SerializedName("brand_sparepart")
    @Expose
    private String brandSparepart;
    @SerializedName("id_employee")
    @Expose
    private Integer idEmployee;
    @SerializedName("mechanic_name")
    @Expose
    private String mechanicName;
    @SerializedName("id_motorcycle")
    @Expose
    private Integer idMotorcycle;
    @SerializedName("plate_number")
    @Expose
    private String plateNumber;

    /**
     * No args constructor for use in serialization
     *
     */
    public TransactionSparepart() {
    }

    /**
     *
     * @param brandSparepart
     * @param idDetailSparepart
     * @param mechanicName
     * @param idEmployee
     * @param nameSparepart
     * @param subtotalTransactionSparepart
     * @param idSparepart
     * @param idMotorcycle
     * @param plateNumber
     * @param typeSparepart
     * @param priceTransactionSparepart
     * @param idTransaction
     * @param amountTransactionSparepart
     */
    public TransactionSparepart(Integer idDetailSparepart, Integer amountTransactionSparepart, Integer priceTransactionSparepart, Integer subtotalTransactionSparepart, String idTransaction, String idSparepart, String typeSparepart, String nameSparepart, String brandSparepart, Integer idEmployee, String mechanicName, Integer idMotorcycle, String plateNumber) {
        super();
        this.idDetailSparepart = idDetailSparepart;
        this.amountTransactionSparepart = amountTransactionSparepart;
        this.priceTransactionSparepart = priceTransactionSparepart;
        this.subtotalTransactionSparepart = subtotalTransactionSparepart;
        this.idTransaction = idTransaction;
        this.idSparepart = idSparepart;
        this.typeSparepart = typeSparepart;
        this.nameSparepart = nameSparepart;
        this.brandSparepart = brandSparepart;
        this.idEmployee = idEmployee;
        this.mechanicName = mechanicName;
        this.idMotorcycle = idMotorcycle;
        this.plateNumber = plateNumber;
    }

    public Integer getIdDetailSparepart() {
        return idDetailSparepart;
    }

    public void setIdDetailSparepart(Integer idDetailSparepart) {
        this.idDetailSparepart = idDetailSparepart;
    }

    public Integer getAmountTransactionSparepart() {
        return amountTransactionSparepart;
    }

    public void setAmountTransactionSparepart(Integer amountTransactionSparepart) {
        this.amountTransactionSparepart = amountTransactionSparepart;
    }

    public Integer getPriceTransactionSparepart() {
        return priceTransactionSparepart;
    }

    public void setPriceTransactionSparepart(Integer priceTransactionSparepart) {
        this.priceTransactionSparepart = priceTransactionSparepart;
    }

    public Integer getSubtotalTransactionSparepart() {
        return subtotalTransactionSparepart;
    }

    public void setSubtotalTransactionSparepart(Integer subtotalTransactionSparepart) {
        this.subtotalTransactionSparepart = subtotalTransactionSparepart;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdSparepart() {
        return idSparepart;
    }

    public void setIdSparepart(String idSparepart) {
        this.idSparepart = idSparepart;
    }

    public String getTypeSparepart() {
        return typeSparepart;
    }

    public void setTypeSparepart(String typeSparepart) {
        this.typeSparepart = typeSparepart;
    }

    public String getNameSparepart() {
        return nameSparepart;
    }

    public void setNameSparepart(String nameSparepart) {
        this.nameSparepart = nameSparepart;
    }

    public String getBrandSparepart() {
        return brandSparepart;
    }

    public void setBrandSparepart(String brandSparepart) {
        this.brandSparepart = brandSparepart;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }

    public Integer getIdMotorcycle() {
        return idMotorcycle;
    }

    public void setIdMotorcycle(Integer idMotorcycle) {
        this.idMotorcycle = idMotorcycle;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

}
package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {

    @SerializedName("id_transaction")
    @Expose
    private String idTransaction;
    @SerializedName("status_process")
    @Expose
    private String statusProcess;
    @SerializedName("date_transaction")
    @Expose
    private String dateTransaction;
    @SerializedName("status_paid")
    @Expose
    private String statusPaid;
    @SerializedName("type_transaction")
    @Expose
    private String typeTransaction;
    @SerializedName("discount_transaction")
    @Expose
    private Integer discountTransaction;
    @SerializedName("total_transaction")
    @Expose
    private Integer totalTransaction;
    @SerializedName("id_customer")
    @Expose
    private Integer idCustomer;
    @SerializedName("name_customer")
    @Expose
    private String nameCustomer;
    @SerializedName("service")
    @Expose
    private Object service;
    @SerializedName("sparepart")
    @Expose
    private Sparepart sparepart;
    @SerializedName("employee")
    @Expose
    private Object employee;

    /**
     * No args constructor for use in serialization
     *
     */
    public History() {
    }

    /**
     *
     * @param statusPaid
     * @param totalTransaction
     * @param idCustomer
     * @param discountTransaction
     * @param typeTransaction
     * @param service
     * @param dateTransaction
     * @param sparepart
     * @param nameCustomer
     * @param employee
     * @param idTransaction
     * @param statusProcess
     */
    public History(String idTransaction, String statusProcess, String dateTransaction, String statusPaid, String typeTransaction, Integer discountTransaction, Integer totalTransaction, Integer idCustomer, String nameCustomer, Object service, Sparepart sparepart, Object employee) {
        super();
        this.idTransaction = idTransaction;
        this.statusProcess = statusProcess;
        this.dateTransaction = dateTransaction;
        this.statusPaid = statusPaid;
        this.typeTransaction = typeTransaction;
        this.discountTransaction = discountTransaction;
        this.totalTransaction = totalTransaction;
        this.idCustomer = idCustomer;
        this.nameCustomer = nameCustomer;
        this.service = service;
        this.sparepart = sparepart;
        this.employee = employee;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getStatusProcess() {
        return statusProcess;
    }

    public void setStatusProcess(String statusProcess) {
        this.statusProcess = statusProcess;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getStatusPaid() {
        return statusPaid;
    }

    public void setStatusPaid(String statusPaid) {
        this.statusPaid = statusPaid;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public Integer getDiscountTransaction() {
        return discountTransaction;
    }

    public void setDiscountTransaction(Integer discountTransaction) {
        this.discountTransaction = discountTransaction;
    }

    public Integer getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(Integer totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }

    public Sparepart getSparepart() {
        return sparepart;
    }

    public void setSparepart(Sparepart sparepart) {
        this.sparepart = sparepart;
    }

    public Object getEmployee() {
        return employee;
    }

    public void setEmployee(Object employee) {
        this.employee = employee;
    }

}
package com.example.dewa732corps.code03.Controller;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionService {

    @SerializedName("id_detail_service")
    @Expose
    private Integer idDetailService;
    @SerializedName("amount_transaction_service")
    @Expose
    private Integer amountTransactionService;
    @SerializedName("price_transaction_service")
    @Expose
    private Integer priceTransactionService;
    @SerializedName("subtotal_transaction_service")
    @Expose
    private Integer subtotalTransactionService;
    @SerializedName("id_transaction")
    @Expose
    private String idTransaction;
    @SerializedName("id_service")
    @Expose
    private Integer idService;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("id_mechanic")
    @Expose
    private Integer idMechanic;
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
    public TransactionService() {
    }

    /**
     *
     * @param amountTransactionService
     * @param idService
     * @param priceTransactionService
     * @param idMotorcycle
     * @param mechanicName
     * @param idDetailService
     * @param plateNumber
     * @param idMechanic
     * @param idTransaction
     * @param serviceName
     * @param subtotalTransactionService
     */
    public TransactionService(Integer idDetailService, Integer amountTransactionService, Integer priceTransactionService, Integer subtotalTransactionService, String idTransaction, Integer idService, String serviceName, Integer idMechanic, String mechanicName, Integer idMotorcycle, String plateNumber) {
        super();
        this.idDetailService = idDetailService;
        this.amountTransactionService = amountTransactionService;
        this.priceTransactionService = priceTransactionService;
        this.subtotalTransactionService = subtotalTransactionService;
        this.idTransaction = idTransaction;
        this.idService = idService;
        this.serviceName = serviceName;
        this.idMechanic = idMechanic;
        this.mechanicName = mechanicName;
        this.idMotorcycle = idMotorcycle;
        this.plateNumber = plateNumber;
    }

    public Integer getIdDetailService() {
        return idDetailService;
    }

    public void setIdDetailService(Integer idDetailService) {
        this.idDetailService = idDetailService;
    }

    public Integer getAmountTransactionService() {
        return amountTransactionService;
    }

    public void setAmountTransactionService(Integer amountTransactionService) {
        this.amountTransactionService = amountTransactionService;
    }

    public Integer getPriceTransactionService() {
        return priceTransactionService;
    }

    public void setPriceTransactionService(Integer priceTransactionService) {
        this.priceTransactionService = priceTransactionService;
    }

    public Integer getSubtotalTransactionService() {
        return subtotalTransactionService;
    }

    public void setSubtotalTransactionService(Integer subtotalTransactionService) {
        this.subtotalTransactionService = subtotalTransactionService;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Integer getIdService() {
        return idService;
    }

    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getIdMechanic() {
        return idMechanic;
    }

    public void setIdMechanic(Integer idMechanic) {
        this.idMechanic = idMechanic;
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
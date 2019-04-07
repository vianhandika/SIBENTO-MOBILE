package com.example.dewa732corps.code03.Controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sparepart {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("min_stock")
    @Expose
    private String minStock;
    @SerializedName("buy_price")
    @Expose
    private String buyPrice;
    @SerializedName("sell_price")
    @Expose
    private String sellPrice;
    @SerializedName("placement")
    @Expose
    private String placement;
    @SerializedName("placement_position")
    @Expose
    private String placementPosition;
    @SerializedName("placement_place")
    @Expose
    private String placementPlace;
    @SerializedName("placement_number")
    @Expose
    private String placementNumber;
    @SerializedName("image")
    @Expose
    private String image;

    /**
     * No args constructor for use in serialization
     *
     */
    public Sparepart() {
    }

    /**
     *
     * @param sellPrice
     * @param placement
     * @param image
     * @param placementNumber
     * @param type
     * @param id
     * @param stock
     * @param minStock
     * @param name
     * @param placementPosition
     * @param buyPrice
     * @param brand
     * @param placementPlace
     */
    public Sparepart(String id, String name, String brand, String type, String stock, String minStock, String buyPrice, String sellPrice, String placement, String placementPosition, String placementPlace, String placementNumber, String image) {
        super();
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.stock = stock;
        this.minStock = minStock;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.placement = placement;
        this.placementPosition = placementPosition;
        this.placementPlace = placementPlace;
        this.placementNumber = placementNumber;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getMinStock() {
        return minStock;
    }

    public void setMinStock(String minStock) {
        this.minStock = minStock;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public String getPlacementPosition() {
        return placementPosition;
    }

    public void setPlacementPosition(String placementPosition) {
        this.placementPosition = placementPosition;
    }

    public String getPlacementPlace() {
        return placementPlace;
    }

    public void setPlacementPlace(String placementPlace) {
        this.placementPlace = placementPlace;
    }

    public String getPlacementNumber() {
        return placementNumber;
    }

    public void setPlacementNumber(String placementNumber) {
        this.placementNumber = placementNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

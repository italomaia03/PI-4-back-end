package br.edu.projeto_integrado.somar.dtos.inventory;

import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class GetInventoryShowcaseParams {

    private String name;
    private String barcode;
    @PositiveOrZero
    private BigDecimal minSellingPrice;
    @PositiveOrZero
    private BigDecimal maxSellingPrice;
    @PositiveOrZero
    private BigDecimal minBuyingPrice;
    @PositiveOrZero
    private BigDecimal maxBuyingPrice;
    private Boolean isExpired;
    private Boolean isDamaged;
    @PositiveOrZero
    private Integer minAmount;
    @PositiveOrZero
    private Integer maxAmount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getMinSellingPrice() {
        return minSellingPrice;
    }

    public void setMinSellingPrice(BigDecimal minSellingPrice) {
        this.minSellingPrice = minSellingPrice;
    }

    public BigDecimal getMaxSellingPrice() {
        return maxSellingPrice;
    }

    public void setMaxSellingPrice(BigDecimal maxSellingPrice) {
        this.maxSellingPrice = maxSellingPrice;
    }

    public BigDecimal getMinBuyingPrice() {
        return minBuyingPrice;
    }

    public void setMinBuyingPrice(BigDecimal minBuyingPrice) {
        this.minBuyingPrice = minBuyingPrice;
    }

    public BigDecimal getMaxBuyingPrice() {
        return maxBuyingPrice;
    }

    public void setMaxBuyingPrice(BigDecimal maxBuyingPrice) {
        this.maxBuyingPrice = maxBuyingPrice;
    }

    public Boolean getIsExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public Integer getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Boolean getIsDamaged() {
        return isDamaged;
    }

    public void setDamaged(Boolean damaged) {
        isDamaged = damaged;
    }
}

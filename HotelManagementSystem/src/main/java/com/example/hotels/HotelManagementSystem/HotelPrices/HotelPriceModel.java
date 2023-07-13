package com.example.hotels.HotelManagementSystem.HotelPrices;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class HotelPriceModel {

    @Id
    private LocalDate priceDate;
    private BigDecimal price;


    public HotelPriceModel(LocalDate priceDate, BigDecimal price) {
        this.priceDate = priceDate;
        this.price=price;
    }

    public HotelPriceModel() {

    }


    public LocalDate getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(LocalDate priceDate) {
        this.priceDate = priceDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

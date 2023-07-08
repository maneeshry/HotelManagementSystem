package com.example.hotels.HotelManagementSystem.HotelPrices;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class HotelPriceModel {

    @Id
    private int id;
    private LocalDate date;
    private BigDecimal price;

    public HotelPriceModel()
    {

    }



    public HotelPriceModel(LocalDate currentDate, BigDecimal price) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

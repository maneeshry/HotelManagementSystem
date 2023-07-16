package com.example.hotels.HotelManagementSystem.HotelPrices;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class HotelPriceModel {

    @Id
    private LocalDate priceDate;
    private BigDecimal price;

    private int kingsmoking;
    private int king_non_smoking;
    private int queen_smoking;
    private int queen_non_smoking;


    public HotelPriceModel() {

    }

    public HotelPriceModel(LocalDate priceDate, BigDecimal price, int kingsmoking, int king_non_smoking, int queen_smoking, int queen_non_smoking) {
        this.priceDate = priceDate;
        this.price = price;
        this.kingsmoking = kingsmoking;
        this.king_non_smoking = king_non_smoking;
        this.queen_smoking = queen_smoking;
        this.queen_non_smoking = queen_non_smoking;
    }

    public int getKingsmoking() {
        return kingsmoking;
    }

    public void setKingsmoking(int kingsmoking) {
        this.kingsmoking = kingsmoking;
    }

    public int getKing_non_smoking() {
        return king_non_smoking;
    }

    public void setKing_non_smoking(int king_non_smoking) {
        this.king_non_smoking = king_non_smoking;
    }

    public int getQueen_smoking() {
        return queen_smoking;
    }

    public void setQueen_smoking(int queen_smoking) {
        this.queen_smoking = queen_smoking;
    }

    public int getQueen_non_smoking() {
        return queen_non_smoking;
    }

    public void setQueen_non_smoking(int queen_non_smoking) {
        this.queen_non_smoking = queen_non_smoking;
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

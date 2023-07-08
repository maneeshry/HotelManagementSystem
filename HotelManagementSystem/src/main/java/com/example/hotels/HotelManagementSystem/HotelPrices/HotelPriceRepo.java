package com.example.hotels.HotelManagementSystem.HotelPrices;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HotelPriceRepo extends JpaRepository<HotelPriceModel, Integer> {

    List<HotelPriceModel> findByDateBetween(LocalDate check_in,LocalDate check_out);
}

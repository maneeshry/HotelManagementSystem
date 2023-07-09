package com.example.hotels.HotelManagementSystem.HotelPrices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelPriceRepo extends JpaRepository<HotelPriceModel, LocalDate> {

    List<HotelPriceModel> findByPriceDateBetween(LocalDate check_in,LocalDate check_out);
}

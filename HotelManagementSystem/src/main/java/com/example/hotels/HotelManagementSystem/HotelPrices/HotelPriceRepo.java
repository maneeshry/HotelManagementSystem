package com.example.hotels.HotelManagementSystem.HotelPrices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelPriceRepo extends JpaRepository<HotelPriceModel, LocalDate> {

    List<HotelPriceModel> findByPriceDateBetween(LocalDate check_in,LocalDate check_out);

    List<HotelPriceModel> findAll();

    HotelPriceModel findByPriceDate(LocalDate priceDate);

//    @Query("SELECT h.kingsmoking FROM HotelPriceModel h WHERE h.priceDate = :date")
//    Integer getAvailabilityByDate(@Param("date") LocalDate date);

    @Query("SELECT CASE " +
            "    WHEN :room = 'King Smoking' THEN h.kingsmoking " +
            "    WHEN :room = 'King Non-Smoking' THEN h.king_non_smoking " +
            "    WHEN :room = 'Queen Smoking' THEN h.queen_smoking " +
            "    WHEN :room = 'Queen Non-Smoking' THEN h.queen_non_smoking " +
            "    ELSE -1 " +
            "END " +
            "FROM HotelPriceModel h WHERE h.priceDate = :date")
    Integer getAvailabilityByDate(@Param("date") LocalDate date, @Param("room") String roomType);

}

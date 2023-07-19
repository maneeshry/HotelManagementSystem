package com.example.hotels.HotelManagementSystem.HotelPrices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class HotelPriceService {
    //private Map<LocalDate,BigDecimal> map;

    @Autowired
    private HotelPriceRepo hotelPriceRepo;



    @Transactional
    public void generateHotelPricesForThreeMonths() {
        HotelPriceModel hotelPriceModel;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(3);
        LocalDate currentDate = startDate;
        //map = new HashMap<>();

        while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
            BigDecimal price = generatePriceBasedOnDate(currentDate);
            hotelPriceModel = new HotelPriceModel();
            hotelPriceModel.setPriceDate(currentDate);
            hotelPriceModel.setPrice(price);
            hotelPriceModel.setKingsmoking(4);
            hotelPriceModel.setKing_non_smoking(4);
            hotelPriceModel.setQueen_smoking(4);
            hotelPriceModel.setQueen_non_smoking(4);
            hotelPriceRepo.save(hotelPriceModel);
            currentDate = currentDate.plusDays(1);
        }

    }

    private BigDecimal generatePriceBasedOnDate(LocalDate date) {

        if (date.getDayOfWeek().getValue() <= 4) { // Monday to Thursday
            return new BigDecimal("75");
        }
        else if (date.getDayOfWeek().getValue() == 5) //Friday
        {
            return new BigDecimal("110");
        }
        else if (date.getDayOfWeek().getValue() ==6) //Saturday
        {
            return new BigDecimal("120");
        }else { // Sunday
            return new BigDecimal("100");
        }
    }



    public BigDecimal calculateTotalPrice(LocalDate checkInDate, LocalDate checkOutDate) {
        BigDecimal tax = BigDecimal.valueOf(12);

        List<HotelPriceModel> hotelPrices = hotelPriceRepo.findByPriceDateBetween(checkInDate, checkOutDate);
        LocalDate currentDate = checkInDate;
        BigDecimal totalPrice = BigDecimal.ZERO;

        while (currentDate.isBefore(checkOutDate)) {
            HotelPriceModel hotelPrice = getHotelPriceForDate(hotelPrices, currentDate);
            if (hotelPrice != null) {
                totalPrice = totalPrice.add(hotelPrice.getPrice());
            }
            currentDate = currentDate.plusDays(1);
        }

        BigDecimal taxAmount = totalPrice.multiply(tax).divide(BigDecimal.valueOf(100));
        BigDecimal totalPriceWithTax = totalPrice.add(taxAmount);

        return totalPriceWithTax;
    }


    private HotelPriceModel getHotelPriceForDate(List<HotelPriceModel> hotelPrices, LocalDate date) {
        for (HotelPriceModel hotelPrice : hotelPrices) {
            if (hotelPrice.getPriceDate().isEqual(date)) {
                return hotelPrice;
            }
        }
        return null;
    }

    public void updateHotelPrice(LocalDate priceDate, BigDecimal price) {

        HotelPriceModel hotelPriceModel = hotelPriceRepo.findByPriceDate(priceDate);
        hotelPriceModel.setPrice(price);
        hotelPriceRepo.save(hotelPriceModel);

    }

    public int checkAvailability()
    {
        return 0;
    }
}

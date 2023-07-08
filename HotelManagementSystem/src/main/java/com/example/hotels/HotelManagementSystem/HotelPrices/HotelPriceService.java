package com.example.hotels.HotelManagementSystem.HotelPrices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelPriceService {

//    public HotelPriceService()
//    {
//        generateHotelPricesForThreeMonths();
//    }

    @Autowired
    private HotelPriceRepo hotelPriceRepo;

    public void generateHotelPricesForThreeMonths() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(3);

        List<HotelPriceModel> hotelPrices = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
            BigDecimal price = generatePriceBasedOnDate(currentDate);
            hotelPrices.add(new HotelPriceModel(currentDate, price));
            currentDate = currentDate.plusDays(1);
        }

        hotelPriceRepo.saveAll(hotelPrices);
    }

    private BigDecimal generatePriceBasedOnDate(LocalDate date) {
        // Implement your logic to generate price based on the date
        // This is just an example
        if (date.getDayOfWeek().getValue() <= 5) { // Monday to Friday
            return new BigDecimal("100");
        } else { // Saturday and Sunday
            return new BigDecimal("150");
        }
    }

    public BigDecimal calculateTotalPrice(LocalDate checkInDate, LocalDate checkOutDate) {
        List<HotelPriceModel> hotelPrices = hotelPriceRepo.findByDateBetween(checkInDate, checkOutDate);

        BigDecimal totalPrice = BigDecimal.ZERO;
        LocalDate currentDate = checkInDate;
        while (currentDate.isBefore(checkOutDate) || currentDate.isEqual(checkOutDate)) {
            HotelPriceModel hotelPrice = getHotelPriceForDate(hotelPrices, currentDate);
            if (hotelPrice != null) {
                totalPrice = totalPrice.add(hotelPrice.getPrice());
            }
            currentDate = currentDate.plusDays(1);
        }

        return totalPrice;
    }

    private HotelPriceModel getHotelPriceForDate(List<HotelPriceModel> hotelPrices, LocalDate date) {
        for (HotelPriceModel hotelPrice : hotelPrices) {
            if (hotelPrice.getDate().isEqual(date)) {
                return hotelPrice;
            }
        }
        return null;
    }
}

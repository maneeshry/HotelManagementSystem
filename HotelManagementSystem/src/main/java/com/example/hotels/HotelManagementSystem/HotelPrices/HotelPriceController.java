package com.example.hotels.HotelManagementSystem.HotelPrices;

import com.example.hotels.HotelManagementSystem.HotelModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class HotelPriceController {

    @Autowired
    private HotelPriceService service;

    @GetMapping("/checkPrice")
    public String calculatePrice(Model model) {

        model.addAttribute("priceData", new HotelModel());
        return "checkPrice";
    }

    @PostMapping("getPrice")
    public String getCalculatedPrice(@ModelAttribute("priceData") HotelModel priceData,  Model model)
    {
        //model.addAttribute("check_in",priceData.getCheck_in());
        //model.addAttribute("check_out",priceData.getCheck_out());
        LocalDate checkInDate = priceData.getCheck_in();
        LocalDate checkOutDate = priceData.getCheck_out();
        BigDecimal totalPrice = service.calculateTotalPrice(checkInDate, checkOutDate);
        model.addAttribute("totalPrice", totalPrice);

        return "getPrice";
    }
}

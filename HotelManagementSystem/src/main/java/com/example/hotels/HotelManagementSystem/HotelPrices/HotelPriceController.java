package com.example.hotels.HotelManagementSystem.HotelPrices;

import com.example.hotels.HotelManagementSystem.HotelModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class HotelPriceController {

    @Autowired
    private HotelPriceService service;

    @Autowired
    private HotelPriceRepo hotelPriceRepo;


    @GetMapping("/checkPrice")
    public String calculatePrice(Model model) {
        model.addAttribute("formData", new HotelModel());
        boolean roomAvailable=true;
        model.addAttribute("roomAvailable",roomAvailable);
        return "checkPrice";
    }

    @GetMapping("/adminRepo")
    public String adminRepo(Model model) {
        service.generateHotelPricesForThreeMonths();
        return "admin";
    }

    @GetMapping("/admin")
    public String adminControlForPrice(Model model) {
        //service.generateHotelPricesForThreeMonths();
        model.addAttribute("admin",hotelPriceRepo.findAll());
        return "admin";
    }
    @PostMapping("/updatePrice")
    public String updatePrice(@RequestParam("priceDate") LocalDate priceDate, @RequestParam("price") BigDecimal price) {
        service.updateHotelPrice(priceDate, price);
        return "redirect:/admin";
    }
    @GetMapping("/getPrice")
    public String showPrice(@ModelAttribute("formData") HotelModel formData,@RequestParam("check_in") LocalDate checkInDate,
                               @RequestParam("check_out") LocalDate checkOutDate,@RequestParam("room_type") String room_type,
                               Model model) {

        BigDecimal totalPrice = service.calculateTotalPrice(checkInDate, checkOutDate);
        model.addAttribute("check_in", checkInDate);
        model.addAttribute("check_out", checkOutDate);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("room_type", room_type);
        int available = hotelPriceRepo.getAvailabilityByDate(formData.getCheck_in(),room_type);
        System.out.println(available);
        boolean roomAvailable=true;
        if(available < 4)
        {
            roomAvailable= false;
            model.addAttribute("roomAvailable",roomAvailable);
            return "checkPrice";
        }
        model.addAttribute("roomAvailable",roomAvailable);
        return "redirect:/form?check_in=" + checkInDate + "&check_out=" + checkOutDate + "&room_type=" + room_type;

    }

    @PostMapping("/getPrice")
    public String getCalculatedPrice(@ModelAttribute("formData") HotelModel formData, Model model) {
        LocalDate checkInDate = formData.getCheck_in();
        LocalDate checkOutDate = formData.getCheck_out();
        String room_type = formData.getRoom_type();

        return "redirect:/getPrice?check_in=" + checkInDate + "&check_out=" + checkOutDate +"&room_type=" + room_type;
    }

}

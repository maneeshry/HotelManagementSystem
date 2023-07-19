package com.example.hotels.HotelManagementSystem;




import com.example.hotels.HotelManagementSystem.HotelPrices.HotelPriceModel;
import com.example.hotels.HotelManagementSystem.HotelPrices.HotelPriceRepo;
import com.example.hotels.HotelManagementSystem.HotelPrices.HotelPriceService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;



@Controller
public class HotelController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private HotelPriceRepo hotelPriceRepo;

    @Autowired
    private HotelPriceService service;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username")
    private String username;


    //HotelModel formData;



    @GetMapping("/form")
    public String showForm(@ModelAttribute("formData") HotelModel formData,Model model,@RequestParam("check_in") LocalDate checkInDate,
                           @RequestParam("check_out") LocalDate checkOutDate,@RequestParam("room_type") String room_type) {
        BigDecimal totalPrice = service.calculateTotalPrice(checkInDate, checkOutDate);
        long numberOfNights = ChronoUnit.DAYS.between(checkInDate,checkOutDate);
        // Add attributes to the model
        model.addAttribute("check_in", checkInDate);
        model.addAttribute("check_out", checkOutDate);
        model.addAttribute("total_price", totalPrice);
        model.addAttribute("room_type",room_type);
        model.addAttribute("nights",numberOfNights);
        model.addAttribute("formData", formData);
        return "booking";
    }

//get check_in and check_out dates from the form

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute("formData") HotelModel formData, Model model) throws MessagingException {
        long numberOfNights = ChronoUnit.DAYS.between(formData.check_in,formData.check_out);
        BigDecimal totalPrice = service.calculateTotalPrice(formData.check_in,formData.check_out);
        // Add attributes to the model
        model.addAttribute("first_name",formData.getFirst_name());
        model.addAttribute("last_name",formData.getLast_name());
        model.addAttribute("email",formData.getEmail());
        model.addAttribute("phone_number",formData.getPhone_number());
        model.addAttribute("check_in",formData.getCheck_in());
        model.addAttribute("check_out",formData.getCheck_out());
        model.addAttribute("nights",numberOfNights);
        model.addAttribute("total_price",totalPrice);
        model.addAttribute("room_type",formData.getRoom_type());
        model.addAttribute("adults",formData.getAdults());
        model.addAttribute("children",formData.getChildren());

        model.addAttribute("formData",formData);

        //send email
        sendEmail(formData,numberOfNights,totalPrice);

        String room = formData.getRoom_type();



        int available = hotelPriceRepo.getAvailabilityByDate(formData.getCheck_in(),room);
        HotelPriceModel hotelPriceModel = hotelPriceRepo.findByPriceDate(formData.getCheck_in());


        switch (room)
        {
            case "King Smoking":
                available--;
                hotelPriceModel.setKingsmoking(available);
                break;

            case "King Non-Smoking":
                available--;
                hotelPriceModel.setKing_non_smoking(available);
                break;

            case "Queen Smoking":
                available--;
                hotelPriceModel.setQueen_smoking(available);
                break;

            case "Queen Non-Smoking":
                available--;
                hotelPriceModel.setQueen_non_smoking(available);
                break;
        }
        hotelPriceRepo.save(hotelPriceModel);

        return "email";
    }

    public void sendEmail(HotelModel formData,long numberOfNights,BigDecimal total_price) throws MessagingException {
        // Prepare the evaluation context
        final Context context = new Context();
        context.setVariable("first_name", formData.getFirst_name());
        context.setVariable("last_name",formData.getLast_name());
        context.setVariable("email", formData.getEmail());
        context.setVariable("phone_number", formData.getPhone_number());
        context.setVariable("check_in",formData.getCheck_in());
        context.setVariable("check_out",formData.getCheck_out());
        context.setVariable("nights",numberOfNights);
        context.setVariable("total_price",total_price);
        context.setVariable("room_type",formData.getRoom_type());
        context.setVariable("adults",formData.getAdults());
        context.setVariable("children",formData.getChildren());


        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("New Reservation Arriving on: "+formData.getCheck_in());
        message.setFrom(username);
        message.setTo(formData.getEmail());
        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process("email", context);
        message.setText(htmlContent, true );
        this.javaMailSender.send(mimeMessage);}


}





package com.example.hotels.HotelManagementSystem.HotelPrices;

import com.example.hotels.HotelManagementSystem.HotelModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class HotelPriceController {

    @Autowired
    private HotelPriceService service;


    @GetMapping("/checkPrice")
    public String calculatePrice(Model model) {
        model.addAttribute("formData", new HotelModel());
        return "checkPrice";
    }

    @GetMapping("/admin")
    public String adminControlForPrice() {
        service.generateHotelPricesForThreeMonths();
        return "admin";
    }



    @GetMapping("/getPrice")
    public String showPrice(@ModelAttribute("formData") HotelModel formData,@RequestParam("check_in") LocalDate checkInDate,
                               @RequestParam("check_out") LocalDate checkOutDate,@RequestParam("room_type") String room_type,
                               Model model) {

        BigDecimal totalPrice = service.calculateTotalPrice(checkInDate, checkOutDate);
        model.addAttribute("check_in",checkInDate);
        model.addAttribute("check_out",checkOutDate);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("room_type",room_type);
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
/*
 @PostMapping("/submit")
    public String submitForm(@ModelAttribute("formData") HotelModel formData, Model model) throws MessagingException {
        // Add attributes to the model
        //model.addAttribute("formData", formData);
        //long numberOfNights = ChronoUnit.DAYS.between(formData.getCheck_in(), formData.getCheck_out());

        model.addAttribute("first_name",formData.getFirst_name());
        model.addAttribute("last_name",formData.getLast_name());
        model.addAttribute("email",formData.getEmail());
        model.addAttribute("phone_number",formData.getPhone_number());
        model.addAttribute("check_in",formData.getCheck_in());
        model.addAttribute("check_out",formData.getCheck_out());
        //model.addAttribute("nights",numberOfNights);
        model.addAttribute("room_type",formData.getRoom_type());
        model.addAttribute("adults",formData.getAdults());
        model.addAttribute("children",formData.getChildren());
        model.addAttribute("pets",formData.getPets());
        model.addAttribute("formData",formData);

        //send email
        sendEmail(formData);
        return "email";
    }

    public void sendEmail(HotelModel formData) throws MessagingException {
        // Prepare the evaluation context
        final Context context = new Context();
        context.setVariable("first_name", formData.getFirst_name());
        context.setVariable("last_name",formData.getLast_name());
        context.setVariable("email", formData.getEmail());
        context.setVariable("phone_number", formData.getPhone_number());
        context.setVariable("check_in",formData.getCheck_in());
        context.setVariable("check_out",formData.getCheck_out());
        //context.setVariable("nights",numberOfNights);
        context.setVariable("room_type",formData.getRoom_type());
        context.setVariable("adults",formData.getAdults());
        context.setVariable("children",formData.getChildren());
        context.setVariable("pets",formData.getPets());

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Hotel Booking Confirmation");
        message.setFrom(username);
        message.setTo(formData.getEmail());
        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process("email", context);
        message.setText(htmlContent, true /* isHtml ); */
        // Send mailthis.javaMailSender.send(mimeMessage);}*/
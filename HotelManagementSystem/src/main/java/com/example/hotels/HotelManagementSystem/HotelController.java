package com.example.hotels.HotelManagementSystem;




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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;



@Controller
public class HotelController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username")
    private String username;

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("formData", new HotelModel());
        return "booking";
    }



    @PostMapping("/submit")
    public String sendSimpleEmail(@ModelAttribute("formData") HotelModel formData,Model model) {

        long numberOfNights = ChronoUnit.DAYS.between(formData.getCheck_in(), formData.getCheck_out());

        model.addAttribute("first_name",formData.getFirst_name());
        model.addAttribute("last_name",formData.getLast_name());
        model.addAttribute("email",formData.getEmail());
        model.addAttribute("phone_number",formData.getPhone_number());
        model.addAttribute("check_in",formData.getCheck_in());
        model.addAttribute("check_out",formData.getCheck_out());
        model.addAttribute("nights",numberOfNights);
        model.addAttribute("room_type",formData.getRoom_type());
        model.addAttribute("adults",formData.getAdults());
        model.addAttribute("children",formData.getChildren());
        model.addAttribute("pets",formData.getPets());



        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper simpleMailMessage = new MimeMessageHelper(mimeMessage,true);

            Context context = new Context();
            context.setVariable("first_name", formData.getFirst_name());
            context.setVariable("last_name",formData.getLast_name());
            context.setVariable("email", formData.getEmail());
            context.setVariable("phone_number", formData.getPhone_number());
            context.setVariable("check_in",formData.getCheck_in());
            context.setVariable("check_out",formData.getCheck_out());
            context.setVariable("nights",numberOfNights);
            context.setVariable("room_type",formData.getRoom_type());
            context.setVariable("adults",formData.getAdults());
            context.setVariable("children",formData.getChildren());
            context.setVariable("pets",formData.getPets());
            String body = templateEngine.process("email", context);
            simpleMailMessage.setFrom(username);
            simpleMailMessage.setTo(formData.getEmail());
            simpleMailMessage.setSubject("New Reservation arriving on "+formData.getCheck_in());
            simpleMailMessage.setText(body,true);

            javaMailSender.send(mimeMessage);


        } catch (MailException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return "email";

    }

}



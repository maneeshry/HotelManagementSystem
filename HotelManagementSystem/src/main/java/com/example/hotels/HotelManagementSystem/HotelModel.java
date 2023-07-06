package com.example.hotels.HotelManagementSystem;
import java.time.LocalDate;
import java.util.Date;

public class HotelModel {


        String first_name;
        String last_name;
        String email;
        String phone_number;
//        String address;
//        String city;
//        String state;
//        int zip_code;
        LocalDate check_in;
        LocalDate check_out;
        String room_type;
        int adults;
        int children;
        int pets;

        public HotelModel() {

        }

        public HotelModel(String first_name, String last_name, String email, String phone_number, String address, String city,
                          String state, int zip_code, LocalDate check_in, LocalDate check_out, String room_type, int adults, int children, int pets) {
            this.first_name = first_name;
            this.last_name = last_name;
            this.email = email;
            this.phone_number = phone_number;
//            this.address = address;
//            this.city = city;
//            this.state = state;
//            this.zip_code = zip_code;
            this.check_in = check_in;
            this.check_out = check_out;
            this.room_type = room_type;
            this.adults = adults;
            this.children = children;
            this.pets = pets;
        }

        public String getFirst_name() {
            return this.first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return this.last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone_number() {
            return this.phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }
//
//        public String getAddress() {
//            return this.address;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//
//        public String getCity() {
//            return this.city;
//        }
//
//        public void setCity(String city) {
//            this.city = city;
//        }
//
//        public String getState() {
//            return this.state;
//        }
//
//        public void setState(String state) {
//            this.state = state;
//        }
//
//        public int getZip_code() {
//            return this.zip_code;
//        }
//
//        public void setZip_code(int zip_code) {
//            this.zip_code = zip_code;
//        }

        public LocalDate getCheck_in() {
            return this.check_in;
        }

        public void setCheck_in(LocalDate check_in) {
            this.check_in = check_in;
        }

        public LocalDate getCheck_out() {
            return this.check_out;
        }

        public void setCheck_out(LocalDate check_out) {
            this.check_out = check_out;
        }

        public String getRoom_type() {
            return this.room_type;
        }

        public void setRoom_type(String room_type) {
            this.room_type = room_type;
        }

        public int getAdults() {
            return this.adults;
        }

        public void setAdults(int adults) {
            this.adults = adults;
        }

        public int getChildren() {
            return this.children;
        }

        public void setChildren(int children) {
            this.children = children;
        }

        public int getPets() {
            return this.pets;
        }

        public void setPets(int pets) {
            this.pets = pets;
        }

    }


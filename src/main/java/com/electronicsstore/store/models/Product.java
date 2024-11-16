package com.electronicsstore.store.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private byte[] image;

    private String type, name_model, description, prosecc_model, video_card_model, color, operation_system, screen_resolution;
    private String screen_technology, screen, hdd_capacity, sdd_capacity;

    private int ram_capacity, battery_capacity, launch_date;
    private double grade, screen_diagonal, price;

    public String getScreen_technology() {
        return screen_technology;
    }

    public void setScreen_technology(String screen_technology) {
        this.screen_technology = screen_technology;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getSdd_capacity() {
        return sdd_capacity;
    }

    public void setSdd_capacity(String sdd_capacity) {
        this.sdd_capacity = sdd_capacity;
    }

    public int getBattery_capacity() {
        return battery_capacity;
    }

    public void setBattery_capacity(int battery_capacity) {
        this.battery_capacity = battery_capacity;
    }

    public int getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(int launch_date) {
        this.launch_date = launch_date;
    }

    public double getScreen_diagonal() {
        return screen_diagonal;
    }

    public void setScreen_diagonal(double screen_diagonal) {
        this.screen_diagonal = screen_diagonal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName_model() {
        return name_model;
    }

    public void setName_model(String name_model) {
        this.name_model = name_model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProsecc_model() {
        return prosecc_model;
    }

    public void setProsecc_model(String prosecc_model) {
        this.prosecc_model = prosecc_model;
    }

    public String getVideo_card_model() {
        return video_card_model;
    }

    public void setVideo_card_model(String video_card_model) {
        this.video_card_model = video_card_model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOperation_system() {
        return operation_system;
    }

    public void setOperation_system(String operation_system) {
        this.operation_system = operation_system;
    }

    public String getScreen_resolution() {
        return screen_resolution;
    }

    public void setScreen_resolution(String screen_resolution) {
        this.screen_resolution = screen_resolution;
    }

    public int getRam_capacity() {
        return ram_capacity;
    }

    public void setRam_capacity(int ram_capacity) {
        this.ram_capacity = ram_capacity;
    }

    public String getHdd_capacity() {
        return hdd_capacity;
    }

    public void setHdd_capacity(String hdd_capacity) {
        this.hdd_capacity = hdd_capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    public Product() {}

    public Product(String type, String name_model, String description, String prosecc_model, String video_card_model, String color, String operation_system, String screen_resolution, int ram_capacity, String hdd_capacity, String sdd_capacity, int battery_capacity, byte[] image, String screen_technology, String screen, double price, int launch_date, double screen_diagonal, double grade) {
        this.type = type;
        this.name_model = name_model;
        this.description = description;
        this.prosecc_model = prosecc_model;
        this.video_card_model = video_card_model;
        this.color = color;
        this.operation_system = operation_system;
        this.screen_resolution = screen_resolution;
        this.ram_capacity = ram_capacity;
        this.hdd_capacity = hdd_capacity;
        this.sdd_capacity = sdd_capacity;
        this.battery_capacity = battery_capacity;
        this.screen_technology = screen_technology;
        this.screen = screen;
        this.price = price;
        this.launch_date = launch_date;
        this.screen_diagonal = screen_diagonal;
        this.grade = grade;
        this.image = image;
    }


}

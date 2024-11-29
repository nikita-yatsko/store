package com.electronicsstore.store.models;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.PostMapping;

@Entity
public class Product {
    // поменять поля
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private byte[] image;
    private String type;
    private String nameModel;
    private String description;
    private String processorModel;
    private String videoCardModel;
    private String operationSystem;
    private String screenResolution;
    private String color;
    private String screenTechnology;
    private String screen;
    private String hddCapacity;
    private String ssdCapacity;
    private int ramCapacity;
    private int batteryCapacity;
    private int launchDate;
    private double grade;
    private double screenDiagonal;
    private double price;


    public String getScreenTechnology() {
        return screenTechnology;
    }

    public void setScreenTechnology(String screen_technology) {
        this.screenTechnology = screen_technology;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getSsdCapacity() {
        return ssdCapacity;
    }

    public void setSsdCapacity(String sdd_capacity) {
        this.ssdCapacity = sdd_capacity;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int battery_capacity) {
        this.batteryCapacity = battery_capacity;
    }

    public int getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(int launch_date) {
        this.launchDate = launch_date;
    }

    public double getScreenDiagonal() {
        return screenDiagonal;
    }

    public void setScreenDiagonal(double screen_diagonal) {
        this.screenDiagonal = screen_diagonal;
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

    public String getNameModel() {
        return nameModel;
    }

    public void setNameModel(String name_model) {
        this.nameModel = name_model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProcessorModel() {
        return processorModel;
    }

    public void setProcessorModel(String prosecc_model) {
        this.processorModel = prosecc_model;
    }

    public String getVideoCardModel() {
        return videoCardModel;
    }

    public void setVideoCardModel(String video_card_model) {
        this.videoCardModel = video_card_model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operation_system) {
        this.operationSystem = operation_system;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screen_resolution) {
        this.screenResolution = screen_resolution;
    }

    public int getRamCapacity() {
        return ramCapacity;
    }

    public void setRamCapacity(int ram_capacity) {
        this.ramCapacity = ram_capacity;
    }

    public String getHddCapacity() {
        return hddCapacity;
    }

    public void setHddCapacity(String hdd_capacity) {
        this.hddCapacity = hdd_capacity;
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


    public Product(String type, String nameModel, String description, String processorModel, String videoCardModel, String color, String operationSystem, String screenResolution, int ramCapacity, String hddCapacity, String ssdCapacity, int batteryCapacity, byte[] image, String screenTechnology, String screen, double price, int launchDate, double screenDiagonal, double grade) {
        this.type = type;
        this.nameModel = nameModel;
        this.description = description;
        this.processorModel = processorModel;
        this.videoCardModel = videoCardModel;
        this.color = color;
        this.operationSystem = operationSystem;
        this.screenResolution = screenResolution;
        this.ramCapacity = ramCapacity;
        this.hddCapacity = hddCapacity;
        this.ssdCapacity = ssdCapacity;
        this.batteryCapacity = batteryCapacity;
        this.screenTechnology = screenTechnology;
        this.screen = screen;
        this.price = price;
        this.launchDate = launchDate;
        this.screenDiagonal = screenDiagonal;
        this.grade = grade;
        this.image = image;
    }
}
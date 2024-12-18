package com.electronicsstore.store.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    // поменять поля
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long product_id;

    @Column(name = "image")
    private byte[] image;

    @NotEmpty(message = "Type should not be empty")
    @Size(min = 2, max = 50, message = "Should be between 2 and 50 characters")
    @Column(name = "type")
    private String type;

    @NotEmpty(message = "Type should not be empty")
    @Size(min = 2, max = 50, message = "Should be between 2 and 50 characters")
    @Column(name = "nameModel")
    private String nameModel;

    @Column(name = "description")
    private String description;

    @NotEmpty(message = "Type should not be empty")
    @Column(name = "processorModel")
    private String processorModel;

    @NotEmpty(message = "Type should not be empty")
    @Column(name = "videoCardModel")
    private String videoCardModel;

    @NotEmpty(message = "Type should not be empty")
    @Column(name = "operationSystem")
    private String operationSystem;

    @NotEmpty(message = "Type should not be empty")
    @Column(name = "screenResolution")
    private String screenResolution;

    @NotEmpty(message = "Type should not be empty")
    @Column(name = "color")
    private String color;

    @NotEmpty(message = "Type should not be empty")
    @Column(name = "screenTechnology")
    private String screenTechnology;

    @NotEmpty(message = "Type should not be empty")
    @Column(name = "screen")
    private String screen;

    @NotEmpty(message = "Type should not be empty")
    @Column(name = "hddCapacity")
    private String hddCapacity;

    @NotEmpty(message = "Type should not be empty")
    @Column(name = "ssdCapacity")
    private String ssdCapacity;

    @Column(name = "ramCapacity")
    private int ramCapacity;

    @Column(name = "batteryCapacity")
    private int batteryCapacity;

    @Column(name = "launchDate")
    private int launchDate;

    @Column(name = "grade")
    private double grade;

    @Column(name = "screenDiagonal")
    private double screenDiagonal;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id") // Change to user_id
    private User owner;
}
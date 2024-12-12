package com.store.models;

import jakarta.persistence.*;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    // поменять поля
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    //todo refact id to product_id
    private Long id;

    @Column(name = "image")
    private byte[] image;

//    @NotEmpty(message = "Type should not be empty")
//    @Size(min = 2, max = 50, message = "Should be between 2 and 50 characters")
//    @Column(name = "type")
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

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id") // Change to user_id
    private User owner;
}
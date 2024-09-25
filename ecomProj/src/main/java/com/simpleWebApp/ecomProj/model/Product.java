package com.simpleWebApp.ecomProj.model;

import java.math.BigDecimal;
import java.util.Date;

//import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
//creates a contructor for all the variable
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;

    //when we first set the date, it is set in the 'right' format so this return invalid
    //do this so we change the date format
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date releaseDate;
    private boolean productAvailable;
    private int stockQuantity;

    //for images
    private String imageName;
    private String imageType;
    //Lob == large
    @Lob
    private byte[] imageData;
}

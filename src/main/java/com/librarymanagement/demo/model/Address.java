package com.librarymanagement.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String landmark;
    private String addressType;


    @ManyToOne
    @JoinColumn(name = "user_id")  // Foreign key column in Address table
    @JsonBackReference
    @ToString.Exclude
    private User user;  // Many addresses belong to one user
}

package com.hackcent.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Column
    private String street; // e.g., 100 Main ST

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String zip;  
}
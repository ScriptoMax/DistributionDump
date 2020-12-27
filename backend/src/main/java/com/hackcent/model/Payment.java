package com.hackcent.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long pid;

    @ManyToOne
    @JoinColumn(name = "hid")
    private Hackathon hackathon;

    @Column(name = "uid")    
    private Long uid;
}
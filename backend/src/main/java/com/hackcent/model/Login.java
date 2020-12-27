package com.hackcent.model;

import javax.persistence.*;
import javax.websocket.ClientEndpoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "credentials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login{

    @Id
    @Column
    private String email;

    @Column
    private String password;   
}
package com.hackcent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organisations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organisation {
	
    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long oid;

    @Column(unique = true)
    private String name;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "organisation")
    @JsonIgnore
    private List<User> members = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "uid")
    private User owner;

    @Column
    private String desciption;    
}
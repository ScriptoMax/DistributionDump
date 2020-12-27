package com.hackcent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long tid;

    @ManyToOne
    @JoinColumn(name = "hid")
    private Hackathon hackathon;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "team")
    @JsonIgnore
    private List<TeamMember> teamMembers = new ArrayList<>();

    @Column
    private float score;

    @Column
    private String codeUrl;   
}
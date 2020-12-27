package com.hackcent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hackathons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hackathon {
	
    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long hid;

    @Column
    private String name;

    @Column
    private String startDate;

    @Column
    private String finishDate;

    @Column
    private String postedOn;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "screenName")
    private User owner;

    @Column
    private int minTeamSize;

    @Column
    private int maxTeamSize;

    @Column
    private float fee;

    @Column
    private int discount;

    @Column
    private String status;

    @Column
    private String judgeScreenName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "hackathon")
    @JsonIgnore
    private List<Team> teams = new ArrayList<>();

    @Column
    private String sponsors;
}
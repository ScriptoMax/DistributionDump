package com.hackcent.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long tmid;

    @ManyToOne
    @JoinColumn(name = "tid")
    private Team team;

    @Column(name = "uid")    
    private long memberId;

    @Column(name = "member_role")
    private String memberRole;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "amount")
    private float amount;

    @Column(name = "payment_date")
    private String paymentDate;   
}
package com.hackcent.model;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	
    @Id
    @Column
    private String rid;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User userObj;

    @ManyToOne
    @JoinColumn(name = "hid", referencedColumnName = "hid")
    private Hackathon hackathon;

    @Column(name = "userRole")
    private String userRole;  
}
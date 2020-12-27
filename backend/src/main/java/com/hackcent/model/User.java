package com.hackcent.model;

import javax.persistence.*;
import javax.websocket.ClientEndpoint;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@Column
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long uid;

    @Column
    private String name;

    @Column(unique = true)
    private String screenName;

    @Column(unique = true)
    private String email;
    
    //@Column
    //private String password;

    @Column
    private String title;

    @Column
    private String aboutMe;   

    @Column
    private String userType;

    @Column
    private String userStatus;

    @Column
    private int attemptCount;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "oid")
    private Organisation organisation;

    @Column
    private String orgStatus;

    @ManyToMany
    @JoinTable(
            name = "roles",
            joinColumns = @JoinColumn(name = "uid"),
            inverseJoinColumns = @JoinColumn(name = "hid")
    )
    private List<Hackathon> hackathons = new ArrayList<>(); 
    
    /*@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Set<GrantedAuthority> setAuths = new HashSet<>();
		if(email != null && email.endsWith("@admin.com"))
			setAuths.add(new SimpleGrantedAuthority("ADMIN"));
		else
			setAuths.add(new SimpleGrantedAuthority("USER"));
		return setAuths;
	}

	@Override
	public String getPassword() {		
		return null;
	}

	@Override
	public String getUsername() {		
		return screenName;
	}

	@Override
	public boolean isAccountNonExpired() {		
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {		
		return false;
	}

	@Override
	public boolean isEnabled() {		
		return false;
	}*/
}
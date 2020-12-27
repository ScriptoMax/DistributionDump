package com.hackcent.service.utils;

import java.util.List;

import com.hackcent.model.Address;
import com.hackcent.model.Hackathon;
import com.hackcent.model.Organisation;
import com.hackcent.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class UserOrgResponse{
    private String organisationName;
    private String status;   
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long uid;
    private String name;
    private String screenname;
    private String title;
    private String email;
    private String aboutMe;    
    private String userType;
    private String userStatus;
    private Address address;
    private UserOrgResponse organisation;      
    
    public UserResponse(User user) {
        this.uid = user.getUid();
        this.name = user.getName();
        this.aboutMe = user.getAboutMe();
        this.address = user.getAddress();
        this.email = user.getEmail();        
        this.screenname = user.getScreenName();
        this.title = user.getTitle();
        this.userType = user.getUserType();
        this.userStatus = user.getUserStatus();

        Organisation org = user.getOrganisation();
        if(org != null){
            this.organisation = new UserOrgResponse(org.getName(),user.getOrgStatus());
        }
    }
}
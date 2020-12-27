package com.hackcent.service.utils;

import java.util.ArrayList;
import java.util.List;

import com.hackcent.model.Address;
import com.hackcent.model.Hackathon;
import com.hackcent.model.Organisation;
import com.hackcent.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
class OrgRequestMembers{	
    private String name;
    private String screenName;
    private String email;    
}

@Data
public class OrganisationResponse {
    private long organisationId;
    private String organisationName;
    private String description;
    private OrgRequestMembers owner;
    private List<OrgRequestMembers> members = new ArrayList<>();
    private List<OrgRequestMembers> requestedMembers = new ArrayList<>();

    public OrganisationResponse(Organisation organisation) {
        this.organisationId = organisation.getOid();
        this.organisationName = organisation.getName();
        this.description = organisation.getDesciption();

        User user = organisation.getOwner();
        owner = new OrgRequestMembers(user.getName(), user.getScreenName(), user.getEmail());

        List<User> tmp = organisation.getMembers();
        for(User u : tmp){
            if(u.getOrgStatus().equals("Requested"))
                requestedMembers.add(new OrgRequestMembers(u.getName(), u.getScreenName(), u.getEmail()));
            else if(u.getOrgStatus().equals("Approved"))
                members.add(new OrgRequestMembers(u.getName(), u.getScreenName(), u.getEmail()));
        }
    }  
}
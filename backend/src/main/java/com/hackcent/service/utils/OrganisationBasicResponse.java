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
public class OrganisationBasicResponse {

    private Long organisationId;
    private String organisationName;
    private String description;
    private OrgRequestMembers owner;


    public OrganisationBasicResponse(Organisation organisation){
        this.organisationId = organisation.getOid();
        this.organisationName = organisation.getName();
        this.description = organisation.getDesciption();

        User user = organisation.getOwner();

        owner = new OrgRequestMembers(user.getName(), user.getScreenName(), user.getEmail());
    }    
}
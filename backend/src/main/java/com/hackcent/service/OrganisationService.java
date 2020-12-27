package com.hackcent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackcent.model.Organisation;
import com.hackcent.model.User;
import com.hackcent.repository.OrganisationRepository;
import com.hackcent.repository.UserRepository;
import com.hackcent.service.utils.OrganisationBasicResponse;
import com.hackcent.service.utils.OrganisationResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrganisationService {

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private UserRepository userRepository;
    
    public ResponseEntity<?> createOrganisation(String name,
                                                String description,
                                                String ownerName) {

        if(name == null || ownerName == null || name == "" || ownerName=="" ) {
            return ResponseEntity.badRequest().body("Please fill up a unique name");
        }

        User owner = null;
        if(ownerName != null)
            owner = userRepository.findUserByScreenName(ownerName);

        if(owner == null)
            return ResponseEntity.badRequest().body("No such owner");

        Organisation tmpOrg = organisationRepository.findOrganisationByName(name);
        if(tmpOrg != null)
            return ResponseEntity.badRequest().body("Organisation name already in use");

        Organisation organisation = new Organisation();

        organisation.setName(name);
        if(description != null) {
            organisation.setDesciption(description);       
            organisation.setOwner(owner);
        }

        organisationRepository.save(organisation);
        OrganisationResponse organisationResponse = new OrganisationResponse(organisation);

        return ResponseEntity.ok().body(organisationResponse);
    }
    
    public ResponseEntity<?> readOrganisation(String name, String screenName){
       
        Organisation organisation = organisationRepository.findOrganisationByName(name);
        if(organisation == null){
            return ResponseEntity.badRequest().body("No such organisation");
        }

        if(organisation.getOwner().getScreenName().equals(screenName)) {
            OrganisationResponse organisationResponse = new OrganisationResponse(organisation);
            return ResponseEntity.ok().body(organisationResponse);
        }
        else{
            OrganisationBasicResponse organisationBasicResponse = new OrganisationBasicResponse(organisation);
            return ResponseEntity.ok().body(organisationBasicResponse);
        }
    }
    
    public ResponseEntity<?> readOrganisations(String screenName){
        List<Organisation> orgObjects = organisationRepository.findAll();
        List<OrganisationResponse> userOrg = new ArrayList<>();
        for(Organisation org : orgObjects) {           
            if(org.getOwner().getScreenName().equals(screenName)) {
                OrganisationResponse organisationResponse = new OrganisationResponse(org);
                userOrg.add(organisationResponse);
            }
        }       

        return ResponseEntity.ok().body(userOrg);
    }
   
    public ResponseEntity<?> readOrganisationNames(){
        List<OrganisationResponse> orgs = new ArrayList<OrganisationResponse>();
        List<Organisation> orgNames = organisationRepository.findAll();
        for (Organisation org : orgNames) {        	
            OrganisationResponse organisationResponse = new OrganisationResponse(org);
            orgs.add(organisationResponse);
        }
        return ResponseEntity.ok().body(orgs);
    }
}
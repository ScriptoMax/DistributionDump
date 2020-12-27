package com.hackcent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hackcent.service.OrganisationService;
import com.hackcent.service.UserService;
import com.hackcent.websession.utils.ResponseHeader;

@RestController
public class OrganisationController {

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private UserService userService;

    private ResponseHeader responseHeader = new ResponseHeader();

    @RequestMapping(value = "/organisation", method = {RequestMethod.POST})
    public ResponseEntity<?> postOrganisation(@RequestParam(value = "name",required = true) String name,
                                              @RequestParam(value = "ownerName",required = true) String ownerName,
                                              @RequestParam(value = "description",required = false) String desctiption){

        ResponseEntity<?> responseEntity = organisationService.createOrganisation(name, desctiption, ownerName);

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/organisation/{orgName}", method = {RequestMethod.GET})
    public ResponseEntity<?> getOrganisation(@PathVariable String orgName,
                                             @RequestParam(value = "screenName", required = true) String screenName){

        ResponseEntity<?> responseEntity = organisationService.readOrganisation(orgName,screenName);

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/organisation/owner/{screenName}", method = {RequestMethod.GET})
    public ResponseEntity<?> getOrganisations(@PathVariable String screenName){

        ResponseEntity<?> responseEntity = organisationService.readOrganisations(screenName);        

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/organisation/{name}/join/{screenName}", method = {RequestMethod.POST})
    public ResponseEntity<?> joinOrganisation(@PathVariable String name,
                                              @PathVariable String screenName){

        ResponseEntity<?> responseEntity = userService.updateUserOrganisation(screenName, name, "Requested");

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/organisation/{name}/approve/{screenName}", method = {RequestMethod.POST})
    public ResponseEntity<?> approveJoinRequest(@PathVariable String name,
                                                @PathVariable String screenName){
        ResponseEntity<?> responseEntity = userService.updateUserOrganisation(screenName, name, "Approved");

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/organisation/{name}/leave/{screenName}", method = {RequestMethod.POST})
    public ResponseEntity<?> leaveRequest(@PathVariable String name,
                                          @PathVariable String screenName){

        ResponseEntity<?> responseEntity = userService.updateUserOrganisation(screenName, name, "Rejected");

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/organisation/names", method = {RequestMethod.GET})
    public ResponseEntity<?> getOrganisationNames(){
    	
        ResponseEntity<?> responseEntity = organisationService.readOrganisationNames();
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }
}
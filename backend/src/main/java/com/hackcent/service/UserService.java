package com.hackcent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackcent.model.Address;
import com.hackcent.model.Organisation;
import com.hackcent.model.User;
import com.hackcent.repository.OrganisationRepository;
import com.hackcent.repository.UserRepository;
import com.hackcent.service.utils.UserResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganisationRepository organisationRepository;
    
    public ResponseEntity<?> createUser(String name,
                                        String screenName,
                                        String email,
                                        String userRole,
                                        String userStatus){

        if(name == null || screenName == null || email == null || name == "" || screenName == "" || email == "") {
            return ResponseEntity.badRequest().body("Please fill up all the fields");
        }

        if(!email.contains("@")){
            return ResponseEntity.badRequest().body("Invalid email id");
        }

        User userToVerify = userRepository.findUserByEmail(email);
        if(userToVerify != null){
            System.out.println("user exists");
            return ResponseEntity.badRequest().body("Email id already in use");
        }

        User newestUser = userRepository.findUserByScreenName(screenName);
        if(newestUser != null){
            return ResponseEntity.badRequest().body("screen name already in use");
        }

        User user = new User();        
        user.setScreenName(screenName);
        user.setName(name);
        user.setEmail(email);
        user.setUserStatus(userStatus);

        String[] userRoles = email.split("@");
        if (userRoles[1].equals("admin.com")) {
            user.setUserType("admin");
        } else {
            user.setUserType("hacker");
        }

        try {
            userRepository.save(user);
        } catch (Exception exception){
            return ResponseEntity.badRequest().body("name/email is wrong");
        }

        return ResponseEntity.ok().body(user);
    }
    
    public ResponseEntity<?> updateUser(String name,
                                        String screenName,
                                        String title,
                                        String aboutMe,                                       
                                        String street,
                                        String city,
                                        String state,
                                        String zip) {

        User user = userRepository.findUserByScreenName(screenName);

        if(name != null)
            user.setName(name);

        if(screenName != null)
            user.setScreenName(screenName);

        if(title != null)
            user.setTitle(title);

        if(aboutMe != null)
            user.setAboutMe(aboutMe);
        
        Address address = user.getAddress();

        if (address == null){
            address = new Address();
            address.setStreet(street);
            address.setCity(city);
            address.setState(state);
            address.setZip(zip);
        } else {
            if (street != null)
                address.setStreet(street);
            if (city != null)
                address.setCity(city);
            if (state != null)
                address.setState(state);
            if (zip != null)
                address.setZip(zip);
        }
        
        user.setAddress(address);
        UserResponse userResponse = new UserResponse(user);

        return ResponseEntity.ok().body(userResponse);
    }
   
    public ResponseEntity<?> updateUserOrganisation(String screenName,
                                                    String orgName,
                                                    String orgStatus){

        User user = userRepository.findUserByScreenName(screenName);

        Organisation organisation = organisationRepository.findOrganisationByName(orgName);

        if(user.getName().equals(organisation.getOwner().getName()))
            return ResponseEntity.badRequest().body("owner cannot join same org");

        user.setOrganisation(organisation);
        user.setOrgStatus(orgStatus);

        if(orgStatus.equals("Requested"))
            return ResponseEntity.ok().body("Request sent of organisation owner");
        else if (orgStatus.equals("Approved"))
            return ResponseEntity.ok().body(screenName + " is now part of organisation");
        else {
            user.setOrganisation(null);
            user.setOrgStatus(null);
            return ResponseEntity.ok().body(screenName + " is no longer part of organisation");
        }
    }
    
    public ResponseEntity<?> readUser(String screenName){
        User user = userRepository.findUserByScreenName(screenName);

        if(user == null){
            return ResponseEntity.badRequest().body("No such user");
        }

        UserResponse userResponse = new UserResponse(user);

        return ResponseEntity.ok().body(userResponse);
    }
    
    public ResponseEntity<?> readScreenNames(String screenName){
        List<User> users = userRepository.findAll();
        List<UserResponse> names = new ArrayList<>();

        if(users != null)
            for (User user : users) {
                UserResponse userResponse = new UserResponse(user);
                names.add(userResponse);
            }

        return ResponseEntity.ok().body(names);
    }
}
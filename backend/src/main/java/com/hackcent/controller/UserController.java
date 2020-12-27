package com.hackcent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import com.google.firebase.auth.UserRecord;
import com.hackcent.model.User;
import com.hackcent.service.UserService;
import com.hackcent.websession.utils.ResponseHeader;

@RestController
public class UserController{

    @Autowired
    private UserService userService;

    private ResponseHeader responseHeader = new ResponseHeader();

    @RequestMapping(value = "/user" , method = {RequestMethod.POST})
    public ResponseEntity<?> postUser(@RequestParam(value = "name",required = true) String name,
                                      @RequestParam(value = "screenName",required = true)  String screenName,
                                      @RequestParam(value = "email",required = true) String email,
                                      @RequestParam(value = "usertype", required = false,defaultValue = "hacker") String userType) {   	
    	
    	ResponseEntity<?> responseEntity = userService.createUser(name, screenName, email, userType, "registered");

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user/{screenName}", method = {RequestMethod.GET})
    public ResponseEntity<?> getUser(@PathVariable String screenName){
        ResponseEntity<?> responseEntity = userService.readUser(screenName);

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{screenName}", method = {RequestMethod.POST})
    public ResponseEntity<?> updateUser(@PathVariable String screenName,
                                        @RequestParam(value = "name",required = false) String name,                                       
                                        @RequestParam(value = "title",required = false) String title,
                                        @RequestParam(value = "street",required = false)  String street,
                                        @RequestParam(value = "city",required = false)  String city,
                                        @RequestParam(value = "state",required = false)  String state,
                                        @RequestParam(value = "zip",required = false)  String zip,
                                        @RequestParam(value = "aboutMe",required = false)  String aboutMe) {
        ResponseEntity<?> responseEntity = userService.updateUser(name, screenName, title, aboutMe, street, city, state, zip);

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user/names",method = {RequestMethod.GET})
    public ResponseEntity<?> getScreenNames(@RequestParam(value = "screenName",required = true) String screenName){
        ResponseEntity<?> responseEntity = userService.readScreenNames(screenName);

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }
}
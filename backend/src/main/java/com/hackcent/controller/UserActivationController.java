package com.hackcent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackcent.service.ActivationService;
import com.hackcent.websession.utils.ResponseHeader;

@RestController
public class UserActivationController {

    @Autowired
    private ActivationService activationService;

    private ResponseHeader responseHeader = new ResponseHeader();

    @RequestMapping(value = "/activate", method = {RequestMethod.GET})
    public ResponseEntity<?> userActivation(@RequestParam(value = "email",required = true) String email) {
        ResponseEntity<?> responseEntity = activationService.activateUserAccount(email);

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }
}
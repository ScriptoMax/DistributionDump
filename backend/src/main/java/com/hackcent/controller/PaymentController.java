package com.hackcent.controller;

import com.hackcent.service.PaymentService;
import com.hackcent.websession.utils.ResponseHeader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;  

    ResponseHeader responseHeader = new ResponseHeader();

    @RequestMapping(value = "/payment/{screenname}/amount/{hid}" , method = {RequestMethod.GET})
    public ResponseEntity<?> getPaymentAmount(@PathVariable String screenName,
                                              @PathVariable Long hid) {
        ResponseEntity<?> responseEntity = paymentService.getPaymentAmount(screenName, hid);
        
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/payment/{screenName}/paid/{hid}", method = {RequestMethod.POST})
    public ResponseEntity<?> updatePaymentStatus(@PathVariable String screenName,
                                                 @PathVariable Long hid){
        System.out.println(screenName);
        ResponseEntity<?> responseEntity = paymentService.createPayment(screenName, hid);

        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/payment/report/{hackname}", method = {RequestMethod.GET})
    public ResponseEntity<?> getPaymentReport(@PathVariable String hackname){
        ResponseEntity<?> responseEntity = paymentService.getTeamsPayment(hackname);
        
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }

    @RequestMapping(value = "/payment/expensereport/{hackname}", method = {RequestMethod.GET})
    public ResponseEntity<?> getExpenseReport(@PathVariable String hackname){
        ResponseEntity<?> responseEntity = paymentService.getEarningReport(hackname);
        
        return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.OK);
    }
}
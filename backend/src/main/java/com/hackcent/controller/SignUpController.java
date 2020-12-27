/*package com.hackcent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.hackcent.exception.ErrorResponse;
import com.hackcent.model.User;
import com.hackcent.service.LoginService;
import com.hackcent.service.UserService;
import com.hackcent.websession.utils.ResponseHeader;

import java.nio.channels.NonWritableChannelException;
import javassist.expr.NewArray;

@RestController
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;  

    private ResponseHeader responseHeader = new ResponseHeader();

    @RequestMapping(value = "/signup", method = {RequestMethod.POST})
    public ResponseEntity<?> postSignUp(@RequestParam(value = "name",required = true) String name,
                                        @RequestParam(value = "screenName",required = true)  String screenName,
                                        @RequestParam(value = "email",required = true) String email,
                                        @RequestParam(value = "password",required = true) String password,
                                        @RequestParam(value = "userType", required = false, defaultValue = "hacker") String userType) throws Exception {

    	/*System.out.println("point 1");
        ResponseEntity<?> responseEntity;

        //User newUserObj = new User();
    	//newUserObj.setScreenName(screenName);
    	//newUserObj.setName(name);
    	//newUserObj.setEmail(email);
    	//newUserObj.setPassword(password);
    	
    	CreateRequest request = new CreateRequest()
			    .setEmail(email)
			    .setEmailVerified(false)
			    .setPassword(password)
			    .setDisplayName(screenName)
			    .setDisabled(false);
    	
    	UserRecord record;
    	String uid = null;
    	
    	try {
			record = FirebaseAuth.getInstance().createUser(request);
			uid = record.getUid();
			//user.setUuid(userRecord.getUid());
			userService.createUser(uid, name, screenName, email, userType, "registered");
			responseEntity = loginService.createLogin(email,password);
			
			new Thread(() -> {
	            System.out.println("Sending activation email to " + email);           
	        }).start();
			return new ResponseEntity<>(ResponseEntity.ok().body("Please activate account using link sent to email"), responseHeader.getHeader(), HttpStatus.CREATED);
	     	//return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ErrorResponse("Email already exists"), HttpStatus.CONFLICT);
		} catch (Exception e) {
			try {
				FirebaseAuth.getInstance().deleteUser(uid);
			} catch (FirebaseAuthException e1) {
				e1.printStackTrace();
				throw e1;
			}
			return new ResponseEntity<>(new ErrorResponse("Error handling sign-up request, canceling operation gets complete"), HttpStatus.INTERNAL_SERVER_ERROR);
        
        responseEntity = userService.createUser(name, screenName, email, userType, "registered");
        if(responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("called######");
            return new ResponseEntity<>(responseEntity,responseHeader.getHeader(),HttpStatus.CREATED);
        }

        responseEntity = loginService.createLogin(email,password);
        if(responseEntity.getStatusCode() != HttpStatus.OK){
            return new ResponseEntity<>(responseEntity, responseHeader.getHeader(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        new Thread(() -> {
            System.out.println("Sending activation email to " + email);           
        }).start();*/

        //return new ResponseEntity<>(ResponseEntity.ok().body("Please activate account using link sent to email"), responseHeader.getHeader(), HttpStatus.CREATED);
 //   }
//}
//}

package com.hackcent.controller;

import com.hackcent.websession.utils.ResponseHeader;
import com.hackcent.service.LoginService;
import com.hackcent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;  

    private ResponseHeader responseHeader = new ResponseHeader();

    @RequestMapping(value = "/signup", method = {RequestMethod.POST})
    public ResponseEntity<?> postSignUp(@RequestParam(value = "name",required = true) String name,
                                        @RequestParam(value = "screenName",required = true)  String screenName,
                                        @RequestParam(value = "email",required = true) String email,
                                        @RequestParam(value = "password",required = true) String password,
                                        @RequestParam(value = "userType", required = false,defaultValue = "hacker") String userType){

        ResponseEntity responseEntity;

        responseEntity = userService.createUser(name,screenName,email,userType,"registered");
        if(responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("called######");
            return new ResponseEntity<>(responseEntity,responseHeader.getHeader(),HttpStatus.CREATED);
        }

        responseEntity = loginService.createLogin(email,password);
        if(responseEntity.getStatusCode() != HttpStatus.OK){
            return new ResponseEntity<>(responseEntity,responseHeader.getHeader(),HttpStatus.CREATED);
        }

        new Thread(() -> {
            System.out.println("Sending activation email to "+email);            
        }).start();

        return new ResponseEntity<>(ResponseEntity.ok().body("Please activate account using link sent to email"),responseHeader.getHeader(), HttpStatus.CREATED);
    }
}
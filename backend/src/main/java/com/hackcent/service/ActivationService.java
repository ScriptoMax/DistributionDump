package com.hackcent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hackcent.model.User;
import com.hackcent.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivationService {
	
    @Autowired
    private UserRepository userRepository;
    
    public ResponseEntity<?> activateUserAccount(String email){
        User user = userRepository.findUserByEmail(email);

        if(user == null){
            return ResponseEntity.badRequest().body("SignUp is needed ahead of login");
        }

        user.setUserStatus("Active");

        return ResponseEntity.ok().body(user);
    }
}
package com.hackcent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackcent.model.Login;
import com.hackcent.model.User;
import com.hackcent.repository.LoginRepository;
import com.hackcent.repository.UserRepository;
import com.hackcent.service.utils.UserResponse;

@Service
@Transactional
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> createLogin(String email, String password) {
        Login emailLogin = loginRepository.findLoginByEmail(email);
        if(emailLogin != null){
            return ResponseEntity.badRequest().body("Account with same email was created earlier");
        }

        Login login = new Login();
        login.setEmail(email);
        login.setPassword(password);

        login = loginRepository.save(login);

        return ResponseEntity.ok().body("Valid object created");
    }

    public ResponseEntity<?> readLogin(String email, String password){
        Login login = loginRepository.findLoginByEmail(email);

        if(login == null){
            return ResponseEntity.badRequest().body("Invalid login/password");
        }

        if(login.getPassword().equals(password)){
            User user = userRepository.findUserByEmail(email);          
            if(user.getUserStatus().equals("locked")){
                return ResponseEntity.badRequest().body("Account locked. Please use forgot password and create a new password");
            }

            UserResponse userResponse = new UserResponse(user);

            return ResponseEntity.ok().body(userResponse);
        }

        else{
            return ResponseEntity.badRequest().body("Invalid login/password");
        }
    }
}
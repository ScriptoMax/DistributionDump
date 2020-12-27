package com.hackcent.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackcent.model.Login;
import com.hackcent.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
public interface LoginRepository extends JpaRepository<Login, String> {  
	
	Login findLoginByEmail(String email);
}
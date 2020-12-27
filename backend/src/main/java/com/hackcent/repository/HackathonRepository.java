package com.hackcent.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackcent.model.Hackathon;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
	
	Hackathon findHackathonByName(String name); 
}
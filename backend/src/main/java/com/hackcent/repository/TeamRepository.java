package com.hackcent.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackcent.model.Team;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
	
	Team findTeamByName(String name); 	
}
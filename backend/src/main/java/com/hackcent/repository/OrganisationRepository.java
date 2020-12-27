package com.hackcent.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackcent.model.Organisation;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
	
	Organisation findOrganisationByName(String name);    
}
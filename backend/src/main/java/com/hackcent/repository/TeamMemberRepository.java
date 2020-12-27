package com.hackcent.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackcent.model.TeamMember;
import com.hackcent.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
	
	TeamMember findTeamMemberByMemberId(Long memberId);  
}
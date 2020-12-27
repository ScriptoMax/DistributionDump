package com.hackcent.repository;

import org.hibernate.usertype.UserVersionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackcent.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User deleteByEmail(String email);
	User findUserByEmail(String email);
	User findUserByScreenName(String screenName);
}
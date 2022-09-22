package com.microservice.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.app.model.DAOUser;

@Repository
public interface UserRepository extends CrudRepository<DAOUser, Integer> {
	
	DAOUser findByUsername(String username);
	
}
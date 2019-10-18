package com.lexisnexis.telematics.cloud.aws.us.oem.gm.security.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lexisnexis.telematics.cloud.aws.us.oem.gm.security.model.DAOUser;

@Repository
public interface UserDao extends CrudRepository<DAOUser, Integer> {
	
	DAOUser findByUsername(String username);
	
}
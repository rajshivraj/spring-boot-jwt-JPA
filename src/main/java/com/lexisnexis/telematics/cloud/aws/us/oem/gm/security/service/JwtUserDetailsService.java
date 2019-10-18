package com.lexisnexis.telematics.cloud.aws.us.oem.gm.security.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lexisnexis.telematics.cloud.aws.us.oem.gm.security.dao.UserDao;
import com.lexisnexis.telematics.cloud.aws.us.oem.gm.security.model.DAOUser;
import com.lexisnexis.telematics.cloud.aws.us.oem.gm.security.model.UserDTO;

/***
 *  JWTUserDetailsService implements the Spring Security UserDetailsService interface. 
 * @param user
 * @return
 */

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	/**
	 * Method loadUserByUsername() fetches user details from the database using the username. 
	 * The Spring Security Authentication Manager calls this method for getting the user details from the 
	 * database when authenticating the user details provided by the user. 
	 * */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DAOUser user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	/**
	 * Here the password for a user is stored in encrypted format using BCrypt. 
	 * Here using the Online Bcrypt Generator you can generate the Bcrypt for a password.
	 * /
	 * */
	public DAOUser save(UserDTO user) {
		DAOUser newUser = new DAOUser();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userDao.save(newUser);
	}
}
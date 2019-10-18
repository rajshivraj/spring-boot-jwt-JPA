package com.lexisnexis.telematics.cloud.aws.us.oem.gm.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lexisnexis.telematics.cloud.aws.us.oem.gm.security.config.JwtTokenUtil;
import com.lexisnexis.telematics.cloud.aws.us.oem.gm.security.model.JwtRequest;
import com.lexisnexis.telematics.cloud.aws.us.oem.gm.security.model.JwtResponse;
import com.lexisnexis.telematics.cloud.aws.us.oem.gm.security.model.UserDTO;
import com.lexisnexis.telematics.cloud.aws.us.oem.gm.security.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	/** Generating JWT
	 * Expose a POST API with mapping /authenticate. 
	 * On passing correct username and password it will generate a JSON Web Token(JWT)
	 * */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		//authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		
		String token = null;
		/* Generating JWT */
		if(userDetails!=null) {
			token = jwtTokenUtil.generateToken(userDetails);
		}

		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	/** Validating JWT
	 * Expose a POST API with mapping /authenticate. 
	 * On passing correct username and password it will generate a JSON Web Token(JWT)
	 * It will allow access only if request has a valid JSON Web Token(JWT
	 * */	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
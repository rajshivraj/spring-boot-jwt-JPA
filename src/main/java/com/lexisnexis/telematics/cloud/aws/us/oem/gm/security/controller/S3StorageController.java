package com.lexisnexis.telematics.cloud.aws.us.oem.gm.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class S3StorageController {

	/**
	 * If user tries to access GET API with mapping /writeToS3Bucket. It will allow access only if request has a valid JSON Web Token(JWT)
	 * */
	@RequestMapping({ "/writeToS3Bucket" })
	public String writeToS3Bucket() {
		return "Success calling writeToS3Bucket";
	}

}
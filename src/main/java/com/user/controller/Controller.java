package com.user.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.UserService;

@RestController
public class Controller {
	
	@Autowired
	UserService userService;
	
	@GetMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String test() {

		return "success";
	}
	
	@GetMapping(path = "/trends", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<JSONObject> getTrends() {
	    return new ResponseEntity<>(userService.getTrends(), HttpStatus.OK);

	}
	
	@GetMapping(path = "/trendSentiment", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<JSONObject> getSentiment() {
	    return new ResponseEntity<>(userService.getTrendSentiment(), HttpStatus.OK);

	}
	
	@GetMapping(path = "/highActiveCountries", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<JSONObject> getActiveCountries() {
	    return new ResponseEntity<>(userService.getActiveCountries(), HttpStatus.OK);
	}
	

}

package com.user.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping(path = "/clearCache", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> clearCache() {
		userService.clearCache();
	    return new ResponseEntity<>("cache cleared", HttpStatus.OK);
	}
	
	@GetMapping(path = "/trends", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<JSONObject> getTrends(@RequestParam int day,@RequestParam(required = false) Integer hours) {
	    return new ResponseEntity<>(userService.getTrends(day,hours), HttpStatus.OK);

	}
	
	@GetMapping(path = "/trendSentiment", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<JSONObject> getSentiment(@RequestParam int day, @RequestParam String trend,@RequestParam(required = false) Integer hours) {
	    return new ResponseEntity<>(userService.getTrendSentiment(day,trend,hours), HttpStatus.OK);

	}
	
	@GetMapping(path = "/highActiveCountries", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<JSONObject> getActiveCountries(@RequestParam int day,@RequestParam(required = false) Integer hours) {
	    return new ResponseEntity<>(userService.getActiveCountries(day,hours), HttpStatus.OK);
	}
	

}

package com.user.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.data.DataAccessObject;

@Service
public class UserService {
	
	@Autowired
	DataAccessObject dao;
	
	public List<JSONObject> getTrends() {
		return dao.getTrends();
	}
	
	public List<JSONObject> getTrendSentiment() {
		return dao.getTrendSentiment();
	}

	public List<JSONObject> getActiveCountries() {
		return dao.getHighActivityCountries();
	}

}

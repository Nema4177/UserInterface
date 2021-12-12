package com.user.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.cache.RedisRepository;
import com.user.data.DataAccessObject;

import redis.clients.jedis.Jedis;

@Service
public class UserService {
	
	@Autowired
	DataAccessObject dao;
	
	@Autowired
	RedisRepository redisRepository;
	
	public JSONObject getTrends() {
		Jedis jedis = redisRepository.getJedis();
		JSONObject response=null;
		try {
			if(jedis.get("trends") == null) {
				response = new JSONObject();
				response.put("trends", dao.getTrends());
				jedis.set("trends",response.toString());
			}else {
				System.out.println("Retrieving from cache");
				JSONParser parser = new JSONParser();
				response = (JSONObject) parser.parse(jedis.get("trends"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public JSONObject getTrendSentiment() {
		Jedis jedis = redisRepository.getJedis();
		JSONObject response=null;
		try {
			if(jedis.get("trendSentiment") == null) {
				response = new JSONObject();
				response.put("trendSentiment", dao.getTrendSentiment());
				jedis.set("trendSentiment",response.toString());
			}else {
				System.out.println("Retrieving from cache");
				JSONParser parser = new JSONParser();
				response = (JSONObject) parser.parse(jedis.get("trendSentiment"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}

	public JSONObject getActiveCountries() {
		Jedis jedis = redisRepository.getJedis();
		JSONObject response=null;
		try {
			if(jedis.get("activeCountries") == null) {
				response = new JSONObject();
				response.put("activeCountries", dao.getHighActivityCountries());
				jedis.set("activeCountries",response.toString());
			}else {
				System.out.println("Retrieving from cache");
				JSONParser parser = new JSONParser();
				response = (JSONObject) parser.parse(jedis.get("activeCountries"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}

}

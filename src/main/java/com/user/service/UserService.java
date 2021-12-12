package com.user.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.cache.RedisRepository;
import com.user.controller.Controller;
import com.user.data.DataAccessObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

@Service
public class UserService {
	
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	DataAccessObject dao;
	
	@Autowired
	RedisRepository redisRepository;
	
	public JSONObject getTrends(int day,Integer hours) {
		Jedis jedis = redisRepository.getJedis();
		JSONObject response=null;
		try {
			if(jedis.get("trends") == null) {
				logger.info("Retrieving from DB");
				response = new JSONObject();
				if(hours == null) response.put("trends", dao.getTrends(day));
				else response.put("trends", dao.getTrends(day,hours));
				jedis.set("trends",response.toString());
			}else {
				logger.info("Retrieving from cache");
				JSONParser parser = new JSONParser();
				response = (JSONObject) parser.parse(jedis.get("trends"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public JSONObject getTrendSentiment(int day, String trend,Integer hours) {
		Jedis jedis = redisRepository.getJedis();
		JSONObject response=null;
		try {
			if(jedis.get("trendSentiment") == null) {
				logger.info("Retrieving from DB");
				response = new JSONObject();
				if(hours == null) response.put("trendSentiment", dao.getTrendSentiment(day,trend));
				else response.put("trendSentiment", dao.getTrendSentiment(hours,day,trend));
				jedis.set("trendSentiment",response.toString());
			}else {
				logger.info("Retrieving from cache");
				JSONParser parser = new JSONParser();
				response = (JSONObject) parser.parse(jedis.get("trendSentiment"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}

	public JSONObject getActiveCountries(int day,Integer hours) {
		Jedis jedis = redisRepository.getJedis();
		JSONObject response=null;
		try {
			if(jedis.get("activeCountries") == null) {
				logger.info("Retrieving from DB");
				response = new JSONObject();
				if(hours == null) response.put("activeCountries", dao.getHighActivityCountries(day));
				else response.put("activeCountries", dao.getHighActivityCountries(day,hours));
				jedis.set("activeCountries",response.toString());
			}else {
				logger.info("Retrieving from cache");
				JSONParser parser = new JSONParser();
				response = (JSONObject) parser.parse(jedis.get("activeCountries"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}

	public String getDailyReport(int day, int month) {
		return dao.getDailyReport(day, month);
	}

	public void clearCache() {
		Jedis jedis = redisRepository.getJedis();
		jedis.flushAll();
	}

}

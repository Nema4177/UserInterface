package com.user.service;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.user.cache.RedisRepository;

import redis.clients.jedis.Jedis;

@Component
public class TestService {
	
	@Autowired
	RedisRepository redisRepository;
	
	@PostConstruct
	void test() {
		
		Jedis jedis = redisRepository.getJedis();
		jedis.set("Sample_redis_key", "sample_redis_value");
		System.out.println(jedis.get("Sample_redis_key"));
		System.out.println(jedis.get("Sample_redis_key2"));
		
		JSONObject sample_json = new JSONObject();
		sample_json.put("key1", "value1");
		jedis.set("Sample_redis_json_key", sample_json.toString());
		System.out.println(jedis.get("Sample_redis_json_key"));
	}

}

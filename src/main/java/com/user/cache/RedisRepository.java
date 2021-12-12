package com.user.cache;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
public class RedisRepository {
	
	Jedis jedis;
	
	RedisRepository(){
		jedis = new Jedis("localhost");	
	}
	
	public Jedis getJedis() {
		return this.jedis;
	}
}

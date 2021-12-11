package com.user.utils;

import org.json.simple.JSONObject;

public class Utils {
	
	public static JSONObject getTrend(int hours,int day,int month,String trend,int volume){
		JSONObject trend_object = new JSONObject();
		trend_object.put("hours",hours);
		trend_object.put("day",day);
		trend_object.put("month",month);
		trend_object.put("trend",trend);
		trend_object.put("volume",volume);
		
		return trend_object;
	}
	
	public static JSONObject getTrend(String trend,int volume){
		JSONObject trend_object = new JSONObject();
		trend_object.put("trend",trend);
		trend_object.put("volume",volume);
		
		return trend_object;
	}

	public static JSONObject getTrendSentiment(String sentiment,String trend, String tweet) {
		JSONObject sentiment_object = new JSONObject();
		sentiment_object.put("sentiment",sentiment);
		sentiment_object.put("trend",trend);
		sentiment_object.put("tweet",tweet);
		return sentiment_object;
	}

	public static JSONObject getHighActivityCountry(String countries, String trend) {
		JSONObject activity_object = new JSONObject();
		activity_object.put("trend",trend);
		activity_object.put("countries",countries);
		return activity_object;
	}
}

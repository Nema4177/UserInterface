package com.user.data;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.user.controller.Controller;
import com.user.utils.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataAccessObject {
	
    private static Logger logger = LoggerFactory.getLogger(DataAccessObject.class);

	Session session;
	
	DataAccessObject() {
		
		Cluster cluster = Cluster.builder()
				.withoutJMXReporting()
		        .withClusterName("myCluster")
		        .addContactPoint("127.0.0.1")
		        //.addContactPoint("35.224.198.193")
		        .build();
		Session session = cluster.connect("tweet_analytics");
		this.session = session;
	}
	
	public void test() {
		
		ResultSet resultSet = session.execute("select * from User");
		
		for(Row rs: resultSet) {
			System.out.println(rs);
		}
	}
	
	public List<JSONObject> getTrends(int day) {
		
		List<JSONObject> trends = new LinkedList<>();
		ResultSet resultSet = session.execute("select * from trends where day="+day+" ALLOW FILTERING");
		
		for(Row rs: resultSet) {
			trends.add(Utils.getTrend(rs.getString(4), rs.getInt(5)));
			logger.info(rs.toString());
		}
		return trends;
	}
	
	public List<JSONObject> getTrends(int day,int hours) {
		
		List<JSONObject> trends = new LinkedList<>();
		ResultSet resultSet = session.execute("select * from trends where day="+day+" AND hours="+hours+" ALLOW FILTERING");
		
		for(Row rs: resultSet) {
			trends.add(Utils.getTrend(rs.getString(4), rs.getInt(5)));
			logger.info(rs.toString());
		}
		return trends;
	}
	
	public List<JSONObject> getTrendSentiment(int day,String trend) {
		
		List<JSONObject> trendSentiment = new LinkedList<>();
		ResultSet resultSet = session.execute("select * from tweet_sentiment where day="+day+" AND trend='"+trend+"' ALLOW FILTERING");
		
		for(Row rs: resultSet) {
			trendSentiment.add(Utils.getTrendSentiment(rs.getString(4), rs.getString(5), rs.getString(6)));
			logger.info(rs.toString());
		}
		return trendSentiment;
	}
	
	public List<JSONObject> getTrendSentiment(int hours, int day,String trend) {
		
		List<JSONObject> trendSentiment = new LinkedList<>();
		ResultSet resultSet = session.execute("select * from tweet_sentiment where day="+day+" AND trend='"+trend+" AND hours="+hours+"' ALLOW FILTERING");
		
		for(Row rs: resultSet) {
			trendSentiment.add(Utils.getTrendSentiment(rs.getString(4), rs.getString(5), rs.getString(6)));
			logger.info(rs.toString());
		}
		return trendSentiment;
	}

	public List<JSONObject> getHighActivityCountries(int day) {
		List<JSONObject> trendSentiment = new LinkedList<>();
		ResultSet resultSet = session.execute("select * from trend_activity_regions where day="+day+" ALLOW FILTERING");
		
		for(Row rs: resultSet) {
			trendSentiment.add(Utils.getHighActivityCountry(rs.getString(4), rs.getString(5)));
			logger.info(rs.toString());
		}
		return trendSentiment;
	}
	
	public List<JSONObject> getHighActivityCountries(int day,int hours) {
		List<JSONObject> trendSentiment = new LinkedList<>();
		ResultSet resultSet = session.execute("select * from trend_activity_regions where day="+day+" AND hours="+hours+" ALLOW FILTERING");
		
		for(Row rs: resultSet) {
			trendSentiment.add(Utils.getHighActivityCountry(rs.getString(4), rs.getString(5)));
			logger.info(rs.toString());
		}
		return trendSentiment;
	}


}

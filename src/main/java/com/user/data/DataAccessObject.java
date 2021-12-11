package com.user.data;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.user.utils.Utils;

@Component
public class DataAccessObject {
	
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
	
	public List<JSONObject> getTrends() {
		
		List<JSONObject> trends = new LinkedList<>();
		ResultSet resultSet = session.execute("select * from trends");
		
		for(Row rs: resultSet) {
			trends.add(Utils.getTrend(rs.getString(4), rs.getInt(5)));
			System.out.println(rs);
		}
		return trends;
	}
	
	public List<JSONObject> getTrendSentiment() {
		
		List<JSONObject> trendSentiment = new LinkedList<>();
		ResultSet resultSet = session.execute("select * from tweet_sentiment");
		
		for(Row rs: resultSet) {
			trendSentiment.add(Utils.getTrendSentiment(rs.getString(4), rs.getString(5), rs.getString(6)));
			System.out.println(rs);
		}
		return trendSentiment;
	}

	public List<JSONObject> getHighActivityCountries() {
		List<JSONObject> trendSentiment = new LinkedList<>();
		ResultSet resultSet = session.execute("select * from trend_activity_regions");
		
		for(Row rs: resultSet) {
			trendSentiment.add(Utils.getHighActivityCountry(rs.getString(4), rs.getString(5)));
			System.out.println(rs);
		}
		return trendSentiment;
	}


}

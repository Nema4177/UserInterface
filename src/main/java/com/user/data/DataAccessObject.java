package com.user.data;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.user.controller.Controller;
import com.user.utils.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import software.amazon.awssdk.services.s3.S3Utilities;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.core.sync.ResponseTransformer;

@Component
public class DataAccessObject {
	
    private static Logger logger = LoggerFactory.getLogger(DataAccessObject.class);

	Session session;
	
	DataAccessObject() {
		
		Cluster cluster = Cluster.builder()
				.withoutJMXReporting()
		        .withClusterName("myCluster")
		        //.addContactPoint("127.0.0.1")
		        .addContactPoint("35.224.198.193")
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

	private JSONObject getCredentialsJSON(){
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try
		{
			Object obj = parser.parse(new FileReader("src/main/resources/credentials.json"));
			jsonObject = (JSONObject) obj;
			String access_key = (String) jsonObject.get("AWS_ACCESS_KEY");
			String secret_key = (String) jsonObject.get("AWS_SECRET_KEY");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return  jsonObject;
	}

	public String getDailyReport(int day, int month) {

		JSONObject credsJSON = getCredentialsJSON();
		AWSCredentials creds = new BasicAWSCredentials(
				(String) credsJSON.get("AWS_ACCESS_KEY"),
				(String) credsJSON.get("AWS_SECRET_KEY")
		);

		String BUCKET_NAME = (String)credsJSON.get("BUCKET_NAME");
		AmazonS3 s3client = AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(creds))
				.withRegion(Regions.US_EAST_2)
				.build();

		String reportName = "report_"+month+"-"+day + ".pdf";

		URL s3Url = s3client.getUrl(BUCKET_NAME, reportName);
		//System.out.println("url: "+s3Url);
		String msg = "Access your daily report here: "+s3Url.toString();
		System.out.println(msg);
//		S3Utilities utilities = S3Utilities.builder().region(Regions.US_WEST_2).build();
//		GetUrlRequest request = GetUrlRequest.builder().bucket("foo-bucket").key("key-without-spaces").build();
//		URL url = utilities.getUrl(request);
		return msg;
	}



}

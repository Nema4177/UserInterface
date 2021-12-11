package com.user.model;

public class Trend {
	
	int hours;
	int day;
	int month;
	public int getHours() {
		return hours;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public String getTrend() {
		return trend;
	}

	public int getVolumne() {
		return volumne;
	}

	
	String trend;
	int volumne;
	
	Trend(int hours,int day,int month,String trend,int volume){
		this.hours = hours;
		this.day = day;
		this.month = month;
		this.trend = trend;
		this.volumne = volume;
	}

}

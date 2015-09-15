package com.example.smap;

import java.util.ArrayList;
import java.util.List;

import android.R.string;

public class Info {
	// ∏≤∏«ŒÔ–≈œ¢
	private double latitude;
	private double longtitude;
	private string name;
	private string distance;
	private int zan;
	
	public static List<Info> infos = new ArrayList<Info>();
//	static{
//		infos .add(new Info(latitude, longtitude, name, distance, zan));
//	}
//	

	public Info(double latitude, double longtitude, string name,
			string distance, int zan) {
		super();
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.name = name;
		this.distance = distance;
		this.zan = zan;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public string getName() {
		return name;
	}

	public void setName(string name) {
		this.name = name;
	}

	public string getDistance() {
		return distance;
	}

	public void setDistance(string distance) {
		this.distance = distance;
	}

	public int getZan() {
		return zan;
	}

	public void setZan(int zan) {
		this.zan = zan;
	}

}

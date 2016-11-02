package com.example;

import java.util.ArrayList;

public class Asset {
	
	private String asset_id;
	private ArrayList<ParkingSpot> locs = new ArrayList<ParkingSpot>();
	
	public ArrayList<ParkingSpot> getLocs() {
		return locs;
	}
	public void setLocs(ArrayList<ParkingSpot> locs) {
		this.locs = locs;
	}
	public String getAsset_id() {
		return asset_id;
	}
	public void setAsset_id(String asset_id) {
		this.asset_id = asset_id;
	}
	
	
	

}

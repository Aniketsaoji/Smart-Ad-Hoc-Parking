package com.example;

import java.util.*;

import org.springframework.stereotype.Service;

import org.springframework.boot.json.JacksonJsonParser;

import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;


/**
 * Handles the Rule Session and insertions into the Rule Session
 * @author 212572312
 *
 */
@Service
public class RuleEngineService {
	
	ArrayList<Asset> allAssets = new ArrayList<Asset>();
	ArrayList<ParkingSpot> allLocations = new ArrayList<ParkingSpot>();
	ArrayList<Event> events = new ArrayList<Event>();
	ArrayList<Float> latitudes = new ArrayList<>(Arrays.asList(32.711735f, 32.711636f, 32.712658f, 32.712658f, 32.71195f,
			32.71179f, 32.712536f, 32.71254f, 32.71254f, 32.712536f, 32.71286f, 32.7128f, 32.71274f,32.712543f, 32.712543f, 32.712425f,
			32.711586f, 32.711613f, 32.711613f, 32.71145f, 32.71145f, 32.71145f));
	ArrayList<Float> longitudes = new ArrayList<>(Arrays.asList(-117.157326f, -117.15733f, -117.157745f, -117.1576f, -117.15645f,
			-117.15645f, -117.158104f, -117.15794f, -117.15803f, -117.15817f, -117.15733f, -117.15733f, -117.15733f, -117.157265f,
			-117.157196f, -117.158394f, -117.156784f, -117.156715f, -117.156654f, -117.15721f, -117.157135f, -117.15707f));
	ArrayList<String> locationUids = new ArrayList<>(Arrays.asList("157-parkingspot", "158-parkingspot", "182-parkingspot","184-parkingspot",
			"162-parkingspot", "161-parkingspot", "237-parkingspot", "239-parkingspot", "238-parkingspot", "236-parkingspot", "147-parkingspot",
			"148-parkingspot", "149-parkingspot", "191-parkingspot", "192-parkingspot", "141-parkingspot", "164-parkingspot", "165-parkingspot",
			"166-parkingspot", "130-parkingspot", "131-parkingspot", "132-parkingspot"));

	long end;
	Date date = new Date();
	long start = date.getTime();

    public double getParkingPrice() {
        double total = 22.0;
        double filled = 0.0;
        for (ParkingSpot parkingSpot : allLocations) {
            if (parkingSpot.getStatus() == true) {
                filled += 1;
            }
        }
        System.out.println(filled);
        System.out.println(total);
        double percentFilled = filled/total;
        double price = 3 + percentFilled*5;
        return price;
    }

    public String getParkingDetails() {
        String json2Return = "{ \"data\":[";
        for(ParkingSpot parkingSpot : allLocations) {
            json2Return = json2Return + parkingSpot.toJson() + ",";
            System.out.println("lolcatz" + parkingSpot.toJson());
        }
        json2Return = json2Return.substring(0, json2Return.length() - 1);
        json2Return = json2Return + "]}";
        System.out.println(json2Return);
        return json2Return;
    }
	
	public String getEvents(long start, long end){
		String s = "{\"events\": [";
		ArrayList<Event> recent = new ArrayList<Event>();
		for(Event e: events){
			if(e.getTs() >= start && e.getTs() <= end){
				s += "{\"ts\":" + e.getTs() + "," + "\"filled\":" + e.getNum_filled() + ",\"open\":" + e.getNum_open() + "},";
			}
			if((new Date()).getTime() - e.getTs() < 86400000){
				recent.add(e);
			}
		}
		s = s.substring(0, s.length()-1);
		s += "]}";
		System.out.println(s);
		events = recent;
		return s;
	}

	@Scheduled(fixedRate=15000)
	public void getParkingEvents() {
		Date d = new Date();
		System.out.println("GETTING DATA " + d.getTime());
		Double percentageFilled = Math.random();

		for (ParkingSpot parkingSpot : allLocations) {
			Double randomValue = Math.random();
			if (randomValue <= percentageFilled) {
				parkingSpot.setStatus(true);
			}
			else {
				parkingSpot.setStatus(false);
			}
		}

//        			long ts = (long) ((LinkedHashMap)l).get("timestamp");
//        			String event_type = (String) ((LinkedHashMap)l).get("event-type");
//        			String location_uid = (String) ((LinkedHashMap)l).get("location-uid");
//        			for(ParkingSpot s: allLocations){
//        				if(s.getLocationuid().equals(location_uid)){
//        					if(event_type.equals("PKIN")){
//        						if(!s.getStatus()){
//        							s.setStatus(true);
//        							s.setLast_ts(ts);
//        							Event last = events.get(events.size() - 1);
//            						Event e = new Event();
//            	        			e.setNum_filled(Math.min(22,last.getNum_filled() + 1));
//            	        			e.setNum_open(22 - e.getNum_filled());
//            	        			e.setTs(ts);
//            	        			System.out.println("lolCATS" + s.getLocationuid() + " " +  s.get_chance() + " " + s.getLast_ts() + " " + s.getStatus());
//            	        			events.add(e);
//        						}
//        					}
//        					else{
//        						if(s.getStatus()){
//        							s.setStatus(false);
//        							s.setLast_ts(ts);
//        							Event last = events.get(events.size() - 1);
//            						Event e = new Event();
//            	        			e.setNum_filled(Math.max(0, last.getNum_filled() - 1));
//            	        			e.setNum_open(22 - e.getNum_filled());
//            	        			e.setTs(ts);
//
//            	        			System.out.println(s.getLocationuid() + " " +  s.get_chance() + " " + s.getLast_ts() + " " + s.getStatus());
//
//            	        			events.add(e);
//        						}
//        					}
//        					break;
//        				}
//        			}
//        		}
//    		}
//
//    	}
    	start = end;
    	end = start + 15000;
    
    }
	
	public void initialize(){
		System.out.println("INITIALIZING");
		Date date = new Date();
		end = date.getTime();
		start = end - 15000;
		Event init_event = new Event();
		init_event.setNum_filled(0);
		init_event.setNum_open(22);
		init_event.setTs(date.getTime());
		events.add(init_event);
//
		System.out.println("_____________ ");
		for (int i=0; i<22; i++) {
			ParkingSpot parkingSpot = new ParkingSpot();
			parkingSpot.setLocationuid(locationUids.get(i));
			parkingSpot.setLatitude(latitudes.get(i));
			parkingSpot.setLongitude(longitudes.get(i));
			parkingSpot.setStatus(false);
			allLocations.add(parkingSpot);
		}
    }
}

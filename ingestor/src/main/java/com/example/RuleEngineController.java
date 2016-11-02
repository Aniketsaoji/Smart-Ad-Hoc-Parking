package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;


/**
 * 
 * @author 212572312
 */
@RestController
public class RuleEngineController {

    private final RuleEngineService service;
    private final Date date = new Date();

    
    @Autowired
    public RuleEngineController(RuleEngineService service) {
        this.service = service;
         this.service.initialize();
        // this.service.getParkingEvents();
    }
    
    @CrossOrigin(origins = "https://yoparking.run.aws-usw02-pr.ice.predix.io")
    @RequestMapping(value = "/getParkingEvents", method = RequestMethod.GET)
    public void getParkingEvents() {
        service.getParkingEvents();
    }

    @CrossOrigin(origins = "https://yoparking.run.aws-usw02-pr.ice.predix.io")
    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String insert(
            @RequestParam(required = true) long start,
            @RequestParam(required = true) long end){

        return service.getEvents(start, end);  
    }

    @RequestMapping(value = "/initialize", method = RequestMethod.GET)
    public void init(){
    	service.initialize();
    }
    
    @CrossOrigin(origins = "https://yoparking.run.aws-usw02-pr.ice.predix.io")
    @RequestMapping(value = "/parkingDetails", method = RequestMethod.GET)
    public String getParkingDetails() {
        return service.getParkingDetails();
    }


    @CrossOrigin(origins = "https://yoparking.run.aws-usw02-pr.ice.predix.io")
    @RequestMapping(value = "/price", method = RequestMethod.GET)
    public double getParkingPrice() {
        return service.getParkingPrice();
    }
}

package com.niveka.web.rest;

import com.niveka.payload.Notify;
import com.niveka.payload.NotifyResponse;
import com.niveka.service.AndroidPushNotificationsService;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *  Created by Nivek@lara on 31/05/2019.
 */

@RestController
@RequestMapping("/tester")
public class TestResource {

    @Autowired
    private AndroidPushNotificationsService service;

    @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> sendNotify() throws JSONException {
        Notify notify = new Notify();
        notify.setSenderName("niveka");
        notify.setContent("When I access localhost:8080/send , I get an exception 401 Unauthorized. What could be the reason");
        notify.setTitle("Test spring");
        notify.setType(0);
        String[] ids = {"fNm78bTSC1w:APA91bFBdHSwjkrsxALjSH_1YAt5-VlrKFsj-XCK6fYoYivRah0Mh2CfcKIFHMhxz7yr7iU9S8hd-hZ0mmEefDB4-JgUNklKXCinr0ITZqId3sQzBLfOCgwNyyU6dyZiWOjyJ-8qNcrP"};
        NotifyResponse response = service.notifyUsers(notify,ids,false);

        return new ResponseEntity<>(response.toString(), HttpStatus.GONE);
    }


    @RequestMapping("/job")
    public void job(){

    }
}

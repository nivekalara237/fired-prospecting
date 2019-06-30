package com.niveka.service;

import com.niveka.common.HeaderRequestInterceptor;
import com.niveka.payload.Notify;
import com.niveka.payload.NotifyResponse;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 *  Created by Nivek@lara on 31/05/2019.
 */

@Service
public class AndroidPushNotificationsService {
    private static final String FIREBASE_SERVER_KEY = "AAAAAEJHYnw:APA91bEbPLbfrpDJ-WiMM1up7zgxwCvruquNegtiQr_MEwaXm3WGz3q3ZiDEzPgK87BDUdzrBpg9QFZ6W1lYw3d0zsQUdrc0mJ6qYhXJW7BzpeeiM8uHS-BAbMNMJ5PP4jXSitF1EgOz";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
    private final Logger log = LoggerFactory.getLogger(AndroidPushNotificationsService.class);
    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        /**
         *
         * https://fcm.googleapis.com/fcm/send
         * Content-Type:application/json
         * Authorization:key=FIREBASE_SERVER_KEY
         *
         */

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);
        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
        return CompletableFuture.completedFuture(firebaseResponse);
    }

    public NotifyResponse notifyUsers(Notify notify,String[] fcmTokensOrTopic,boolean toTopic) throws JSONException {
        NotifyResponse notifyResponse = new NotifyResponse();
        String json;
        if (!toTopic){
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < fcmTokensOrTopic.length; i++) {
                s.append("\"")
                    .append(fcmTokensOrTopic[i])
                    .append("\",");
            }
            String tokens = s.toString();
            tokens = tokens.substring(0,tokens.length()-1);
            json = "{\n" +
                "    \"data\": {\n" +
                "        \"content\": \""+notify.getContent()+"\",\n" +
                "        \"title\": \""+notify.getTitle()+"\",\n" +
                "        \"type\": "+notify.getType()+",\n" +
                "        \"sender_name\": \""+notify.getSenderName()+"\n" +
                "     },\n" +
                "    \"registration_ids\": ["+tokens+"],\n" +
                "    \"priority\":\"high\"\n" +
                "}";
        }else{
            StringBuilder s = new StringBuilder();
            s.append("\"/topic/");
            for (int i = 0; i < fcmTokensOrTopic.length; i++) {
                s.append(fcmTokensOrTopic[i]).append("\"");
            }
            String topic = s.toString();
            json = "{\n" +
                "    \"data\": {\n" +
                "        \"content\": \""+notify.getContent()+"\",\n" +
                "        \"title\": \""+notify.getTitle()+"\",\n" +
                "        \"type\": "+notify.getType()+",\n" +
                "        \"sender_name\": \""+notify.getSenderName()+"\n" +
                "     },\n" +
                "    \"to\": "+topic+",\n" +
                "    \"priority\":\"high\"\n" +
                "}";
        }
        HttpEntity<String> request = new HttpEntity<>(json);
        CompletableFuture<String> pushNotification = this.send(request);
        CompletableFuture.allOf(pushNotification).join();
        try {
            String firebaseResponse = pushNotification.get();

            notifyResponse.setResponse(firebaseResponse);
            notifyResponse.setStatusCode(pushNotification.isDone()?200:400);
        } catch (InterruptedException e) {
            e.printStackTrace();
            notifyResponse.setStatusCode(500);
            notifyResponse.setResponse(e.getMessage());
        } catch (ExecutionException e) {
            notifyResponse.setStatusCode(500);
            notifyResponse.setResponse(e.getMessage());
            e.printStackTrace();
        }
        return notifyResponse;
    }

}

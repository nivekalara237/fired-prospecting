package com.niveka.web.rest.util;

import com.niveka.domain.User;
import com.niveka.service.UserService;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Nivek@lara on 08/01/2019.
 */
public class Utils {
//    public static String convertJodaTimeToReadable(String jodaStr){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
//        DateTime myJodaFromString = new DateTime(jodaStr);
//        return DateTimeUtils.formatWithPattern(myJodaFromString.toDate(),"dd-MM-yyyy HH:mm:ss");
//    }
//
//    public static String convertJodaTimeToReadable2(String jodaStr){
//        //DateTime myJodaFromString = new DateTime("2018-05-05T05:55:55.000-04:00");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
//        DateTime myJodaFromString = new DateTime(jodaStr);
//        return DateTimeUtils.formatWithPattern(myJodaFromString.toDate(),"dd-MM-yyyy HH:mm:ss");
//    }

    public static String getDateToJoda(String jodaStr){
        /*DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        Date date = null;
        DateTime dt = formatter.parseDateTime(jodaStr);
        date = dt.toDate();
        return date;*/
        String customFormat = "yyy/MM/dd HH:mm";

        if(jodaStr!=null && !jodaStr.equals("")){
            DateTimeFormatter dtf = ISODateTimeFormat.dateTime();
            LocalDateTime parsedDate = dtf.parseLocalDateTime(jodaStr);
            Date d = null;
            String dateWithCustomFormat = parsedDate.toString(DateTimeFormat.forPattern(customFormat));
            return dateWithCustomFormat;
        }else
            return "";

    }

    public static long getJodaToLong(String str){
        if(str!=null && !str.equals("")){
            DateTimeFormatter dtf = ISODateTimeFormat.dateTime();
            LocalDateTime parsedDate = dtf.parseLocalDateTime(str);
            return  parsedDate.toDate().getTime();
        }else
            return 0;
    }

    public static String currentJodaDateStr(){
        DateTime today = new DateTime().toDateTime();
        return today.toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ"));
    }

    public static User currentUser(UserService userService){
        final Optional<User> isUser = userService.getUserWithAuthorities();
        return isUser.orElse(null);
    }

    public String getCurrentUserLogin() {
        org.springframework.security.core.context.SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String login = null;
        if (authentication != null)
            if (authentication.getPrincipal() instanceof UserDetails)
                login = ((UserDetails) authentication.getPrincipal()).getUsername();
            else if (authentication.getPrincipal() instanceof String)
                login = (String) authentication.getPrincipal();

        return login;
    }

}

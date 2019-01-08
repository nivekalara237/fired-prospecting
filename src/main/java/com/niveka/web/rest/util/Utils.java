package com.niveka.web.rest.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static Date getDateToJoda(String jodaStr){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
        Date date = null;
        DateTime dt = formatter.parseDateTime(jodaStr);
        date = dt.toDate();
        return date;
    }

    public static String currentJodaDateStr(){
        DateTime today = new DateTime().toDateTime();
        return today.toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ"));
    }
}

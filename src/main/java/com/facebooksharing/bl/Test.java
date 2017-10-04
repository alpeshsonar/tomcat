package com.facebooksharing.bl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class Test 
{
	DateFormat dfm = new SimpleDateFormat("yyyyMMddHHmm");  

    long unixtime;
    public String timeConversion(String time)
    {
        dfm.setTimeZone(TimeZone.getTimeZone("GMT+4:30"));//Specify your timezone 
	    try
	    {
	        unixtime = dfm.parse(time).getTime();  
	        unixtime=unixtime/1000;
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	    return unixtime+"";
    }
}

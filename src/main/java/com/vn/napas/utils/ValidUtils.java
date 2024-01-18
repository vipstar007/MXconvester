package com.vn.napas.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class ValidUtils {
	
	 public static boolean isNullOrEmpty(String s) {
	        if (s == null || s.isEmpty()) {
	            return true;
	        }
	        s = s.trim();
	        if ("".equals(s)) {
	            return true;
	        }
	        return false;
	    }
	 
	 
	 public static boolean isTimeStamp(String date) {
	        try {
	            if (date == null || date.isEmpty()) {
	                return false;
	            }
	            date = date.trim();
		        if ("".equals(date)) {
		            return false;
		        }
		        //"yyyy-MM-dd'T'HH:mm:ssXXX"
	            SimpleDateFormat sdf = new SimpleDateFormat(Constants.VALID_TIME_STAMP);
	            sdf.setLenient(false);
	            sdf.parse(date);
	            return true;
	        } catch (ParseException e) {
	            return false;
	        } catch (Exception e) {
	            return false;
	        }
	    }
	    
}

package com.finartz.airplanereservations.demo.util;

public class CardUtil {
    public static String maskCard(String plainCardNumber){
        String validateCardNumber = plainCardNumber.replaceAll("[^0-9]","");
        if (validateCardNumber.length() == 16){
            return validateCardNumber.substring(0,6) + "******" + validateCardNumber.substring(12);
        }else{
            return null;
        }
    }

}

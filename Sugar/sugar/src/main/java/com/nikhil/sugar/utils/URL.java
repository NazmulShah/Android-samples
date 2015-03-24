package com.peelworks.hul.me.utils;
public class URL {

    public static String getFullURL(String webServiceName){
        return WSConstants.baseURL + webServiceName;
    }
}

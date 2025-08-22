package com.sbs.java.text_board.global.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class Util {

  public static Map<String, String> getParamsFormUrl(String url) {
    System.out.println("getParamsFormUrl 메소드 실행!!");
    Map<String,String> params = new LinkedHashMap<>();  //순서를 보장한다.

    String[] urlBit = url.split("\\?",2);

    if(urlBit.length == 1) return params;

    String[] queryStr = urlBit[1].split("&");

    for (String bit: queryStr) {
      String[] bits = bit.split("=",2);  //배열의 크기 2개
      if (bit.length() == 1) continue;
      String key = bits[0];
      String value = bits[1];
      params.put(key, value);
    }
    return params;
  }

  public static String getUrlPath(String url){
    System.out.println("getUrlPath 메소드 실행!!");
    String urlBit = url.split("\\?",2)[0];
    return urlBit;
  }
}

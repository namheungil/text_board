package com.sbs.java.text_board;

import java.util.LinkedHashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

  public static void main(String[] args) {
    //디스패처 서블릿 방식
    App app = new App();
    app.run();  //로직을 실행
  }
}

class Article {
  int id;
  String subject;
  String content;

  //생성자
  Article(int id, String subject, String content) {
    this.id = id;
    this.subject = subject;
    this.content = content;

  }

  @Override
  public String toString() {
    return "[id:%d subject:%s content:%s]".formatted(id,subject,content);
  }
}

class Util {
  static Map<String, String> getParamsFormUrl(String url){
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

  static String getUrlPath(String url){
    System.out.println("getUrlPath 메소드 실행!!");
    String urlBit = url.split("\\?",2)[0];
    return urlBit;
  }
}

class Rq {
  String url;
  Map<String,String> params;
  String urlPath;

  Rq(String url) {
    this.url = url;
    params = Util.getParamsFormUrl(this.url);
    urlPath = Util.getUrlPath(this.url);
  }

  public Map<String, String> getParams()
  {
    return params;
  }

  public String getUrlPath() {
    return urlPath;
  }
}
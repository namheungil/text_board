package com.sbs.java.text_board.global.base;

import com.sbs.java.text_board.global.util.Util;

import java.util.Map;

public class Rq {
  public String url;
  public Map<String,String> params;
  public String urlPath;

  public Rq(String url) {
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

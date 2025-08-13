import java.util.LinkedHashMap;
import java.util.Map;

public class AppTest {
  public static void main(String[] args) {

    Rq rq = new Rq("/usr/article/write?subject=자바는 무엇인가요?&content=자바는 객체지향 프로그래밍 언어입니다.1+2=3&writerName=홍길동&boardId=1&id=");

    Map<String,String> params = rq.getParams();
    System.out.println(params);
    System.out.println(params);

    String urlPath = rq.getUrlPath();
    System.out.println(urlPath);
    System.out.println(urlPath);

    //System.out.println(urlBit[4]);

    //System.out.println(Arrays.toString(queryStringBits));
    //List<String> paramNames = new ArrayList<>();
    //List<String> paramValues = new ArrayList<>();
    /*
    for(String bit : queryStringBits) {
      String[] bitBits = bit.split("=");
      String paramName = bitBits[0];
      String paramValue = bitBits[1];

      params.put(paramName, paramValue);
      //paramNames.add(paramName);
      //paramValues.add(paramValue);

      //System.out.printf("%s : %s\n", paramNames, paramValues);/*
    }
    System.out.println(params);

    System.out.println("====원하는것만 하나씩 뽑아오기====");
    System.out.printf("ocid : %s\n", params.get("ocid"));
    System.out.printf("pc : %s\n", params.get("pc"));
    System.out.printf("cvid : %s\n", params.get("cvid"));
    System.out.printf("point : %d\n", Integer.parseInt(params.get("point")));

    System.out.println("====반복문을 사용해서 데이터 순회====");
    params.forEach((key,value) -> System.out.printf("%s : %s\n", key, value));

    String targetData = "cvid";
    int findIndex = paramNames.indexOf("cvid");
    System.out.printf("%s : %s\n", targetData, paramValues.get(findIndex));


    for(int i=0; i < paramNames.size(); i++) {
      String paramName = paramNames.get(i);
      int paramValue =  paramValues.get(i);
      System.out.printf("%s : %d\n", paramName, paramValue);
    }
    */

    //Arrays.stream(queryStringBits).forEach(System.out::println);
  }
}
//subject=자바는 무엇인가요?&content=자바는 객체지향 프로그래밍 언어입니다.1+2=3&writerName=홍길동&boardId=1&id=
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
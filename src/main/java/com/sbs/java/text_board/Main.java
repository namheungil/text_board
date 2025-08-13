package com.sbs.java.text_board;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

  static void makeArticleTest(List<Article> articles) {

    // V1
    //for (int i=1; i<=3; i++) {
    //  articles.add(new Article(i,"제목"+ i,  "내용" + i));
    //}

    // V2
    IntStream.rangeClosed(1,100)
        .forEach(i -> articles.add(new Article(i,"제목"+ i,  "내용" + i) ));
  }

  public static void main(String[] args) {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    Scanner sc = new Scanner(System.in);

    List<Article> articles = new ArrayList<>();

    makeArticleTest(articles);
    int lastArticleid = articles.get(articles.size()-1).id;  //전역번수

    System.out.println("==자바 텍스트 게시판===");
    System.out.println("텍스트 게시판을 시작합니다.");

    while(true) {
      System.out.print("명령) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);

      if(rq.getUrlPath().equals("/usr/article/write")) {
        actionUsrArticleWrite(sc, articles, lastArticleid);
        lastArticleid++;
      } else if (rq.getUrlPath().equals("/usr/article/list")) {
        actionUsrArticleList(rq, articles);
      } else if (rq.getUrlPath().equals("/usr/article/detail")) {
        actionUsrArticleDetail(rq, articles);
      } else if (rq.getUrlPath().equals("/usr/article/modify")) {
        actionUsrArticleModify(sc, rq, articles);
      } else if (rq.getUrlPath().equals("/usr/article/delete")) {
        actionUsrArticleDelete(rq, articles);
      } else if (rq.getUrlPath().equals("exit")) {
        System.out.println("프로그램을 종료합니다.");
        break;
      } else {
        System.out.println("잘못 입력 된 명령어입니다.");
      }
      System.out.printf("입력 받은 명령어: %s\n",cmd );
    }

    System.out.println("텍스트 게시판을 종료합니다.");
    System.out.println("==자바 텍스트 게시판 종료");

    sc.close();

  }

  private static void actionUsrArticleDelete(Rq rq, List<Article> articles) {
    Map<String,String> params = rq.getParams();

    if(!params.containsKey("id")) {
      System.out.println("id값을 입력해 주세요");
      return;
    }

    int id = 0;

    try {
      id = Integer.parseInt(params.get("id"));
    } catch(NumberFormatException e) {
      System.out.println("id를 정수 형태로 입력해 주세요");
      return;
    }

    if(articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    //V1
    /*
    Article findArticle = null;
    for(Article article : articles) {
      if(article.id==id) {
        findArticle = article;
        break;
      }
    }
    */

    //V2
    int finalId = id;
    Article findArticle = articles.stream()
        .filter(article ->article.id==finalId)
        .findFirst()
        .orElse(null); // 못 찾은 경우에 null반환

    if(findArticle == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    articles.remove(findArticle);
    System.out.printf("%d번 게시물이 삭제되었습니다.\n",id);
  }

  private static void actionUsrArticleModify(Scanner sc, Rq rq, List<Article> articles) {
    Map<String,String> params = rq.getParams();

    if(!params.containsKey("id")) {
      System.out.println("id값을 입력해 주세요");
      return;
    }

    int id = 0;

    try {
      id = Integer.parseInt(params.get("id"));
    } catch(NumberFormatException e) {
      System.out.println("id를 정수 형태로 입력해 주세요");
      return;
    }

    if(articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }
    if (id > articles.size()) {
      System.out.printf("%d 번의 게시물이 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("==%d번 게시물 수정==\n", id);
    //검색 ID의 내용을 가져온다.
    Article article = articles.get(id-1);
    System.out.print("새 제목 : ");
    article.subject = sc.nextLine();
    System.out.print("새 내용 : ");
    article.content = sc.nextLine();

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  private static void actionUsrArticleWrite(Scanner sc, List<Article> articles, int lastArticleid) {
    System.out.println("==게시물 작성==");
    System.out.print("제목 : ");
    String subject = sc.nextLine();

    if(subject.trim().isEmpty()) {
      System.out.println("제목을 입력해 주세요.");
      return;
    }
    System.out.print("내용 : ");
    String content = sc.nextLine();

    if(content.trim().isEmpty()) {
      System.out.println("내용을 입력해 주세요");
      return;
    }

    int id = ++lastArticleid;

    Article article= new Article(id,subject,content);
    //객체 생성후 객체 변수에 데이터 저장
    articles.add(article);

    System.out.printf("%d번게시물이 등록되었습니다.\n",id);
  }

  private static void actionUsrArticleDetail(Rq rq, List<Article> articles) {
    Map<String,String> params = rq.getParams();

    if(!params.containsKey("id")) {
      System.out.println("id값을 입력해 주세요");
      return;
    }

    int id = 0;

    try {
      id = Integer.parseInt(params.get("id"));
    } catch(NumberFormatException e) {
      System.out.println("id를 정수 형태로 입력해 주세요");
      return;
    }

    if(articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }
    if (id > articles.size()) {
      System.out.printf("%d 번의 게시물이 존재하지 않습니다.\n", id);
      return;
    }

    //검색 ID의 내용을 가져온다.
    Article article = articles.get(id-1);

    if(article == null) {
      System.out.println("원하는 게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("====게시물 상세보기=====");
    System.out.printf("ID : %d\n",article.id);
    System.out.printf("제목 : %s\n",article.subject);
    System.out.printf("내용 : %s\n",article.content);
    return;
  }

  static void actionUsrArticleList(Rq rq, List<Article> articles) {
    Map<String,String> params = rq.getParams();

    List<Article> filteredArticles = new ArrayList<>(articles);

    if(params.containsKey("searchKeyword")) {
      String searchKeyword = params.get("searchKeyword");
      filteredArticles = articles.stream()
          .filter(article -> article.subject.contains(searchKeyword) || article.content.contains(searchKeyword))
          .collect(Collectors.toList());
    }

    //리스트 정렬 로직
    //원본을 기반한 주소값 복사본을 만든다
    List<Article> sortedArticles = filteredArticles;

    if(params.containsKey("orderBy")){
      String orderBy = params.get("orderBy");
      switch (orderBy) {
        case "idAsc":
          sortedArticles.sort((a1, a2) -> a1.id - a2.id);
          break;
        case "idDesc":
        default:
          sortedArticles.sort((a1, a2) -> a2.id - a1.id);
          break;
      }
    }
    else {
      sortedArticles.sort((a1, a2) -> a2.id - a1.id);
    }

    System.out.printf("====게시물 리스트 보기(총 %d개)=====", sortedArticles.size() );
    System.out.println("번호 | 제목");

    sortedArticles
        .forEach(article -> System.out.printf("%d | %s\n", article.id, article.subject )
        );

    // V1
    //for (int i = articles.size() - 1; 0 <= i; i--) {
    //  Article article = articles.get(i);
    //  System.out.printf("%d | %s\n", article.id, article.subject);
    //}
    // V2
    //for(Article article : articles){
    //  System.out.printf("%d | %s\n", article.id, article.subject);
    //}

    // V3
    //articles.forEach(article -> System.out.printf("%d | %s\n", article.id, article.subject) );
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
package com.sbs.java.text_board.boundedContext.article.controller;

import com.sbs.java.text_board.boundedContext.article.dto.Article;
import com.sbs.java.text_board.container.Container;
import com.sbs.java.text_board.global.base.Rq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArticleController {
  public List<Article> articles;
  public int lastId;

  public ArticleController() {
    articles = new ArrayList<>();
    makeArticleTest();
    lastId = lastArticleid = articles.get(articles.size()-1).id;
  }

  void makeArticleTest() {
    // V1
    //for (int i=1; i<=3; i++) {
    //  articles.add(new Article(i,"제목"+ i,  "내용" + i));
    //}

    // V2
    IntStream.rangeClosed(1,100)
        .forEach(i -> articles.add(new Article(i,"제목"+ i,  "내용" + i) ));
  }

  public void doWrite() {
    System.out.println("==게시물 작성==");
    System.out.print("제목 : ");
    String subject = Container.sc.nextLine();

    if(subject.trim().isEmpty()) {
      System.out.println("제목을 입력해 주세요.");
      return;
    }
    System.out.print("내용 : ");
    String content = Container.sc.nextLine();

    if(content.trim().isEmpty()) {
      System.out.println("내용을 입력해 주세요");
      return;
    }

    int id = ++lastId;

    Article article= new Article(id,subject,content);
    //객체 생성후 객체 변수에 데이터 저장
    articles.add(article);

    System.out.printf("%d번게시물이 등록되었습니다.\n",id);
  }

  public void showList(Rq rq) {
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

    sortedArticles.forEach(
        article -> System.out.printf("%d | %s\n", article.id, article.subject )
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

  public void showDetail(Rq rq) {
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

    //검색 ID의 내용을 가져온다.
    Article article = findById(id,articles);

    if (article == null) {
      System.out.printf("%d 번의 게시물이 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("====%d번 게시물 상세보기=====\n",id);
    System.out.printf("ID : %d\n",article.id);
    System.out.printf("제목 : %s\n",article.subject);
    System.out.printf("내용 : %s\n",article.content);
    return;
  }

  public void doModify(Rq rq) {
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

    Article article = findById(id,articles);

    if (article == null) {
      System.out.printf("%d 번의 게시물이 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("==%d번 게시물 수정==\n", id);
    //검색 ID의 내용을 가져온다.

    System.out.print("새 제목 : ");
    article.subject = Container.sc.nextLine();
    System.out.print("새 내용 : ");
    article.content = Container.sc.nextLine();

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void doDelete(Rq rq) {
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

    Article article = findById(id,articles);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    articles.remove(article);
    System.out.printf("%d번 게시물이 삭제되었습니다.\n",id);
  }

  public Article findById(int id, List<Article> articles) {
    return articles.stream()
        .filter(article ->article.id==id)
        .findFirst()
        .orElse(null); // 못 찾은 경우에 null반환
  }
}

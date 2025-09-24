package com.sbs.java.text_board.boundedContext.article.controller;

import com.sbs.java.text_board.boundedContext.article.dto.Article;
import com.sbs.java.text_board.boundedContext.article.service.ArticleService;
import com.sbs.java.text_board.container.Container;
import com.sbs.java.text_board.global.base.Rq;

import java.util.List;
import java.util.Map;

public class ArticleController {
  public ArticleService articleService;

  public ArticleController() {
    articleService = Container.articleService;;
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

    int id = articleService.write(subject, content);

    System.out.printf("%d번게시물이 등록되었습니다.\n",id);
  }

  public void showList(Rq rq) {
    Map<String,String> params = rq.getParams();
    String searchKeyword = params.get("searchKeyword");
    String orderBy = params.get("orderBy");

    List<Article> articles = articleService.findAll(searchKeyword, orderBy);

    System.out.printf("====게시물 리스트 보기(총 %d개)=====", articles.size() );
    System.out.println("번호 | 제목");

    articles.forEach(
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

    List<Article> articles = articleService.findAll();

    if(articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    //검색 ID의 내용을 가져온다.
    Article article = articleService.findById(id);

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

    List<Article> articles = articleService.findAll();

    if(articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    System.out.printf("==%d번 게시물 수정==\n", id);
    //검색 ID의 내용을 가져온다.
    System.out.print("새 제목 : ");
    String subject = Container.sc.nextLine();

    System.out.print("새 내용 : ");
    String content = Container.sc.nextLine();

    articleService.modify(id, subject, content);

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

    List<Article> articles = articleService.findAll();

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

    articleService.delete(id);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n",id);
  }

}

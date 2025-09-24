package com.sbs.java.text_board.boundedContext.article.service;

import com.sbs.java.text_board.boundedContext.article.dto.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArticleService {
  public List<Article> articles;
  public int lastId;

  public ArticleService() {
    articles = new ArrayList<>();
    makeArticleTest();
    lastId = articles.get(articles.size()-1).id;
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

  public int write(String subject, String content) {
    int id = ++lastId;

    Article article= new Article(id,subject,content);
    //객체 생성후 객체 변수에 데이터 저장
    articles.add(article);

    return id;
  }

  public List<Article> findAll(){
    return articles;
  }


  public List<Article> findAll(String searchKeyword, String orderBy) {

    //검색시작
    //articles : 정렬되지 않은 1~100 게시물 객체를 품고 있는 리스트
    List<Article> filteredArticles = new ArrayList<>(articles);

    if(!searchKeyword.isEmpty()) {
      filteredArticles = articles.stream()
          .filter(article -> article.subject.contains(searchKeyword) || article.content.contains(searchKeyword))
          .collect(Collectors.toList());
    }

    //리스트 정렬 로직
    //원본을 기반한 주소값 복사본을 만든다
    List<Article> sortedArticles = filteredArticles;

    if(!orderBy.isEmpty()){
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
    //정렬 끝

    return sortedArticles;
  }

  public void modify(int id, String subject, String content) {
    Article article = findById(id);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    };

    article.subject = subject;
    article.content = content;
  }

  public void delete(int id){
    Article article = findById(id);
    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    };

    articles.remove(article);
  }

  public Article findById(int id) {
    return articles.stream()
        .filter(article ->article.id==id)
        .findFirst()
        .orElse(null); // 못 찾은 경우에 null반환
  }

}

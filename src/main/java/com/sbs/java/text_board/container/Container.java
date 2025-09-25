package com.sbs.java.text_board.container;

import com.sbs.java.text_board.boundedContext.article.controller.ArticleController;
import com.sbs.java.text_board.boundedContext.article.repository.ArticleRepository;
import com.sbs.java.text_board.boundedContext.article.service.ArticleService;

import java.util.Scanner;

public class Container {
  public static Scanner sc;

  public static ArticleRepository articleRepository;

  public static ArticleService articleService;

  public static ArticleController articleController;

  // 프로그램 실행시 딱1번 실행된다.
  static {
    sc = new Scanner(System.in);

    articleRepository = new ArticleRepository();

    articleService = new ArticleService();

    articleController = new ArticleController();
  }
}

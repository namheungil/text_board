package com.sbs.java.text_board;

import com.sbs.java.text_board.boundedContext.article.controller.ArticleController;
import com.sbs.java.text_board.container.Container;
import com.sbs.java.text_board.global.base.Rq;

public class App {
  public ArticleController articleController;

  public App() {
    articleController = Container.articleController;
  }

  void run() {

    System.out.println("==자바 텍스트 게시판===");
    System.out.println("텍스트 게시판을 시작합니다.");

    while(true) {
      System.out.print("명령) ");
      String cmd = Container.sc.nextLine();

      Rq rq = new Rq(cmd);

      if(rq.getUrlPath().equals("/usr/article/write")) {
        articleController.doWrite();
      } else if (rq.getUrlPath().equals("/usr/article/list")) {
        articleController.showList(rq);
      } else if (rq.getUrlPath().equals("/usr/article/detail")) {
        articleController.showDetail(rq);
      } else if (rq.getUrlPath().equals("/usr/article/modify")) {
        articleController.doModify(rq);
      } else if (rq.getUrlPath().equals("/usr/article/delete")) {
        articleController.doDelete(rq);
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

    Container.sc.close();
  }

}

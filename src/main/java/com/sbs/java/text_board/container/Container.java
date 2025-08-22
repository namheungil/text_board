package com.sbs.java.text_board.container;

import java.util.Scanner;

public class Container {
  public static Scanner sc;
  // 프로그램 실행시 딱1번 실행된다.
  static {
    sc = new Scanner(System.in);
  }
}

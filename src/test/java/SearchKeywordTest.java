import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SearchKeywordTest {
  public static void main(String[] args) {
    List<Article> articles = new ArrayList<>();

    IntStream.rangeClosed(1, 5)
        .forEach(i ->  articles.add(new Article(i,"제목"+i, "내용"+i)) );

    articles.add(new Article(6,"자바는 무슨 언어인가요?","자바가 절차지향인지 객체지향인지 궁금합니다."));
    articles.add(new Article(7,"요즘 넷플릭스 드라마는 뭐가 재밌나요?","'폭삭 속았수다'가 재밌다는데 정말인가요?"));
    articles.add(new Article(8,"코딩실력이 빨리 늘려면...","코딩 공부가 너무 어려워요."));

    System.out.println(articles);

    String searchKeyword = "자바";

    /*
    //V1
    List<Article> filteredArticles = new ArrayList<>();


    //향상된 for문 (변수타입 변수 : 배열 or List [객체] )
    for(Article article : articles) {
      if(article.subject.contains(searchKeyword) || article.content.contains(searchKeyword) ) {
        filteredArticles.add(article);
      }
    }
    */
    //V2  Stream 방식 사용
    List<Article> filteredArticles = articles.stream()
        .filter(article -> article.subject.contains(searchKeyword) || article.content.contains(searchKeyword))
            .collect(Collectors.toList()) ;

    System.out.println(filteredArticles);
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

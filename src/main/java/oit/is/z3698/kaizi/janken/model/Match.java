package oit.is.z3698.kaizi.janken.model;

public class Match {
  int id;
  String user1;
  String user2;
  String user1Hand;
  String user2Hand;

  // Thymeleafでフィールドを扱うためにはgetter/setterが必ず必要
  // vscodeのソースコード右クリック->ソースアクションでsetter/getterを簡単に追加できる
  public String getUser1() {
    return user1;
  }

  public void setUser1(String user1) {
    this.user1 = user1;
  }

  public String getUser2() {
    return user2;
  }

  public void setUser2(String user2) {
    this.user2 = user2;
  }

  public String getUser1Hand() {
    return user1Hand;
  }

  public void setUser1Hand(String user1Hand) {
    this.user1Hand = user1Hand;
  }

  public String getUser2Hand() {
    return user2Hand;
  }

  public void setUser2Hand(String user2Hand) {
    this.user2Hand = user2Hand;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}

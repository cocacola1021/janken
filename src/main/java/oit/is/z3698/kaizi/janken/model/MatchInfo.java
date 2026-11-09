
package oit.is.z3698.kaizi.janken.model;

public class MatchInfo {
  int id;
  int user1;
  int user2;
  String user1Hand;
  boolean isActive;

  // 表示用のユーザー名
  String user1Name;
  String user2Name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUser1() {
    return user1;
  }

  public void setUser1(int user1) {
    this.user1 = user1;
  }

  public int getUser2() {
    return user2;
  }

  public void setUser2(int user2) {
    this.user2 = user2;
  }

  public String getUser1Hand() {
    return user1Hand;
  }

  public void setUser1Hand(String user1Hand) {
    this.user1Hand = user1Hand;
  }

  public boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public String getUser1Name() {
    return user1Name;
  }

  public void setUser1Name(String user1Name) {
    this.user1Name = user1Name;
  }

  public String getUser2Name() {
    return user2Name;
  }

  public void setUser2Name(String user2Name) {
    this.user2Name = user2Name;
  }
}

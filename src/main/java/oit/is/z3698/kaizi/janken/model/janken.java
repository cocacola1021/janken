
package oit.is.z3698.kaizi.janken.model;

public class janken {
  private String userHand;
  private String cpuHand;
  private String result;

  public void play(String hand) {
    this.userHand = hand;
    this.cpuHand = "Gu";

    if (this.userHand.equals(this.cpuHand)) {
      this.result = "Draw";
    } else if (this.userHand.equals("Pa")) {
      this.result = "You Win!";
    } else {
      this.result = "You Lose";
    }
  }

  public String getUserHand() {
    return userHand;
  }

  public String getCpuHand() {
    return cpuHand;
  }

  public String getResult() {
    return result;
  }
}

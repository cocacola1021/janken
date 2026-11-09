package oit.is.z3698.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3698.kaizi.janken.model.janken;
import oit.is.z3698.kaizi.janken.model.Entry;
import oit.is.z3698.kaizi.janken.model.User;
import oit.is.z3698.kaizi.janken.model.UserMapper;
import oit.is.z3698.kaizi.janken.model.Match;
import oit.is.z3698.kaizi.janken.model.MatchMapper;
import oit.is.z3698.kaizi.janken.model.MatchInfo;
import oit.is.z3698.kaizi.janken.model.MatchInfoMapper;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @Autowired
  private MatchInfoMapper matchInfoMapper;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();

    this.entry.addUser(loginUser);

    model.addAttribute("entry", this.entry);
    model.addAttribute("name", loginUser);

    ArrayList<User> users = userMapper.selectAllUsers();
    model.addAttribute("users", users);

    ArrayList<Match> matches = matchMapper.selectAllMatch();
    model.addAttribute("matches", matches);

    // アクティブな試合を取得
    ArrayList<MatchInfo> matchInfos = matchInfoMapper.selectActiveMatchInfo();

    model.addAttribute("matchInfos", matchInfos);

    return "janken.html";
  }

  @org.springframework.web.bind.annotation.PostMapping("/janken")
  public String jankenLogin(
      @RequestParam String name,
      ModelMap model) {

    model.addAttribute("name", name);
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, Principal prin, ModelMap model) {
    User cpu = userMapper.selectById(id);
    model.addAttribute("login_user", prin.getName());
    model.addAttribute("cpu", cpu);
    return "match.html";
  }

  @GetMapping("/fight")
  public String jankenGame(
      Principal prin,
      @RequestParam Integer id,
      @RequestParam String hand,
      ModelMap model) {

    // ログインユーザーを取得
    String loginUser = prin.getName();
    User user1 = userMapper.selectByName(loginUser);

    // 対戦相手を取得
    User user2 = userMapper.selectById(id);

    if (user1 == null || user2 == null) {
      return "redirect:/janken";
    }

    // 手の入力チェック
    if (!hand.equals("Gu")
        && !hand.equals("Choki")
        && !hand.equals("Pa")) {
      return "redirect:/janken";
    }

    // MatchInfoを作成
    MatchInfo matchInfo = new MatchInfo();

    matchInfo.setUser1(user1.getId());
    matchInfo.setUser2(user2.getId());
    matchInfo.setUser1Hand(hand);
    matchInfo.setIsActive(true);

    // matchinfoテーブルに登録
    matchInfoMapper.insertMatchInfo(matchInfo);

    // wait.htmlに表示する情報
    model.addAttribute("name", loginUser);

    return "wait.html";
  }

}

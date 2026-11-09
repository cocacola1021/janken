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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.http.MediaType;

import oit.is.z3698.kaizi.janken.service.AsyncKekka;

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

  @Autowired
  private AsyncKekka asyncKekka;

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
  @Transactional
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

    // 相手が自分との対戦を待っているか確認
    // user1 = 先に待機している相手
    // user2 = 今回ログインしている自分
    MatchInfo waitingMatch = matchInfoMapper.selectWaitingMatchInfo(user2.getId(), user1.getId());

    if (waitingMatch == null) {

      // 待機中の相手がいない場合、自分が先手になる
      MatchInfo matchInfo = new MatchInfo();

      matchInfo.setUser1(user1.getId());
      matchInfo.setUser2(user2.getId());
      matchInfo.setUser1Hand(hand);
      matchInfo.setIsActive(true);

      matchInfoMapper.insertMatchInfo(matchInfo);

      // 待機画面へ
      model.addAttribute("name", loginUser);

      return "wait.html";

    } else {

      // 待機中の相手がいる場合、試合成立

      Match match = new Match();

      // 先手の情報（matchinfoから取得）
      match.setUser1(String.valueOf(waitingMatch.getUser1()));
      match.setUser1Hand(waitingMatch.getUser1Hand());

      // 後手の情報（今回ログインしているユーザー）
      match.setUser2(String.valueOf(user1.getId()));
      match.setUser2Hand(hand);

      // 試合成立
      match.setIsActive(true);

      // matchesテーブルに登録
      matchMapper.InsertMatch(match);

      // 先手の待機状態を解除
      matchInfoMapper.updateMatchInfoActive(waitingMatch.getId());

      // 試合成立後はjanken画面へ
      return "redirect:/janken";
    }
  }

  @GetMapping(value = "/janken/asyncKekka", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter asyncKekka(Principal prin) {

    // ログインユーザーのIDを取得
    User user = userMapper.selectByName(prin.getName());

    SseEmitter emitter = new SseEmitter(0L);

    if (user == null) {
      emitter.complete();
      return emitter;
    }

    // 非同期で試合結果を監視
    asyncKekka.checkMatch(emitter, user.getId());

    return emitter;
  }

}

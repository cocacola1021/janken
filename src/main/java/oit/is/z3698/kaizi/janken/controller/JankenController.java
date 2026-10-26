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

@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @Autowired
  private UserMapper userMapper;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    ArrayList<User> users = userMapper.selectAllUsers();
    model.addAttribute("users", users);
    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String jankenGame(
      Principal prin,
      @RequestParam(required = false) String hand,
      ModelMap model) {

    // ログインユーザー情報を取得
    String loginUser = prin.getName();
    model.addAttribute("name", loginUser);
    model.addAttribute("entry", this.entry);

    if (hand != null) {
      if (!hand.equals("Gu")
          && !hand.equals("Choki")
          && !hand.equals("Pa")) {
        return "redirect:/janken";
      }

      janken game = new janken();
      game.play(hand);

      model.addAttribute("userHand", game.getUserHand());
      model.addAttribute("cpuHand", game.getCpuHand());
      model.addAttribute("result", game.getResult());
    }

    return "janken.html";
  }

  @org.springframework.web.bind.annotation.PostMapping("/janken")
  public String jankenLogin(
      @RequestParam String name,
      ModelMap model) {

    model.addAttribute("name", name);
    return "janken.html";
  }

}

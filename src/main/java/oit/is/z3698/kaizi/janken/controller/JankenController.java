
package oit.is.z3698.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3698.kaizi.janken.model.janken;

@Controller
public class JankenController {

  @GetMapping("/janken")
  public String jankenString() {
    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String jankenGame(
      @RequestParam(required = false) String hand,
      ModelMap model) {

    if (hand != null) {
      if (!hand.equals("Gu")
          && !hand.equals("Choki")
          && !hand.equals("Pa")) {
        return "redirect:/janken";
      }

      janken janken = new janken();
      janken.play(hand);

      model.addAttribute("userHand", janken.getUserHand());
      model.addAttribute("cpuHand", janken.getCpuHand());
      model.addAttribute("result", janken.getResult());
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

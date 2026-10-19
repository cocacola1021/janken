package oit.is.z3698.kaizi.janken.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Entry {
  ArrayList<String> users = new ArrayList<>();

  public void addUser(String name) {
    // 同名のユーザが居たら何もせずにreturn
    for (String s : this.users) {
      if (s.equals(name)) {
        return;
      }
    }
    // 同名のユーザが居なかった場合はusersにnameを追加する
    this.users.add(name);
  }

  public ArrayList<String> getUsers() {
    return users;
  }

}

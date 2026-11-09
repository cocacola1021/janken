
package oit.is.z3698.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchInfoMapper {

  // アクティブな試合を取得する
  @Select("SELECT mi.*, u1.name AS user1Name, u2.name AS user2Name "
      + "FROM matchinfo mi "
      + "JOIN users u1 ON mi.user1 = u1.id "
      + "JOIN users u2 ON mi.user2 = u2.id "
      + "WHERE mi.isActive = TRUE")
  ArrayList<MatchInfo> selectActiveMatchInfo();

  // 対戦待ち情報を登録する
  @Insert("INSERT INTO matchinfo (user1, user2, user1Hand, isActive) "
      + "VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive})")
  void insertMatchInfo(MatchInfo matchInfo);
}

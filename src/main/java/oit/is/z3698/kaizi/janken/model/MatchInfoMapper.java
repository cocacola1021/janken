package oit.is.z3698.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MatchInfoMapper {

  // アクティブな試合をすべて取得
  @Select("SELECT mi.*, u1.name AS user1Name, u2.name AS user2Name "
      + "FROM matchinfo mi "
      + "JOIN users u1 ON mi.user1 = u1.id "
      + "JOIN users u2 ON mi.user2 = u2.id "
      + "WHERE mi.isActive = TRUE")
  ArrayList<MatchInfo> selectActiveMatchInfo();

  // 指定した相手が自分との対戦を待っているか確認
  @Select("SELECT * FROM matchinfo "
      + "WHERE user1 = #{user1} "
      + "AND user2 = #{user2} "
      + "AND isActive = TRUE "
      + "ORDER BY id DESC LIMIT 1")
  MatchInfo selectWaitingMatchInfo(
      @Param("user1") int user1,
      @Param("user2") int user2);

  // 待機情報を登録
  @Insert("INSERT INTO matchinfo (user1, user2, user1Hand, isActive) "
      + "VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive})")
  void insertMatchInfo(MatchInfo matchInfo);

  // 待機状態を解除
  @Update("UPDATE matchinfo SET isActive = FALSE WHERE id = #{id}")
  void updateMatchInfoActive(@Param("id") int id);
}

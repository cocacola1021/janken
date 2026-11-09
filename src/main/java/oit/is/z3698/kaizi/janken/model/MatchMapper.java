package oit.is.z3698.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {

  @Select("SELECT * from matches")
  ArrayList<Match> selectAllMatch();

  @Insert("INSERT INTO matches "
      + "(user1, user2, user1Hand, user2Hand, isActive) "
      + "VALUES "
      + "(#{user1}, #{user2}, #{user1Hand}, #{user2Hand}, #{isActive})")
  void InsertMatch(Match match);

  @Select("SELECT * FROM matchinfo")
  ArrayList<Match> selectAllMatchInfo();

  @Insert("INSERT INTO matchinfo (user1, user2, user1Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive})")
  void InsertMatchInfo(Match match);

  @Select("SELECT * FROM matches "
      + "WHERE user1 = #{userId} AND isActive = TRUE "
      + "ORDER BY id DESC LIMIT 1")
  Match selectActiveMatchByUserId(
      @org.apache.ibatis.annotations.Param("userId") int userId);

  @org.apache.ibatis.annotations.Update("UPDATE matches SET isActive = FALSE WHERE id = #{id}")
  void updateMatchActive(
      @org.apache.ibatis.annotations.Param("id") int id);

}

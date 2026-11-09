
package oit.is.z3698.kaizi.janken.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z3698.kaizi.janken.model.Match;
import oit.is.z3698.kaizi.janken.model.MatchMapper;

@Service
public class AsyncKekka {

  @Autowired
  private MatchMapper matchMapper;

  @Async
  public void checkMatch(SseEmitter emitter, int userId) {

    boolean dbUpdated = false;

    try {
      while (!dbUpdated) {

        // 試合結果を確認
        Match match = matchMapper.selectActiveMatchByUserId(userId);

        if (match != null) {

          // 試合結果を非同期で送信
          emitter.send(
              SseEmitter.event()
                  .name("kekka")
                  .data(match));

          // 表示済みなのでDBを更新
          matchMapper.updateMatchActive(match.getId());

          dbUpdated = true;

          // SSE通信を終了
          emitter.complete();

        } else {

          // まだ試合が成立していない場合は1秒待つ
          Thread.sleep(1000);
        }
      }

    } catch (IOException e) {
      emitter.completeWithError(e);

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      emitter.complete();
    }
  }
}

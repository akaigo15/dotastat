package net.akaigo15.dotastat.opendota.cache;


import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

public class TimedOpenDotaCacheTests {

  @Test
  public void getPlayerHeroInfo_shouldReturnInTimeOk() {
    TimedOpenDotaCache cache = new TimedOpenDotaCache(new DotaCacheConfig(500));
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo("1"));
    list.add(makePlayerHeroInfo("2"));
    list.add(makePlayerHeroInfo("3"));
    list.add(makePlayerHeroInfo("4"));
    cache.addPlayerHeroInfo(list,1234);

    Optional<List<PlayerHeroInfo>> option = cache.getPlayerHeroInfo(1234);
    assertThat(option.isPresent()).isTrue();
    List<PlayerHeroInfo> values = option.get();
    assertThat(values.size()).isEqualTo(4);
  }

  @Test
  public void getPlayerHeroInfo_shouldNotReturnInTime() throws InterruptedException {
    TimedOpenDotaCache cache = new TimedOpenDotaCache(new DotaCacheConfig(1));
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo("1"));
    list.add(makePlayerHeroInfo("2"));
    list.add(makePlayerHeroInfo("3"));
    list.add(makePlayerHeroInfo("4"));
    cache.addPlayerHeroInfo(list,1234);

    Thread.sleep(1100);
    Optional<List<PlayerHeroInfo>> option = cache.getPlayerHeroInfo(1234);
    assertThat(option.isPresent()).isFalse();
  }

  private PlayerHeroInfo makePlayerHeroInfo(String heroId) {
    PlayerHeroInfo info = new PlayerHeroInfo();

    info.setGames(1);
    info.setAgainst_games(2);
    info.setAgainst_win(3);
    info.setLast_played(4);
    info.setWin(5);
    info.setWith_games(6);
    info.setWith_win(7);
    info.setHero_id(heroId);

    return info;

  }
}
